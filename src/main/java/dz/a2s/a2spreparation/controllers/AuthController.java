package dz.a2s.a2spreparation.controllers;

import dz.a2s.a2spreparation.dto.auth.AuthResponseDto;
import dz.a2s.a2spreparation.dto.auth.LoginDto;
import dz.a2s.a2spreparation.repositories.UserEntityRepository;
import dz.a2s.a2spreparation.security.JWTGenerator;
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
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserEntityRepository userEntityRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTGenerator jwtGenerator;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody @Valid LoginDto loginDto) {
        log.info("Entering login method from AuthController with {}", loginDto);

        String username = loginDto.getCompanyId() + ":" + loginDto.getUsername();
        log.info("Getting the custom username from the loginDto inside the authController {}", username);

        Authentication authentication = this.authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
//                  loginDto.getUsername(),
                  username,
                  loginDto.getPassword()
          )
        );

        log.info("Authentication object created {}", authentication);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = this.jwtGenerator.generateToken(authentication);
        AuthResponseDto authResponseDto = new AuthResponseDto(token);
        return ResponseEntity.ok(authResponseDto);
    }

}
