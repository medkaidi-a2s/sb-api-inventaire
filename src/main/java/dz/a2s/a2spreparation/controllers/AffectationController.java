package dz.a2s.a2spreparation.controllers;

import dz.a2s.a2spreparation.dto.affectation.AffectCmdRequestDto;
import dz.a2s.a2spreparation.dto.response.SuccessResponseDto;
import dz.a2s.a2spreparation.entities.views.PrpCdeZone;
import dz.a2s.a2spreparation.entities.views.PrpCommande;
import dz.a2s.a2spreparation.entities.views.PrpPrepareControle;
import dz.a2s.a2spreparation.services.AffectationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @GetMapping("listCmdPrp/{status}")
    public ResponseEntity<SuccessResponseDto<List<PrpCommande>>> getListeCmdPrp(@PathVariable int status) {
        log.info("Entering the getListeCmdPrp from the AffectationController with status {}", status);

        log.info("Fetching liste des commandes from the service");
        List<PrpCommande> listeCommandes = this.affectationService.getListCommande(status);
        log.info("Data fetched from the service length = {}", listeCommandes.size());

        SuccessResponseDto<List<PrpCommande>> successResponseDto = new SuccessResponseDto<>(
                200,
                "Liste des commandes à affecter",
                listeCommandes
        );

        return ResponseEntity.ok(successResponseDto);
    }

    @PostMapping("/affectCommande")
    public ResponseEntity<SuccessResponseDto<ArrayList<Integer>>> affectCommandePrp(@RequestBody List<AffectCmdRequestDto> commandes) {
        log.info("Entering the affectation method with {}", commandes);
        ArrayList<Integer> tableau = new ArrayList<>();
        commandes.forEach(commande -> {
            Integer response = this.affectationService.affectCommandePrp(
                commande.getP_cmp(),
                commande.getP_vnt(),
                commande.getP_stk(),
                commande.getP_type(),
                commande.getP_prp(),
                commande.getP_cnt1(),
                commande.getP_cnt2(),
                commande.getP_user()
            );
            tableau.add(response);
        });
        SuccessResponseDto<ArrayList<Integer>> successResponseDto = new SuccessResponseDto<>(200, "essai d'une procédure", tableau);
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
