package dz.a2s.a2sinventaire.controllers;

import dz.a2s.a2sinventaire.api.AuthApi;
import dz.a2s.a2sinventaire.dto.auth.AuthResponseDto;
import dz.a2s.a2sinventaire.dto.auth.ChangePasswordDto;
import dz.a2s.a2sinventaire.dto.auth.LoginRequest;
import dz.a2s.a2sinventaire.dto.response.SuccessResponseDto;
import dz.a2s.a2sinventaire.security.JWTGenerator;
import dz.a2s.a2sinventaire.services.CustomUserDetailsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController implements AuthApi {

    private final AuthenticationManager authenticationManager;
//    private final UserEntityRepository userEntityRepository;
//    private final PasswordEncoder passwordEncoder;
    private final JWTGenerator jwtGenerator;
    private final CustomUserDetailsService customUserDetailsService;

    public ResponseEntity<SuccessResponseDto<AuthResponseDto>> login(@RequestBody @Valid LoginRequest loginRequest) {
        log.info("| Entry | AuthController.login method from AuthController with username: {}", loginRequest.getUsername());

        Authentication authentication = this.authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
                  loginRequest.getUsername(),
                  loginRequest.getPassword()
          )
        );

        log.info("Authentication object created {}", authentication);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = this.jwtGenerator.generateToken(authentication);
        AuthResponseDto authResponseDto = new AuthResponseDto(token);
        SuccessResponseDto<AuthResponseDto> successResponseDto = new SuccessResponseDto<AuthResponseDto>(200, "Authentification réussie", authResponseDto);
        return ResponseEntity.ok(successResponseDto);
    }

    public ResponseEntity<SuccessResponseDto<Integer>> changePassword(@RequestBody @Valid ChangePasswordDto changePasswordDto) throws Exception {
        log.info("Point d'entrée de la méthode changePassword du AuthController");

        int response = this.customUserDetailsService.changePassword(changePasswordDto.getCurrentPassword(), changePasswordDto.getNewPassword());

        SuccessResponseDto<Integer> successResponseDto = new SuccessResponseDto<>(
                200,
                "Mot de passe de l'utilisateur a été changé avec succès",
                response
        );

        return ResponseEntity.ok(successResponseDto);
    }

}
