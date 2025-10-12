package dz.a2s.a2sinventaire.security;

import dz.a2s.a2sinventaire.exceptions.ActionNotAllowedException;
import dz.a2s.a2sinventaire.services.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@NoArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JWTGenerator jwtGenerator;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info(" ");log.debug(" ");
        log.info("**********************************************************************************************************************");
        log.info("                    NEW REQUEST - URL : {} - ADRESSE : {}", request.getRequestURI(), request.getRemoteAddr());
        log.info("**********************************************************************************************************************");

        String token = getJWTFromRequest(request);

        try {
            if(StringUtils.hasText(token) && this.jwtGenerator.validateToken(token)) {
                String username = this.jwtGenerator.getUsernameFromJWT(token);
                log.info("Parsed the username from the token {}", username);

                AppUserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                log.info("Authentication set for user: {}", username);
            }
            filterChain.doFilter(request, response);
        } catch (AuthenticationCredentialsNotFoundException e) {
            log.error("AuthenticationCredentialsNotFoundException: {}", e.getMessage());
            throw new ActionNotAllowedException(e.getMessage());
        }
    }

    private String getJWTFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    //ajouter la méthode suivante pour exclure /auth/login de la vérification (filtre) du token, elle va s'exécuter alors dans les deux cas :
    // lorsque il n'y a pas de token et le lorsque le token est présent mais qu'il est invalide
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request)
            throws ServletException {
        String path = request.getRequestURI();

        List<String> excludedPaths = Arrays.asList(
                "/actuator",
                "/swagger-ui/index.html",
                "/auth/login"
        );

        return excludedPaths.contains(path);
    }
}
