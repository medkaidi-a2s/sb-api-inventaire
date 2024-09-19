package dz.a2s.a2spreparation.controllers;

import dz.a2s.a2spreparation.dto.AuthorizationDto;
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
public class UserController {
    private final AuthorizationService authorizationService;

    @GetMapping("/authorizations/affectation")
    public ResponseEntity<SuccessResponseDto<AuthorizationDto>> getAffectationAuthorization() {
        log.info("Entering getAffectationAuthorization from the UserController");

        AuthorizationDto response = this.authorizationService.getAffectationAuthorization();
        log.info("Authorization retrieved from the service : {}", response);

        SuccessResponseDto<AuthorizationDto> successResponseDto = new SuccessResponseDto<AuthorizationDto>(
                200,
                "Autorisation d'affectation",
                response
        );
        return ResponseEntity.ok(successResponseDto);
    }

}
