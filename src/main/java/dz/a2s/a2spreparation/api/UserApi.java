package dz.a2s.a2spreparation.api;

import dz.a2s.a2spreparation.dto.AuthorizationDto;
import dz.a2s.a2spreparation.dto.response.SuccessResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "Gestion des utilisateurs", description = "APIs relatifs aux opérations liée à la gestion des utilisateurs")
public interface UserApi {

    @Operation(summary = "Autorisation d'affectation", description = "Déterminer si l'utilisateur authentifié à accès à l'interface d'affectation")
    @ApiResponse(responseCode = "200", description = "Autorisation récupérée avec succés, si elle est égale à 1 cela veut dire que l'utilisateur peut accèder à l'interface d'affectation")
    @GetMapping("/authorizations/affectation")
    public ResponseEntity<SuccessResponseDto<AuthorizationDto>> getAffectationAuthorization();

    @Operation(summary = "Autorisation de préparation", description = "Déterminer si l'utilisateur authentifié à accès à l'interface de préparation")
    @ApiResponse(responseCode = "200", description = "Autorisation récupérée avec succés, si elle est égale à 1 cela veut dire que l'utilisateur peut accèder à l'interface de préparation")
    @GetMapping("/authorizations/preparation")
    public ResponseEntity<SuccessResponseDto<AuthorizationDto>> getPreparationAuthorization();

}
