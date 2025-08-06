package dz.a2s.a2spreparation.controllers;

import dz.a2s.a2spreparation.api.PreparationApi;
import dz.a2s.a2spreparation.dto.CommandeResponseDto;
import dz.a2s.a2spreparation.dto.affectation.CmdIdDto;
import dz.a2s.a2spreparation.dto.affectation.CmdPrlvIdDto;
import dz.a2s.a2spreparation.dto.affectation.CmdZoneIdDto;
import dz.a2s.a2spreparation.dto.preparation.*;
import dz.a2s.a2spreparation.dto.preparation.request.AddLotRequest;
import dz.a2s.a2spreparation.dto.preparation.request.AvailableLotsRequest;
import dz.a2s.a2spreparation.dto.preparation.request.ReplaceLotRequest;
import dz.a2s.a2spreparation.dto.preparation.response.ProductLotDto;
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
public class PreparationController implements PreparationApi {
    private final PreparationService preparationService;

    public ResponseEntity<SuccessResponseDto<List<CommandeResponseDto>>> getCommandeParPreparateur(@RequestParam Optional<String> date) {
        String reqDate = date.orElse("");

        log.info("Point d'entrée à la méthode getCommandeParPreparateur du PreparationController avec date {}", reqDate);

        List<CommandeResponseDto> commandes = this.preparationService.getCommandes(reqDate);
        log.info("Récupération de la liste des commandes par préparateur à partir du service avec size : {}", commandes.size());

        SuccessResponseDto<List<CommandeResponseDto>> successResponseDto = new SuccessResponseDto<List<CommandeResponseDto>>(
                200,
                "Listes des commandes par préparateur",
                commandes
        );

        return ResponseEntity.ok(successResponseDto);
    }

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

    public ResponseEntity<SuccessResponseDto<List<LigneZoneDto>>> getDetailsVenteZone(
            @RequestParam Integer cmpId,
            @RequestParam Integer id,
            @RequestParam String type,
            @RequestParam String stkCode,
            @RequestParam Optional<Integer> zone
    ) throws Exception {
        log.info("Entering the getDetailsVenteZone method from the PreparationController with cmpId : {} venteId : {} type : {} stkCode : {} zone : {}", cmpId, id, type, stkCode, zone);

        if(cmpId == 0 ||  id == 0 || type.isEmpty() || stkCode.isEmpty())
            throw new Exception("Paramètres manquant ou invalide");

        List<LigneZoneDto> details = this.preparationService.getDetailsVenteZone(new CmdZoneIdDto(
                cmpId,
                id,
                type,
                stkCode,
                zone.orElse(null)
        ));

        SuccessResponseDto<List<LigneZoneDto>> response = new SuccessResponseDto<>(
                200,
                "Détails de la commande",
                details
        );

        return ResponseEntity.ok(response);
    }

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

    @Override
    public ResponseEntity<SuccessResponseDto<CommandeReceiptData>> getCommandeReceiptData(@RequestBody @Valid CmdIdDto commande) {
        log.info("| Entry | PreparationController.getCommandeReceiptData | Args | commande={}", commande);

        var receiptData = this.preparationService.getReceiptParCommande(commande);
        log.trace("Fetched receipt data from the service | receiptData={}", receiptData);

        var response = new SuccessResponseDto<>(
                200,
                "Données du ticket récupérées avec succès",
                receiptData
        );

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<SuccessResponseDto<CommandeReceiptData>> getZoneReceiptData(@RequestBody @Valid CmdZoneIdDto commande) {
        log.info("| Entry | PreparationController.getZoneReceiptData | Args | commande={}", commande);

        var receiptData = this.preparationService.getReceiptParZone(commande);
        log.trace("Fetched receipt data from the service | receptData={}", receiptData);

        var response = new SuccessResponseDto<>(
                200,
                "Données du ticket récupérées avec succès",
                receiptData
        );

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<SuccessResponseDto<Integer>> deleteLigneCommande(@RequestBody @Valid LigneVenteDto id) {
        log.info("| Entry | PreparationController.deleteLigneCommande | Args | id={}", id);

        var response = this.preparationService.deleteLigneCommande(id);
        log.info("Fetched the response from the service | response={}", response);

        var successResponseDto = new SuccessResponseDto<>(
                200,
                "Ligne de commande supprimée avec succès",
                response
        );

        return ResponseEntity.ok(successResponseDto);
    }

    @Override
    public ResponseEntity<SuccessResponseDto<Integer>> editQuantityCommande(@RequestBody @Valid LigneQteDto id) {
        log.info("| Entry | PreparationController.editQuantityCommande | Args | id={}", id);

        var response = this.preparationService.editQuantityCommande(id);
        log.info("Fetched the response from the service | response={}", response);

        var successResponseDto = new SuccessResponseDto<>(
                200,
                "Quantité de la ligne modifiée avec succès",
                response
        );

        return ResponseEntity.ok(successResponseDto);
    }

    @Override
    public ResponseEntity<SuccessResponseDto<List<ProductLotDto>>> getAvailableLots(@RequestBody AvailableLotsRequest request) {
        log.info("| Entry | PreparationController.getAvailableLots | Args | request={}", request);

        var lots = this.preparationService.getAvailableLots(request.getCmpId(), request.getMedId(), request.getOldLotId(), request.getQte());
        log.info("Fetched the available lots from the service | lots.size={}", lots.size());

        var successResponseDto = new SuccessResponseDto<>(
                200,
                "Lots disponibles",
                lots
        );

        return ResponseEntity.ok(successResponseDto);
    }

    @Override
    public ResponseEntity<SuccessResponseDto<Integer>> replaceProductLot(@RequestBody @Valid ReplaceLotRequest request) {
        log.info("| Entry | PreparationController.replaceProductLot | Args | request={}", request);

        var response = this.preparationService.replaceProductLot(request);
        log.info("Fetched the response from the service | response={}", response);

        var successResponseDto = new SuccessResponseDto<>(
                200,
                "Lot remplacé avec succès",
                response
        );

        return ResponseEntity.ok(successResponseDto);
    }

    @Override
    public ResponseEntity<SuccessResponseDto<Integer>> addProductLot(@RequestBody @Valid AddLotRequest request) {
        log.info("| Entry | PreparationController.addProductLot | Args | request={}", request);

        var response = this.preparationService.addProductLot(request);
        log.info("Fetched the response from the service | response={}", response);

        var successResponseDto = new SuccessResponseDto<>(
                200,
                "Lot ajouté avec succès",
                response
        );

        return ResponseEntity.ok(successResponseDto);
    }

}
