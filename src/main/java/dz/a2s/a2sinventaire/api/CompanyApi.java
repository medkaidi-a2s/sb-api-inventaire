package dz.a2s.a2sinventaire.api;

import dz.a2s.a2sinventaire.dto.auth.AuthorizationDto;
import dz.a2s.a2sinventaire.dto.response.SuccessResponseDto;
import dz.a2s.a2sinventaire.entities.Company;
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

    @Operation(summary = "Récupération de la méthode d'inventaire", description = "Récupération de la méthode d'inventaire (1: par emplacement, 2: par utilisateur)")
    @ApiResponse(responseCode = "200", description = "Méthode d'inventaire récupéré avec succès")
    @ApiResponse(responseCode = "404", description = "Méthode d'inventaire non initialisée")
    @GetMapping("/api/company/method-inventaire")
    public ResponseEntity<SuccessResponseDto<Integer>> getMethodInventaire();

    @Operation(summary = "Récupération des autorisations", description = "Récupération des autorisations selon la liste définie dans la config de l'application (yml)")
    @ApiResponse(responseCode = "200", description = "Autorisations récupérées avec succès")
    @GetMapping("/api/company/authorizations")
    public ResponseEntity<SuccessResponseDto<List<AuthorizationDto>>> getAuthorizations();

}
