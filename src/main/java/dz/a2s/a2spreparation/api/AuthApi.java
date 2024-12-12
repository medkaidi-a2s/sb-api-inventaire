package dz.a2s.a2spreparation.api;

import dz.a2s.a2spreparation.dto.auth.AuthResponseDto;
import dz.a2s.a2spreparation.dto.auth.ChangePasswordDto;
import dz.a2s.a2spreparation.dto.auth.LoginDto;
import dz.a2s.a2spreparation.dto.response.SuccessResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Gestion de l'authentification", description="APIs pour la gestion de l'authentification des utilisateurs")
public interface AuthApi {


    @Operation(summary = "Authentification des utilisateurs", description = "Authentifier les utilisateurs via le username, companyId et mot de passe")
    @ApiResponse(responseCode = "200", description = "Utilisateur authentifié")
    @PostMapping("/login")
    public ResponseEntity<SuccessResponseDto<AuthResponseDto>> login(@RequestBody @Valid LoginDto loginDto);

    @Operation(summary = "Changement du mot de passe", description = "Changement du mot de passe de l'utilisateur authentifié")
    @ApiResponse(responseCode = "200", description = "Mot de passe changé avec succès")
    @PostMapping("/change-password")
    public ResponseEntity<SuccessResponseDto<Integer>> changePassword(@RequestBody @Valid ChangePasswordDto changePasswordDto) throws Exception;

}
