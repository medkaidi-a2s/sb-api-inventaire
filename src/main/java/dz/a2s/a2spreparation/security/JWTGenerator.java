package dz.a2s.a2spreparation.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Component
public class JWTGenerator {

    private final SecurityConstants securityConstants;

    public String generateToken(Authentication authentication) {
        log.info("Generating token with authentication object {}", authentication);
        AppUserDetails user = (AppUserDetails) authentication.getPrincipal();

        log.info("Generating token for {}", authentication.getName());
//        String username = authentication.getName();
        String username = user.getUsername();
        Map<String, String> claims = Map.of(
                "username", username,
                "companyId", Integer.toString(user.getCompanyId()),
                "name", user.getName()
        );
        Date currentDate = new Date();
        Timestamp exp = new Timestamp(currentDate.getTime() + this.securityConstants.getJWT_EXPIRATION());

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(currentDate)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS512, this.securityConstants.getJWT_SECRET())
                .compact();

        log.info("Token generated inside JWTGenerator");

        return token;
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(this.securityConstants.getJWT_SECRET())
                .build()
                .parseClaimsJws(token)
                .getBody();

        String usercode = (String) claims.get("username");
        String companyId = (String) claims.get("companyId");

        return companyId + ":" + usercode;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(this.securityConstants.getJWT_SECRET())
                    .build()
                    .parseClaimsJws(token);
            return true;
        }catch(Exception ex) {
           throw new AuthenticationCredentialsNotFoundException("JWT is expired or not valid");
        }
    }

}
