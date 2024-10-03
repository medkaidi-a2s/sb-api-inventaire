package dz.a2s.a2spreparation.controllers;

import dz.a2s.a2spreparation.dto.affectation.*;
import dz.a2s.a2spreparation.dto.response.SuccessResponseDto;
import dz.a2s.a2spreparation.entities.views.*;
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

    @GetMapping("commandes-zones")
    public ResponseEntity<SuccessResponseDto<List<AffCmdDto>>> getListeCmdPrpZone() {
        log.info("Entering the getListeCmdPrpZone from the AffectationController");

        log.info("Fetching liste des commandes from the service");
        List<AffCmdDto> commandes = this.affectationService.getListCmdZones();
        log.info("Data fetched from the service length = {}", commandes.size());

        SuccessResponseDto<List<AffCmdDto>> successResponseDto = new SuccessResponseDto<>(
                200,
                "Liste des commandes déjà affectées par zone",
                commandes
        );

        return ResponseEntity.ok(successResponseDto);
    }

    @GetMapping("commandes")
    public ResponseEntity<SuccessResponseDto<List<PrpCmdDto>>> getListeCmdPrp(@RequestParam String date) {
        log.info("Entering the getListeCmdPrp from the AffectationController date {}", date);

        log.info("Fetching liste des commandes from the service");
        List<PrpCmdDto> commandes = this.affectationService.getListCmd(date);
        log.info("Data fetched from the service length = {}", commandes.size());

        SuccessResponseDto<List<PrpCmdDto>> successResponseDto = new SuccessResponseDto<>(
                200,
                "Liste des commandes à affecter",
                commandes
        );

        return ResponseEntity.ok(successResponseDto);
    }

    @GetMapping("commandes/assigned")
    public ResponseEntity<SuccessResponseDto<List<AffCmdDto>>> getListeCmdAssigned(@RequestParam String date) {
        log.info("Entering the getListeCmdAssigned from the AffectationController date {}", date);

        log.info("Fetching liste des commandes affectées from the service");
        List<AffCmdDto> commandes = this.affectationService.getListCmdAssigned(date);
        log.info("Data fetched from the service length = {}", commandes.size());

        SuccessResponseDto<List<AffCmdDto>> successResponseDto = new SuccessResponseDto<>(
                200,
                "Liste des commandes déjà affectées",
                commandes
        );

        return ResponseEntity.ok(successResponseDto);
    }

    @GetMapping("commandes/preparateur-controleurs")
    public ResponseEntity<SuccessResponseDto<PrpCdePrepCont>> getCmdPrepCont(@RequestParam Integer id, @RequestParam String type, @RequestParam String stkCode) {
        log.info("Entering getCmdPrepCont method from the AffectationController with id {}", id);

        PrpCdePrepCont prpCdePrepCont = this.affectationService.getPrepCont(
                id,
                type,
                stkCode
        );

        SuccessResponseDto<PrpCdePrepCont> response = new SuccessResponseDto<>(
                200,
                "Préparateur et contrôleurs de la commande",
                prpCdePrepCont
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("commandes-preleve")
    public ResponseEntity<SuccessResponseDto<List<PrpCmdPrlvDto>>> getListeCmdPrlv(@RequestParam String date) {
        log.info("Entering the getListeCmdPrlv method from the AffectationController with date {}", date);

        log.info("Fetching data from the service");
        List<PrpCmdPrlvDto> commandes = this.affectationService.getListCmdPrlv(date);
        log.info("Fetched data from the service with length {}", commandes.size());

        SuccessResponseDto<List<PrpCmdPrlvDto>> successResponseDto = new SuccessResponseDto<>(
          200,
          "Liste des commandes à affecter par prélévement",
          commandes
        );

        return ResponseEntity.ok(successResponseDto);
    }

    @GetMapping("commandes-preleve/assigned")
    public ResponseEntity<SuccessResponseDto<List<AffCmdPrlvDto>>> getListCmdPrlvAssigned(@RequestParam String date) {
        log.info("Entering the getListCmdPrlvAssigned method from the AffectationController with date {}", date);

        log.info("Fetching data from the service with date {}", date);
        List<AffCmdPrlvDto> commandes = this.affectationService.getListCmdPrlvAssigned(date);
        log.info("Fetched data from the service with length {}", commandes.size());

        SuccessResponseDto<List<AffCmdPrlvDto>> successResponseDto = new SuccessResponseDto<>(
                200,
                "Liste des commandes par prélévement déjà affectées",
                commandes
        );

        return ResponseEntity.ok(successResponseDto);
    }

    @GetMapping("commandes-preleve/preparateur-controleurs")
    public ResponseEntity<SuccessResponseDto<PrpCdePrlvPrepCont>> getPrepContPrlv(@RequestParam Integer id, @RequestParam String type, @RequestParam Integer annee) {
        log.info("Entering the getPrepContPrlv method from the AffectationController");

        log.info("Fetching data from the service");
        PrpCdePrlvPrepCont prpCdePrlvPrepCont = this.affectationService.getPrepContPrlv(id, type, annee);
        log.info("Data fetched from the service {}", prpCdePrlvPrepCont);

        SuccessResponseDto<PrpCdePrlvPrepCont> response = new SuccessResponseDto<>(
                200,
                "Préparateur et contrôleurs de la commande par prélévement",
                prpCdePrlvPrepCont
        );

        return ResponseEntity.ok(response);
    }

    @PatchMapping("commandes-preleve/preparateur-controleurs")
    public ResponseEntity<SuccessResponseDto<PrpCdePrlvPrepCont>> editAffectCmdPrpPrlv(@RequestBody PrpCdePrlvPrepContDto dto) throws Exception{
        log.info("Entering the editAffectCmdPrpPrlv from the AffectationController");

        PrpCdePrlvPrepCont prpCdePrlvPrepCont = this.affectationService.editAffectCmdPrpPrlv(
                dto.getP_cmp(),
                dto.getP_slt_id(),
                dto.getP_slt_type(),
                dto.getP_slt_annee(),
                dto.getP_prp(),
                dto.getP_cnt1(),
                dto.getP_cnt2()
        );

        SuccessResponseDto<PrpCdePrlvPrepCont> response = new SuccessResponseDto<>(
                200,
                "Réaffectation effectuée avec succès",
                prpCdePrlvPrepCont
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/affect-commande")
    public ResponseEntity<SuccessResponseDto<ArrayList<AffectCmdResultDto>>> affectCommandePrp(@RequestBody List<AffectCmdRequestDto> commandes) {
        log.info("Entering the affectation method with {}", commandes);
        ArrayList<AffectCmdResultDto> tableau = new ArrayList<>();
        commandes.forEach(commande -> {
            AffectCmdResultDto response = this.affectationService.affectCmdPrp(
                commande.getP_cmp(),
                commande.getP_vnt(),
                commande.getP_stk(),
                commande.getP_type(),
                commande.getP_prp(),
                commande.getP_cnt1(),
                commande.getP_cnt2(),
                commande.getP_user(),
                commande.getReference()
            );
            tableau.add(response);
        });
        SuccessResponseDto<ArrayList<AffectCmdResultDto>> successResponseDto = new SuccessResponseDto<>(200, "Résultat de l'affectation des commandes", tableau);
        return ResponseEntity.ok(successResponseDto);
    }

    @PostMapping("/affect-preleve")
    public ResponseEntity<SuccessResponseDto<ArrayList<AffectCmdResultDto>>> affectCommandePrlv(@RequestBody List<AffectCmdPrlvReqDto> commandes) {
        log.info("Entering method affectCommandePrlv from the AffectatinController with {}", commandes);
        ArrayList<AffectCmdResultDto> tableau = new ArrayList<>();
        commandes.forEach(commande -> {
            AffectCmdResultDto response = this.affectationService.affectCmdPrpPrlv(
                    commande.getP_cmp(),
                    commande.getP_slt_id(),
                    commande.getP_slt_type(),
                    commande.getP_slt_annee(),
                    commande.getP_prp(),
                    commande.getP_cnt1(),
                    commande.getP_cnt2(),
                    commande.getP_user(),
                    commande.getReference()
            );
            tableau.add(response);
        });
        SuccessResponseDto<ArrayList<AffectCmdResultDto>> successResponseDto = new SuccessResponseDto<>(200, "Résultat de l'affectation des commandes", tableau);
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
