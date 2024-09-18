package dz.a2s.a2spreparation.controllers;

import dz.a2s.a2spreparation.dto.response.SuccessResponseDto;
import dz.a2s.a2spreparation.services.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user-data")
public class UserController {
    private final AuthorizationService authorizationService;

    @GetMapping("/authorizations/affectation")
    public ResponseEntity<SuccessResponseDto<Integer>> getAffectationAuthorization() {
        Integer authorization = this.authorizationService.getAffectationAuthorization();
        SuccessResponseDto<Integer> successResponseDto = new SuccessResponseDto<Integer>(
                200,
                "Autorisation d'affectation",
                authorization
        );
        return ResponseEntity.ok(successResponseDto);
    }

}
