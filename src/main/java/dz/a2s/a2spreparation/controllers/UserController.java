package dz.a2s.a2spreparation.controllers;

import dz.a2s.a2spreparation.api.UserApi;
import dz.a2s.a2spreparation.dto.auth.AuthorizationDto;
import dz.a2s.a2spreparation.dto.response.SuccessResponseDto;
import dz.a2s.a2spreparation.services.AuthorizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user-data")
public class UserController implements UserApi {
    private final AuthorizationService authorizationService;

    public ResponseEntity<SuccessResponseDto<AuthorizationDto>> getAffectationAuthorization() {
        log.info("Entering getAffectationAuthorization from the UserController");

        AuthorizationDto response = this.authorizationService.getAffectationAuthorization();
        log.info("Affectation Authorization recieved from the service : {}", response);

        SuccessResponseDto<AuthorizationDto> successResponseDto = new SuccessResponseDto<AuthorizationDto>(
                200,
                "Autorisation d'affectation",
                response
        );
        return ResponseEntity.ok(successResponseDto);
    }

    public ResponseEntity<SuccessResponseDto<AuthorizationDto>> getPreparationAuthorization() {
        log.info("Entering the getPreparationAuthorization method from the UserController");

        AuthorizationDto response = this.authorizationService.getPreparationAuthorization();
        log.info("Preparation Authorization recieved from the service : {}", response);

        SuccessResponseDto<AuthorizationDto> successResponseDto = new SuccessResponseDto<AuthorizationDto>(
                200,
                "Autorisation de préparation",
                response
        );
        return ResponseEntity.ok(successResponseDto);
    }

    public ResponseEntity<SuccessResponseDto<AuthorizationDto>> getControlAuthorization() {
        log.info("Entering the getControlAuthorization method from the UserController");

        AuthorizationDto response = this.authorizationService.getControlAuthorization();
        log.info("Control Authorization recieved from the service : {}", response);

        SuccessResponseDto<AuthorizationDto> successResponseDto = new SuccessResponseDto<AuthorizationDto>(
                200,
                "Autorisation de contrôle",
                response
        );
        return ResponseEntity.ok(successResponseDto);
    }

    @Override
    public ResponseEntity<SuccessResponseDto<AuthorizationDto>> getStatisticsAuthorization() {
        log.info("Entering the getStatisticsAuthorization method from the UserController");

        AuthorizationDto response = this.authorizationService.getStatisticsAuthorization();
        log.info("Statistics Authorization recieved from the service : {}", response);

        SuccessResponseDto<AuthorizationDto> successResponseDto = new SuccessResponseDto<AuthorizationDto>(
                200,
                "Autorisation des statistiques",
                response
        );
        return ResponseEntity.ok(successResponseDto);
    }

}
