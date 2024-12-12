package dz.a2s.a2spreparation.controllers;

import dz.a2s.a2spreparation.api.AuthApi;
import dz.a2s.a2spreparation.dto.auth.AuthResponseDto;
import dz.a2s.a2spreparation.dto.auth.ChangePasswordDto;
import dz.a2s.a2spreparation.dto.auth.LoginDto;
import dz.a2s.a2spreparation.dto.response.SuccessResponseDto;
import dz.a2s.a2spreparation.repositories.UserEntityRepository;
import dz.a2s.a2spreparation.security.JWTGenerator;
import dz.a2s.a2spreparation.services.CustomUserDetailsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController implements AuthApi {

    private final AuthenticationManager authenticationManager;
    private final UserEntityRepository userEntityRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTGenerator jwtGenerator;
    private final CustomUserDetailsService customUserDetailsService;

    public ResponseEntity<SuccessResponseDto<AuthResponseDto>> login(@RequestBody @Valid LoginDto loginDto) {
        log.info("Entering login method from AuthController with userCode and companyId {} : {}", loginDto.getUsername(), loginDto.getCompanyId());

        String username = loginDto.getCompanyId() + ":" + loginDto.getUsername();
        log.info("Getting the custom username from the loginDto inside the authController {}", username);

        Authentication authentication = this.authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
                  username,
                  loginDto.getPassword()
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
