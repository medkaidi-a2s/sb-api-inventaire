package dz.a2s.a2spreparation.api;

import dz.a2s.a2spreparation.dto.auth.AuthorizationDto;
import dz.a2s.a2spreparation.dto.response.SuccessResponseDto;
import dz.a2s.a2spreparation.entities.Company;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Tag(name = "Gestion des sites", description = "APIs permettant de récupérer la liste des sites avec leur methode de préparation")
public interface CompanyApi {

    @Operation(summary = "Récupération des sites", description = "Permet de récupérer tous les sites relatif à l'entreprise en question")
    @ApiResponse(responseCode = "200", description = "Sites récupérés avec succès")
    @GetMapping("/auth/companies")
    public ResponseEntity<SuccessResponseDto<List<Company>>> findAllCompanies();

    @Operation(summary = "Récupération de la méthode de préparation", description = "Récupération de la méthode de préparation des commandes qui peut être une des trois cas de figures, 1 - par commande, 2 - par zone, 3 - par prélèvement")
    @ApiResponse(responseCode = "200", description = "Méthode de préparation récupérée avec succès")
    @GetMapping("/api/company/method")
    public ResponseEntity<SuccessResponseDto<Integer>> getMethod();

    @Operation(summary = "Récupération du format d'impression", description = "Récupération du format d'impression - 1: Impression sur ticket ordinaire - 2: Impression ticket autocollant 50Lx60H")
    @ApiResponse(responseCode = "200", description = "Format d'impression récupéré avec succès")
    @GetMapping("/api/company/format-impression")
    public ResponseEntity<SuccessResponseDto<Integer>> getFormatImpression();

    @Operation(summary = "Récupération des autorisations", description = "Récupération des autorisations selon la liste définie dans la config de l'application (yml)")
    @ApiResponse(responseCode = "200", description = "Autorisations récupérées avec succès")
    @GetMapping("/api/company/authorizations")
    public ResponseEntity<SuccessResponseDto<List<AuthorizationDto>>> getAuthorizations();

}
