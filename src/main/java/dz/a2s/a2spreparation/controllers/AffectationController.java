package dz.a2s.a2spreparation.controllers;

import dz.a2s.a2spreparation.dto.response.SuccessResponseDto;
import dz.a2s.a2spreparation.entities.views.PrpCdeZone;
import dz.a2s.a2spreparation.entities.views.PrpCommande;
import dz.a2s.a2spreparation.entities.views.PrpPrepareControle;
import dz.a2s.a2spreparation.services.AffectationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/affectation")
public class AffectationController {
    private final AffectationService affectationService;

    @GetMapping("listeCmdPrpZone")
    public ResponseEntity<SuccessResponseDto<List<PrpCdeZone>>> getListeCmdPrpZone() {
        log.info("Entering the getListeCmdPrpZone from the AffectationController");

        log.info("Fetching liste des commandes from the service");
        List<PrpCdeZone> listeCommandes = this.affectationService.getListCmdZones();
        log.info("Data fetched from the service length = {}", listeCommandes.size());

        SuccessResponseDto<List<PrpCdeZone>> successResponseDto = new SuccessResponseDto<List<PrpCdeZone>>(
                200,
                "Liste des commandes déjà affectées par zone",
                listeCommandes
        );

        return ResponseEntity.ok(successResponseDto);
    }

    @GetMapping("listCmdPrp")
    public ResponseEntity<SuccessResponseDto<List<PrpCommande>>> getListeCmdPrp() {
        log.info("Entering the getListeCmdPrp from the AffectationController");

        log.info("Fetching liste des commandes from the service");
        List<PrpCommande> listeCommandes = this.affectationService.getListCommande();
        log.info("Data fetched from the service length = {}", listeCommandes.size());

        SuccessResponseDto<List<PrpCommande>> successResponseDto = new SuccessResponseDto<>(
                200,
                "Liste des commandes à affecter",
                listeCommandes
        );

        return ResponseEntity.ok(successResponseDto);
    }

    @GetMapping("preparateurs")
    public ResponseEntity<SuccessResponseDto<List<PrpPrepareControle>>> getAllPreparateurs() {
        log.info("Entering the getAllPreparateurs method from the AffectationController");

        log.info("Fetching liste des préparateurs from the service");
        List<PrpPrepareControle> preparateurs = this.affectationService.getAllPreparateurs();
        log.info("Fetched la listes des préparateurs from the service with length {}", preparateurs.size());

        SuccessResponseDto<List<PrpPrepareControle>> successResponseDto = new SuccessResponseDto<>(
                200,
                "Liste des préparateurs",
                preparateurs
        );

        return ResponseEntity.ok(successResponseDto);
    }

    @GetMapping("controleurs")
    public ResponseEntity<SuccessResponseDto<List<PrpPrepareControle>>> getAllControleurs() {
        log.info("Entering the getAllControleurs method from the AffectationController");

        log.info("Fetching liste des contrôleurs from the service");
        List<PrpPrepareControle> controleurs = this.affectationService.getAllControleurs();
        log.info("Fetched la listes des contrôleurs from the service with length {}", controleurs.size());

        SuccessResponseDto<List<PrpPrepareControle>> successResponseDto = new SuccessResponseDto<>(
                200,
                "Liste des contrôleurs",
                controleurs
        );

        return ResponseEntity.ok(successResponseDto);
    }

}
