package dz.a2s.a2spreparation.api;

import dz.a2s.a2spreparation.dto.CommandeResponseDto;
import dz.a2s.a2spreparation.dto.CommandeZoneResponseDto;
import dz.a2s.a2spreparation.dto.affectation.CmdIdDto;
import dz.a2s.a2spreparation.dto.affectation.CmdZoneIdDto;
import dz.a2s.a2spreparation.dto.controle.response.BonCommandeZoneDto;
import dz.a2s.a2spreparation.dto.preparation.LigneQteDto;
import dz.a2s.a2spreparation.dto.preparation.LigneQteZoneDto;
import dz.a2s.a2spreparation.dto.response.SuccessResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Tag(name = "Gestion du contrôle", description = "APIs relatifs aux opérations liées au contrôle des préparation")
public interface CheckingApi {

    @Operation(
            summary = "Récupération des commandes à contrôler par contrôleur",
            description = "Récupération des commandes à contrôler pour le contrôleur actuellement authentifié selon la date passée en paramètre, si une chaine vide est passée, on récupére toutes les commandes non contrôlées")
    @ApiResponse(responseCode = "200", description = "Commandes à contrôler récupérées avec succès")
    @GetMapping("/commandes")
    public ResponseEntity<SuccessResponseDto<List<CommandeResponseDto>>> getAllPreparedCommandes(@RequestParam Optional<String> date);

    @Operation(
            summary = "Récupération des commandes zone à contrôler par contrôleur",
            description = "Récupération des commandes zone à contrôler pour le contrôleur actuellement authentifié selon la date passée en paramètre, si une chaine vide est passée, on récupére toutes les commandes non contrôlées")
    @ApiResponse(responseCode = "200", description = "Commandes à contrôler récupérées avec succès")
    @GetMapping("/commandes-zone")
    public ResponseEntity<SuccessResponseDto<List<CommandeZoneResponseDto>>> getAllPreparedCommandesZone(@RequestParam Optional<String> date);

    @Operation(
            summary = "Récupération des bons de commandes par zone à contrôler par contrôleur (méthode 4)",
            description = "Récupération des bons de commandes par zone à contrôler pour le contrôleur actuellement authentifié selon la date passée en paramètre, si une chaine vide est passée, on récupére toutes les commandes non contrôlées")
    @ApiResponse(responseCode = "200", description = "Bons de commande à contrôler récupérées avec succès")
    @GetMapping("/bons-commandes-zone")
    public ResponseEntity<SuccessResponseDto<List<BonCommandeZoneDto>>> getPreparedBonCommandeZone(@RequestParam Optional<String> date);

    @Operation(
            summary = "Commencer le contrôle d'une commande",
            description = "Commencer le contrôle d'une commande via l'id de la vente")
    @ApiResponse(responseCode = "200", description = "Contrôle de la commande commencé avec succès")
    @PatchMapping("/commande/start-control")
    public ResponseEntity<SuccessResponseDto<Integer>> startControleCde(@RequestBody @Valid CmdIdDto commande) throws Exception;

    @Operation(
            summary = "Définition de la quantité contrôlée d'une commande",
            description = "Définir la quantité de la ligne contrôlée d'une commande")
    @ApiResponse(responseCode = "200", description = "Quantité définie avec succès")
    @PatchMapping("/commande/set-control-quantity")
    public ResponseEntity<SuccessResponseDto<Integer>> setControlledQuantity(@RequestBody @Valid LigneQteDto ligne) throws Exception;

    @Operation(
            summary = "Marquer la commande comme contrôlée",
            description = "Marquer une commande comme étant contrôlée après avoir contrôlé toutes les lignes")
    @ApiResponse(responseCode = "200", description = "Commande contrôlée avec succès")
    @PatchMapping("/commande/set-controlled")
    public ResponseEntity<SuccessResponseDto<Integer>> setCommandeControlled(@RequestBody @Valid CmdIdDto id) throws Exception;

    @Operation(
            summary = "Commencer le contrôle d'une commande par zone",
            description = "Commencer le contrôle d'une commande par zone via l'id de la vente")
    @ApiResponse(responseCode = "200", description = "Contrôle de la commande commencé avec succès")
    @PatchMapping("/commande-zone/start-control")
    public ResponseEntity<SuccessResponseDto<Integer>> startControleZone(@RequestBody @Valid CmdZoneIdDto commande) throws Exception;

    @Operation(
            summary = "Commencer le contrôle d'une commande globale par zone",
            description = "Commencer le contrôle d'une commande globale par zone via l'id de la vente (méthode 4)")
    @ApiResponse(responseCode = "200", description = "Contrôle de la commande commencé avec succès")
    @PatchMapping("/commande-zone/start-control-global")
    public ResponseEntity<SuccessResponseDto<Integer>> startControleCommandeZone(@RequestBody @Valid CmdIdDto commande) throws Exception;

    @Operation(
            summary = "Définition de la quantité contrôlée d'une commande par zone",
            description = "Définir la quantité de la ligne contrôlée d'une commande par zone")
    @ApiResponse(responseCode = "200", description = "Quantité définie avec succès")
    @PatchMapping("/commande-zone/set-control-quantity")
    public ResponseEntity<SuccessResponseDto<Integer>> setControlledQuantityZone(@RequestBody @Valid LigneQteZoneDto ligne) throws Exception;

    @Operation(
            summary = "Marquer une commande par zone comme contrôlée",
            description = "Marquer une commande par zone comme étant contrôlée après avoir contrôlé toutes les lignes")
    @ApiResponse(responseCode = "200", description = "Commande contrôlée avec succès")
    @PatchMapping("/commande-zone/set-controlled")
    public ResponseEntity<SuccessResponseDto<Integer>> setCommandeZoneControlled(@RequestBody @Valid CmdZoneIdDto id) throws Exception;

    @Operation(
            summary = "Marquer une commande globale par zone comme contrôlée",
            description = "Terminer le contrôle d'une commande globale par zone")
    @ApiResponse(responseCode = "200", description = "Commande globale contrôlée avec succès")
    @PatchMapping("/commande-zone/set-controlled-global")
    public ResponseEntity<SuccessResponseDto<Integer>> setCommandeZoneGlobalControlled(@RequestBody @Valid CmdIdDto id);

}
