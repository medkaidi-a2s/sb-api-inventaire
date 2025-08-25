package dz.a2s.a2spreparation.api;

import dz.a2s.a2spreparation.dto.common.ListResponse;
import dz.a2s.a2spreparation.dto.inventaire.response.ComptageAccessResponse;
import dz.a2s.a2spreparation.dto.response.PaginatedResponse;
import dz.a2s.a2spreparation.dto.response.SuccessResponseDto;
import dz.a2s.a2spreparation.dto.stock.response.StockDto;
import dz.a2s.a2spreparation.entities.Inventaire;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

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

}
