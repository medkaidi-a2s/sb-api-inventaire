package dz.a2s.a2spreparation.api;

import dz.a2s.a2spreparation.dto.CommandeResponseDto;
import dz.a2s.a2spreparation.dto.affectation.CmdIdDto;
import dz.a2s.a2spreparation.dto.affectation.CmdPrlvIdDto;
import dz.a2s.a2spreparation.dto.affectation.CmdZoneIdDto;
import dz.a2s.a2spreparation.dto.preparation.*;
import dz.a2s.a2spreparation.dto.response.SuccessResponseDto;
import dz.a2s.a2spreparation.entities.views.Motif;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Tag(name = "Gestion de la préparation", description = "APIs relatifs à tous les aspects de la préparation des commandes")
public interface PreparationApi {

    @Operation(
            summary = "Récupération des commandes par préparateurs",
            description = "Récupération des commandes à préparer pour le préparateur actuellement authentifié selon la date passée en paramètres, si une chaine vide est passée, on récupére toutes les commandes non préparées")
    @ApiResponse(responseCode = "200", description = "Commandes à préparer récupérées avec succès")
    @GetMapping("/commandes")
    public ResponseEntity<SuccessResponseDto<List<CommandeResponseDto>>> getCommandeParPreparateur(@RequestParam Optional<String> date);

    @Operation(
            summary = "Récupération des commandes de prélèvement par préparateurs",
            description = "Récupération des commandes de prélèvement à préparer pour le préparateur actuellement authentifié selon la date passée en paramètres, si une chaine vide est passée, on récupére toutes les commandes non préparées")
    @ApiResponse(responseCode = "200", description = "Commandes de prélèvement à préparer récupérées avec succès")
    @GetMapping("/commandes-preleve")
    public ResponseEntity<SuccessResponseDto<List<PrpCmdPrlvUsrCodeDto>>> getCommandesPrlvParPreparateur(@RequestParam Optional<String> date);

    @Operation(
            summary = "Récupération d'une seule commande par prélèvement",
            description = "Récupération des données d'une seule commande par prélèvement via le id, type, année de la vente")
    @ApiResponse(responseCode = "200", description = "Données de la commande spécifiée récupérées avec succès")
    @GetMapping("/commande-preleve")
    public ResponseEntity<SuccessResponseDto<PrpCmdPrlvUsrCodeDto>> getOnePrlvCommande(@RequestParam String type, @RequestParam Integer annee, @RequestParam Integer id) throws Exception;

    @Operation(
            summary = "Récupération du détail d'une commande par prélèvement",
            description = "Récupération du détail d'une seule commande par prélèvement via le companyId, id, type, année de la vente")
    @ApiResponse(responseCode = "200", description = "Détail de la commande spécifiée récupérées avec succès")
    @GetMapping("/commande-preleve/details")
    public ResponseEntity<SuccessResponseDto<List<LignePrlvDto>>> getDetailsVentePrlv(
            @RequestParam Integer cmpId,
            @RequestParam Integer id,
            @RequestParam String type,
            @RequestParam Integer annee
    ) throws Exception;

    @Operation(
            summary = "Récupération du détail d'une commande",
            description = "Récupération du détail d'une seule commande via le companyId, id, type, code stock de la vente")
    @ApiResponse(responseCode = "200", description = "Détail de la commande spécifiée récupérées avec succès")
    @GetMapping("/commande/details")
    public ResponseEntity<SuccessResponseDto<List<LigneDto>>> getDetailsVente(
            @RequestParam Integer cmpId,
            @RequestParam Integer id,
            @RequestParam String type,
            @RequestParam String stkCode
    );

    @Operation(
            summary = "Récupération du détail d'une commande par zone",
            description = "Récupération du détail d'une seule commande par zone via le companyId, id, type, code stock et zone de la vente")
    @ApiResponse(responseCode = "200", description = "Détail de la commande spécifiée récupérées avec succès")
    @GetMapping("/commande-zone/details")
    public ResponseEntity<SuccessResponseDto<List<LigneZoneDto>>> getDetailsVenteZone(
            @RequestParam Integer cmpId,
            @RequestParam Integer id,
            @RequestParam String type,
            @RequestParam String stkCode,
            @RequestParam Integer zone
    ) throws Exception;

    @Operation(
            summary = "Commencer la préparation d'une commande",
            description = "Commencer la préparation d'une commande en changeant le statut en passant l'identifiant de la vente")
    @ApiResponse(responseCode = "200", description = "Préparation commencée avec succès")
    @PatchMapping("/commande/start-prep")
    public ResponseEntity<SuccessResponseDto<Integer>> startPrepareCde(@RequestBody @Valid CmdIdDto commande) throws Exception;

    @Operation(
            summary = "Commencer la préparation d'une commande par zone",
            description = "Affectation de la commande à l'utilisateur authentifié et début de la préparation pour la commande par zone en passant l'identifiant de la vente")
    @ApiResponse(responseCode = "200", description = "Commande par zone affectée et préparation commencée avec succès")
    @PatchMapping("/commande-zone/start-prep")
    public ResponseEntity<SuccessResponseDto<Integer>> startPrepareZone(@RequestBody @Valid CmdZoneIdDto commande) throws Exception;

    @Operation(
            summary = "Commencer la préparation d'une commande par prélèvement",
            description = "Commencer la préparation pour une commande par prélèvement en passant l'identifiant de la vente")
    @ApiResponse(responseCode = "200", description = "Préparation de la commande par prélèvement commencée avec succès")
    @PatchMapping("/commande-preleve/start-prep")
    public ResponseEntity<SuccessResponseDto<Integer>> startPreparePrlv(@RequestBody @Valid CmdPrlvIdDto commande) throws Exception;

    @Operation(
            summary = "Définition de la quantité préparée",
            description = "Définir la quantité préparé d'une ligne d'une commande")
    @ApiResponse(responseCode = "200", description = "Quantité définie avec succès")
    @PatchMapping("/commande/set-prep-quantity")
    public ResponseEntity<SuccessResponseDto<Integer>> setPreparedQuantity(@RequestBody @Valid LigneQteDto ligne) throws Exception;

    @Operation(
            summary = "Définition de la quantité préparée pour les commandes zone",
            description = "Définir la quantité préparé d'une ligne d'une commande par zone")
    @ApiResponse(responseCode = "200", description = "Quantité définie avec succès")
    @PatchMapping("/commande-zone/set-prep-quantity")
    public ResponseEntity<SuccessResponseDto<Integer>> setPreparedQuantityZone(@RequestBody @Valid LigneQteZoneDto ligne) throws Exception;

    @Operation(
            summary = "Récupération des motifs de préparation",
            description = "Récupération des motifs de préparation")
    @ApiResponse(responseCode = "200", description = "Motifs récupérés avec succès")
    @GetMapping("/motifs")
    public ResponseEntity<SuccessResponseDto<List<Motif>>> getAllMotifs();

    @Operation(
            summary = "Marquer une commande comme préparée",
            description = "Marquer la commande comme préparée via l'id de la vente")
    @ApiResponse(responseCode = "200", description = "Commande marquée comme préparée")
    @PatchMapping("/commande/set-prepared")
    public ResponseEntity<SuccessResponseDto<Integer>> setCommandePrepared(@RequestBody @Valid CmdIdDto id) throws Exception;

    @Operation(
            summary = "Marquer une commande par zone comme préparée",
            description = "Marquer la commande zone comme préparée via l'id de la vente")
    @ApiResponse(responseCode = "200", description = "Commande marquée comme préparée")
    @PatchMapping("/commande-zone/set-prepared")
    public ResponseEntity<SuccessResponseDto<Integer>> setCommandeZonePrepared(@RequestBody @Valid CmdZoneIdDto id) throws Exception;

    @Operation(
            summary = "Récupération des données du ticket d'une commande par facture",
            description = "Récupératin des données du ticket en passant l'identifiant de la vente")
    @ApiResponse(responseCode = "200", description = "Données récupérées avec succès")
    @PostMapping("/commande/receipt-data")
    public ResponseEntity<SuccessResponseDto<CommandeReceiptData>> getCommandeReceiptData(CmdIdDto commande);

    @Operation(
            summary = "Récupération des données du ticket d'une commande par zone",
            description = "Récupératin des données du ticket en passant l'identifiant de la vente")
    @ApiResponse(responseCode = "200", description = "Données récupérées avec succès")
    @PostMapping("/commande-zone/receipt-data")
    public ResponseEntity<SuccessResponseDto<CommandeReceiptData>> getZoneReceiptData(CmdZoneIdDto commande);

}
