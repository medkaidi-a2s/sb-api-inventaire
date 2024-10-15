package dz.a2s.a2spreparation.controllers;

import dz.a2s.a2spreparation.dto.affectation.CmdIdDto;
import dz.a2s.a2spreparation.dto.affectation.CmdPrlvIdDto;
import dz.a2s.a2spreparation.dto.affectation.CmdZoneIdDto;
import dz.a2s.a2spreparation.dto.preparation.*;
import dz.a2s.a2spreparation.dto.response.SuccessResponseDto;
import dz.a2s.a2spreparation.entities.keys.StkListesId;
import dz.a2s.a2spreparation.entities.keys.VenteId;
import dz.a2s.a2spreparation.entities.keys.VentePrlvDetailsId;
import dz.a2s.a2spreparation.entities.views.VenteDetails;
import dz.a2s.a2spreparation.entities.views.VentePrlvDetails;
import dz.a2s.a2spreparation.services.PreparationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/preparation")
public class PreparationController {
    private final PreparationService preparationService;

    @GetMapping("/commandes")
    public ResponseEntity<SuccessResponseDto<List<PrpCdeUsrCodeDto>>> getCommandeParPreparateur(@RequestParam String date) {
        log.info("Entering the getCommandeParPreparateur method from the PreparationController with date {}", date);

        List<PrpCdeUsrCodeDto> commandes = this.preparationService.getCommandes(date);
        log.info("Commandes par préparateur fetched from the service with length {}", commandes.size());

        SuccessResponseDto<List<PrpCdeUsrCodeDto>> successResponseDto = new SuccessResponseDto<List<PrpCdeUsrCodeDto>>(
                200,
                "Listes des commandes par préparateur",
                commandes
        );

        return ResponseEntity.ok(successResponseDto);
    }

    @GetMapping("/commandes-preleve")
    public ResponseEntity<SuccessResponseDto<List<PrpCmdPrlvUsrCodeDto>>> getCommandesPrlvParPreparateur(@RequestParam String date) {
        log.info("Entering the getCommandesPrlvParPreparateur method from the PreparationController with date {}", date);

        List<PrpCmdPrlvUsrCodeDto> commandes = this.preparationService.getCommandesPrlv(date);
        log.info("Commandes prélevement par préparateur fetched from the service with length {}", commandes.size());

        SuccessResponseDto successResponseDto = new SuccessResponseDto<>(
                200,
                "Listes des commandes par préparateur",
                commandes
        );

        return ResponseEntity.ok(successResponseDto);
    }

    @GetMapping("/commande-preleve")
    public ResponseEntity<SuccessResponseDto<PrpCmdPrlvUsrCodeDto>> getOnePrlvCommande(@RequestParam String type, @RequestParam Integer annee, @RequestParam Integer id) {
        log.info("Entering the getOnePrlvCommande from PreparationController");

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
    ) {
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
    public ResponseEntity<SuccessResponseDto<List<LigneDto>>> getDetailsVenteZone(
            @RequestParam Integer cmpId,
            @RequestParam Integer id,
            @RequestParam String type,
            @RequestParam String stkCode
    ) {
        log.info("Entering the getDetailsVente method from the PreparationController");

        List<LigneDto> details = this.preparationService.getDetailsVenteZone(new VenteId(
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

    @PatchMapping("/commande/start-prep-cde")
    public ResponseEntity<SuccessResponseDto<Integer>> startPrepareCde(@RequestBody CmdIdDto commande) throws Exception {
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

    @PatchMapping("/commande/start-prep-zone")
    public ResponseEntity<SuccessResponseDto<Integer>> startPrepareZone(@RequestBody CmdZoneIdDto commande) throws Exception {
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

    @PatchMapping("/commande/start-prep-preleve")
    public ResponseEntity<SuccessResponseDto<Integer>> startPreparePrlv(@RequestBody CmdPrlvIdDto commande) throws Exception {
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
    public ResponseEntity<SuccessResponseDto<Integer>> setPreparedQuantity(@RequestBody LigneQteDto ligne) throws Exception {
        log.info("Entering the setPreparedQuantity method from the PreparationController with {}", ligne);

        Integer response = this.preparationService.setPreparedQuantity(
                ligne.getCmpId(),
                ligne.getId(),
                ligne.getType(),
                ligne.getStkCode(),
                ligne.getNo(),
                ligne.getQte()
        );

        SuccessResponseDto successResponseDto = new SuccessResponseDto<>(
                200,
                "Quantité préparée mis à jour avec succès",
                response
        );

        return ResponseEntity.ok(successResponseDto);
    }

}
