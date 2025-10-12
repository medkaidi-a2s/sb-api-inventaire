package dz.a2s.a2sinventaire.api;

import dz.a2s.a2sinventaire.dto.errorCodes.ErrorCodeResponse;
import dz.a2s.a2sinventaire.dto.response.SuccessResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Tag(name = "Gestion des codes d'erreur", description = "APIs relatifs aux opérations liées à la gestion des codes d'erreur")
public interface ErrorCodeApi {

    @Operation(
            summary = "Récupération et interpretation des codes d'erreur",
            description = "Récupération des codes d'erreur ainsi que les messages d'erreur qui leurs sont associés")
    @ApiResponse(responseCode = "200", description = "Codes d'erreur récupérés avec succès")
    @GetMapping("")
    public ResponseEntity<SuccessResponseDto<List<ErrorCodeResponse>>> getErrorCodes();

}
