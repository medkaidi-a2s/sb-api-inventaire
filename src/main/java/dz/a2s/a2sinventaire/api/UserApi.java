package dz.a2s.a2sinventaire.api;

import dz.a2s.a2sinventaire.dto.auth.AuthorizationDto;
import dz.a2s.a2sinventaire.dto.response.SuccessResponseDto;
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

    @Operation(summary = "Autorisation de contrôle", description = "Déterminer si l'utilisateur authentifié à accès à l'interface du contrôle")
    @ApiResponse(responseCode = "200", description = "Autorisation récupérée avec succés, si elle est égale à 1 cela veut dire que l'utilisateur peut accèder à l'interface du contrôle")
    @GetMapping("/authorizations/control")
    public ResponseEntity<SuccessResponseDto<AuthorizationDto>> getControlAuthorization();

    @Operation(summary = "Autorisation pour les statistiques", description = "Déterminer si l'utilisateur authentifié à accès à l'interface des statistiques")
    @ApiResponse(responseCode = "200", description = "Autorisation récupérée avec succés, si elle est égale à 1 cela veut dire que l'utilisateur peut accèder à l'interface des statistiques")
    @GetMapping("/authorizations/statistics")
    public ResponseEntity<SuccessResponseDto<AuthorizationDto>> getStatisticsAuthorization();

}
