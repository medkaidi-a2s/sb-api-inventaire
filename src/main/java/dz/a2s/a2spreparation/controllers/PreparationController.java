package dz.a2s.a2spreparation.controllers;

import dz.a2s.a2spreparation.dto.affectation.CmdIdDto;
import dz.a2s.a2spreparation.dto.affectation.CmdPrlvIdDto;
import dz.a2s.a2spreparation.dto.affectation.CmdZoneIdDto;
import dz.a2s.a2spreparation.dto.preparation.*;
import dz.a2s.a2spreparation.dto.response.SuccessResponseDto;
import dz.a2s.a2spreparation.entities.keys.StkListesId;
import dz.a2s.a2spreparation.entities.keys.VenteId;
import dz.a2s.a2spreparation.entities.views.Motif;
import dz.a2s.a2spreparation.services.PreparationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/preparation")
public class PreparationController {
    private final PreparationService preparationService;

    @GetMapping("/commandes")
    public ResponseEntity<SuccessResponseDto<List<PrpCdeUsrCodeDto>>> getCommandeParPreparateur(@RequestParam Optional<String> date) {
        String reqDate = date.orElse("");

        log.info("Entering the getCommandeParPreparateur method from the PreparationController with date {}", reqDate);

        List<PrpCdeUsrCodeDto> commandes = this.preparationService.getCommandes(reqDate);
        log.info("Commandes par préparateur fetched from the service with length {}", commandes.size());

        SuccessResponseDto<List<PrpCdeUsrCodeDto>> successResponseDto = new SuccessResponseDto<List<PrpCdeUsrCodeDto>>(
                200,
                "Listes des commandes par préparateur",
                commandes
        );

        return ResponseEntity.ok(successResponseDto);
    }

    @GetMapping("/commandes-preleve")
    public ResponseEntity<SuccessResponseDto<List<PrpCmdPrlvUsrCodeDto>>> getCommandesPrlvParPreparateur(@RequestParam Optional<String> date) {
        String reqDate = date.orElse("");

        log.info("Entering the getCommandesPrlvParPreparateur method from the PreparationController with date {}", reqDate);

        List<PrpCmdPrlvUsrCodeDto> commandes = this.preparationService.getCommandesPrlv(reqDate);
        log.info("Commandes prélevement par préparateur fetched from the service with length {}", commandes.size());

        SuccessResponseDto successResponseDto = new SuccessResponseDto<>(
                200,
                "Listes des commandes par préparateur",
                commandes
        );

        return ResponseEntity.ok(successResponseDto);
    }

    @GetMapping("/commande-preleve")
    public ResponseEntity<SuccessResponseDto<PrpCmdPrlvUsrCodeDto>> getOnePrlvCommande(@RequestParam String type, @RequestParam Integer annee, @RequestParam Integer id) throws Exception {
        log.info("Entering the getOnePrlvCommande from PreparationController with id : {} type : {} annee : {}", id, type, annee);

        if(id == 0 || type.isEmpty() || annee == 0)
            throw new Exception("Paramètre manquant ou invalide");

        PrpCmdPrlvUsrCodeDto commande = this.preparationService.getOneCmdPrlv(id, type, annee);

        SuccessResponseDto<PrpCmdPrlvUsrCodeDto> response = new SuccessResponseDto<>(
                200,
                "Commande par prélevement retournée",
                commande
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/commande-preleve/details")
    public ResponseEntity<SuccessResponseDto<List<LignePrlvDto>>> getDetailsVentePrlv(
            @RequestParam Integer cmpId,
            @RequestParam Integer id,
            @RequestParam String type,
            @RequestParam Integer annee
    ) throws Exception {
       log.info("Entering the getDetailsVenteprlv method from the PreparationController with cmpId : {} id : {} type : {} annee : {}", cmpId, id, type, annee);

       if(id == 0 || type.isEmpty() || cmpId == 0 || annee == 0)
           throw new Exception("Paramètre manquant ou invalide");

        List<LignePrlvDto> details = this.preparationService.getDetailsVentePrlv(
            new StkListesId(
                cmpId,
                id,
                type,
                annee
            )
        );

        SuccessResponseDto<List<LignePrlvDto>> response = new SuccessResponseDto<>(
                200,
                "Détails de la commande par prélévement",
                details
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/commande/details")
    public ResponseEntity<SuccessResponseDto<List<LigneDto>>> getDetailsVente(
            @RequestParam Integer cmpId,
            @RequestParam Integer id,
            @RequestParam String type,
            @RequestParam String stkCode
    ) {
        log.info("Entering the getDetailsVente method from the PreparationController");

        List<LigneDto> details = this.preparationService.getDetailsVente(new VenteId(
           cmpId,
           id,
           type,
           stkCode
        ));

        SuccessResponseDto<List<LigneDto>> response = new SuccessResponseDto<>(
                200,
                "Détails de la commande",
                details
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/commande-zone/details")
    public ResponseEntity<SuccessResponseDto<List<LigneZoneDto>>> getDetailsVenteZone(
            @RequestParam Integer cmpId,
            @RequestParam Integer id,
            @RequestParam String type,
            @RequestParam String stkCode,
            @RequestParam Integer zone
    ) throws Exception {
        log.info("Entering the getDetailsVenteZone method from the PreparationController with cmpId : {} venteId : {} type : {} stkCode : {} zone : {}", cmpId, id, type, stkCode, zone);

        if(cmpId == 0 || zone == 0 || id == 0 || type.isEmpty() || stkCode.isEmpty())
            throw new Exception("Paramètres manquant ou invalide");

        List<LigneZoneDto> details = this.preparationService.getDetailsVenteZone(new CmdZoneIdDto(
                cmpId,
                id,
                type,
                stkCode,
                zone
        ));

        SuccessResponseDto<List<LigneZoneDto>> response = new SuccessResponseDto<>(
                200,
                "Détails de la commande",
                details
        );

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/commande/start-prep")
    public ResponseEntity<SuccessResponseDto<Integer>> startPrepareCde(@RequestBody @Valid CmdIdDto commande) throws Exception {
        log.info("Entering the method startPrepareCde from the PreparationController");

        Integer response = this.preparationService.startPrepareCde(
                commande.getCmpId(),
                commande.getId(),
                commande.getType(),
                commande.getStkCode()
        );

        SuccessResponseDto<Integer> successResponseDto = new SuccessResponseDto<>(
                200,
                "Commande mis à jour avec succès",
                response
        );

        return ResponseEntity.ok(successResponseDto);
    }

    @PatchMapping("/commande-zone/start-prep")
    public ResponseEntity<SuccessResponseDto<Integer>> startPrepareZone(@RequestBody @Valid CmdZoneIdDto commande) throws Exception {
        log.info("Entering the method startPrepareZone from the PreparationController with {}", commande);

        Integer response = this.preparationService.startPrepareZone(
                commande.getCmpId(),
                commande.getId(),
                commande.getType(),
                commande.getStkCode(),
                commande.getZone()
        );

        SuccessResponseDto<Integer> successResponseDto = new SuccessResponseDto<>(
                200,
                "Commande mis à jour avec succès",
                response
        );

        return ResponseEntity.ok(successResponseDto);
    }

    @PatchMapping("/commande-preleve/start-prep")
    public ResponseEntity<SuccessResponseDto<Integer>> startPreparePrlv(@RequestBody @Valid CmdPrlvIdDto commande) throws Exception {
        log.info("Entering the method startPreparePrlv from the PreparationController");

        Integer response = this.preparationService.startPreparePrlv(
                commande.getCmpId(),
                commande.getId(),
                commande.getType(),
                commande.getAnnee()
        );

        SuccessResponseDto<Integer> successResponseDto = new SuccessResponseDto<>(
                200,
                "Commande mis à jour avec succès",
                response
        );
        return ResponseEntity.ok(successResponseDto);
    }

    @PatchMapping("/commande/set-prep-quantity")
    public ResponseEntity<SuccessResponseDto<Integer>> setPreparedQuantity(@RequestBody @Valid LigneQteDto ligne) throws Exception {
        log.info("Entering the setPreparedQuantity method from the PreparationController with {}", ligne);

        Integer response = this.preparationService.setPreparedQuantity(
                ligne.getCmpId(),
                ligne.getId(),
                ligne.getType(),
                ligne.getStkCode(),
                ligne.getNo(),
                ligne.getQte(),
                ligne.getMotif()
        );

        SuccessResponseDto successResponseDto = new SuccessResponseDto<>(
                200,
                "Quantité préparée mis à jour avec succès",
                response
        );

        return ResponseEntity.ok(successResponseDto);
    }

    @PatchMapping("/commande-zone/set-prep-quantity")
    public ResponseEntity<SuccessResponseDto<Integer>> setPreparedQuantityZone(@RequestBody @Valid LigneQteZoneDto ligne) throws Exception {
        log.info("Entering the setPreparedQuantity method from the PreparationController with {}", ligne);

        Integer response = this.preparationService.setPreparedQuantityZone(ligne);

        SuccessResponseDto successResponseDto = new SuccessResponseDto<>(
                200,
                "Quantité préparée mis à jour avec succès",
                response
        );

        return ResponseEntity.ok(successResponseDto);
    }

    @GetMapping("/motifs")
    public ResponseEntity<SuccessResponseDto<List<Motif>>> getAllMotifs() {
        log.info("Entering the method getAllMotifs from the PreparationController");

        List<Motif> motifs = this.preparationService.getAllMotif();
        log.info("Fetched the list of motifs from the service {}", motifs);

        SuccessResponseDto successResponseDto = new SuccessResponseDto<>(
                200,
                "Liste des motifs",
                motifs
        );

        return ResponseEntity.ok(successResponseDto);
    }

    @PatchMapping("/commande/set-prepared")
    public ResponseEntity<SuccessResponseDto<Integer>> setCommandePrepared(@RequestBody @Valid CmdIdDto id) throws Exception {
        log.info("Entering the setCommandePrepared method from the PreparationController with {}", id);

        Integer response = this.preparationService.setCommandePrepared(id);

        SuccessResponseDto<Integer> successResponseDto = new SuccessResponseDto<>(
                200,
                "Commande préparée avec succès",
                response
        );

        return ResponseEntity.ok(successResponseDto);
    }

    @PatchMapping("/commande-zone/set-prepared")
    public ResponseEntity<SuccessResponseDto<Integer>> setCommandeZonePrepared(@RequestBody @Valid CmdZoneIdDto id) throws Exception {
        log.info("Entering the setCommandeZonePrepared method from the PreparationController with {}", id);

        Integer response = this.preparationService.setCommandeZonePrepared(id);

        SuccessResponseDto<Integer> successResponseDto = new SuccessResponseDto<>(
                200,
                "Commande par zone préparée avec succès",
                response
        );

        return ResponseEntity.ok(successResponseDto);
    }

}
