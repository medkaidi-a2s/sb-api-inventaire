package dz.a2s.a2spreparation.api;

import dz.a2s.a2spreparation.dto.common.ListResponse;
import dz.a2s.a2spreparation.dto.inventaire.request.InventaireLineRequest;
import dz.a2s.a2spreparation.dto.inventaire.request.SaisiEcartRequest;
import dz.a2s.a2spreparation.dto.inventaire.request.SaisiRequest;
import dz.a2s.a2spreparation.dto.inventaire.response.ComptageAccessResponse;
import dz.a2s.a2spreparation.dto.inventaire.response.EcartLineResponse;
import dz.a2s.a2spreparation.dto.inventaire.response.InventaireLineResponse;
import dz.a2s.a2spreparation.dto.inventaire.response.SaisiResponse;
import dz.a2s.a2spreparation.dto.response.PaginatedResponse;
import dz.a2s.a2spreparation.dto.response.SuccessResponseDto;
import dz.a2s.a2spreparation.dto.stock.response.StockDto;
import dz.a2s.a2spreparation.entities.Inventaire;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Tag(name = "Gestion des inventaires", description = "APIs pour les opérations relatives aux inventaires")
public interface InventaireApi {

    @Operation(summary = "Récupération de la liste des inventaires", description = "Récupération de la liste des inventaires en cours")
    @ApiResponse(responseCode = "200", description = "Inventaires récupérés avec succès")
    @GetMapping("/campagnes")
    public ResponseEntity<SuccessResponseDto<List<Inventaire>>> getListInventaires();

    @Operation(summary = "Récupération de la liste des comptages", description = "Récupération de la liste des comptages")
    @ApiResponse(responseCode = "200", description = "Comptages récupérés avec succès")
    @GetMapping("/comptages")
    public ResponseEntity<SuccessResponseDto<List<ListResponse>>> getListComptages();

    @Operation(summary = "Récupération des droits d'accès au comptage", description = "Récupération des droits d'accès aux comptages des inventaires")
    @ApiResponse(responseCode = "200", description = "Droits récupérés avec succès")
    @GetMapping("/{id}/comptages-access")
    public ResponseEntity<SuccessResponseDto<ComptageAccessResponse>> getComptageAccess(Integer invId);

    @Operation(summary = "Vérification de l'emplacement d'inventaire", description = "Vérifier si l'emplacement d'un inventaire existe")
    @ApiResponse(responseCode = "200", description = "Emplacement existant")
    @ApiResponse(responseCode = "204", description = "Emplacement inexistant")
    @GetMapping("/emplacements/{emplacement}")
    public ResponseEntity<SuccessResponseDto<String>> checkEmplacement(String emplacement);

    @Operation(summary = "Récupération des produits à inventorier", description = "Récupération des produits à inventorier")
    @ApiResponse(responseCode = "200", description = "Produits récupérés avec succès")
    @PostMapping("/details")
    public ResponseEntity<PaginatedResponse<InventaireLineResponse>> getInventaireLines(InventaireLineRequest request);

    @Operation(summary = "Récupération des produits pour le traitement des écarts", description = "Récupération des produits pour le traitement des écarts")
    @ApiResponse(responseCode = "200", description = "Produits récupérés avec succès")
    @PostMapping("/traitement-ecarts")
    public ResponseEntity<PaginatedResponse<EcartLineResponse>> getEcartLines(InventaireLineRequest request);

    @Operation(summary = "Saisir une ligne d'inventaire", description = "Saisir la quantité et le motif d'un produit pour l'inventaire")
    @ApiResponse(responseCode = "200", description = "Inventaire saisi avec succès")
    @PostMapping("/saisir")
    public ResponseEntity<SuccessResponseDto<SaisiResponse>> saisirInventaire(SaisiRequest request);

    @Operation(summary = "Rectification de l'écart", description = "Rectification de la quantité présentant un écart entre comptage 1 et 2")
    @ApiResponse(responseCode = "200", description = "Ecart réctifié avec succès")
    @PostMapping("/update-ecart")
    public ResponseEntity<SuccessResponseDto<Integer>> updateEcartLine(SaisiEcartRequest request);

}
