package dz.a2s.a2spreparation.controllers;

import dz.a2s.a2spreparation.api.InventaireApi;
import dz.a2s.a2spreparation.dto.common.ListResponse;
import dz.a2s.a2spreparation.dto.inventaire.request.InventaireLineRequest;
import dz.a2s.a2spreparation.dto.inventaire.request.SaisiRequest;
import dz.a2s.a2spreparation.dto.inventaire.response.ComptageAccessResponse;
import dz.a2s.a2spreparation.dto.inventaire.response.InventaireLineResponse;
import dz.a2s.a2spreparation.dto.inventaire.response.SaisiResponse;
import dz.a2s.a2spreparation.dto.response.PaginatedResponse;
import dz.a2s.a2spreparation.dto.response.SuccessResponseDto;
import dz.a2s.a2spreparation.entities.Inventaire;
import dz.a2s.a2spreparation.services.InventaireService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/inventaires")
public class InventaireController implements InventaireApi {

    private final InventaireService inventaireService;

    @Override
    public ResponseEntity<SuccessResponseDto<List<Inventaire>>> getListInventaires() {
        log.info("| Entry | InventaireController.getListInventaires()");

        var inventaires = this.inventaireService.getListInventaires();
        log.info("Fetched the list of inventaires from the service | inventaires={}", inventaires);

        var response = new SuccessResponseDto<>(
                200,
                "Liste des inventaires récupérée avec succès",
                inventaires
        );

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<SuccessResponseDto<List<ListResponse>>> getListComptages() {
        log.info("| Entry | InventaireController.getListComptages()");

        var comptages = this.inventaireService.getListComptages();
        log.info("Fetched the list of comptages from the service | comptages={}", comptages);

        var response = new SuccessResponseDto<>(
                200,
                "Liste des comptages récupérée avec succès",
                comptages
        );

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<SuccessResponseDto<ComptageAccessResponse>> getComptageAccess(@PathVariable("id") Integer invId) {
        log.info("| Entry | InventaireController.getComptageAccess | Args | invId={}", invId);

        var access = this.inventaireService.getComptageAccess(invId);
        log.info("Fetched the comptage access from the service | access={}", access);

        var response = new SuccessResponseDto<>(
                200,
                "Droits d'accès au comptage récupérés avec succès",
                access
        );

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<SuccessResponseDto<String>> checkEmplacement(@PathVariable("emplacement") String emplacement) {
        log.info("| Entry | InventaireController.checkEmplacement | Args | emplacement={}", emplacement);

        var value = this.inventaireService.checkEmplacement(emplacement);
        log.info("Checked the inventory placement via the service | value = {}", value);

        var response = new SuccessResponseDto<>(
                200,
                "Emplacement existant",
                value
        );

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<PaginatedResponse<InventaireLineResponse>> getInventaireLines(@RequestBody InventaireLineRequest request) {
        log.info("| Entry | InventaireController.getInventaireLines | Args | request={}", request);

        var lines = this.inventaireService.getInventaireLines(
                request.getInvId(),
                request.getComptage(),
                request.getEmplacement(),
                request.getStockZero(),
                request.getSearch(),
                request.getPage()
        );
        log.info("Fetched the inventory lines from the service | lines.size={}", lines.data().size());

        var response = new PaginatedResponse<>(
                200,
                "Stocks récupéré avec succès - page = " + request.getPage() + " - size = " + 10,
                lines.data(),
                lines.totalRecords(),
                lines.totalPages(),
                lines.currentPage(),
                lines.pageSize()
        );
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<SuccessResponseDto<SaisiResponse>> saisirInventaire(@RequestBody @Valid SaisiRequest request) {
        log.info("| Entry | InventaireController.saisirInventaire | Args | request={}", request);

        var data = this.inventaireService.saisirInventaire(request);
        log.info("Persisted inventaire data via the service | data={}", data);

        var response = new SuccessResponseDto<>(
                200,
                "Inventaire saisi avec succès",
                data
        );

        return ResponseEntity.ok(response);
    }
}
