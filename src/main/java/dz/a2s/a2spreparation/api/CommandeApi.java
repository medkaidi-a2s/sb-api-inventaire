package dz.a2s.a2spreparation.api;

import dz.a2s.a2spreparation.dto.CommandeResponseDto;
import dz.a2s.a2spreparation.dto.CommandeZoneResponseDto;
import dz.a2s.a2spreparation.dto.affectation.CmdColisageDto;
import dz.a2s.a2spreparation.dto.affectation.CmdIdDto;
import dz.a2s.a2spreparation.dto.affectation.CmdZoneColisageDto;
import dz.a2s.a2spreparation.dto.affectation.CmdZoneIdDto;
import dz.a2s.a2spreparation.dto.commande.request.CommandeColisageRequest;
import dz.a2s.a2spreparation.dto.commande.request.ListeEtiquetteRequest;
import dz.a2s.a2spreparation.dto.commande.request.UpdateColisageRequest;
import dz.a2s.a2spreparation.dto.commande.response.ColisageDto;
import dz.a2s.a2spreparation.dto.commande.response.CommandeColisageResponse;
import dz.a2s.a2spreparation.dto.commande.response.ListeEtiquettesResponse;
import dz.a2s.a2spreparation.dto.response.PaginatedResponse;
import dz.a2s.a2spreparation.dto.response.SuccessResponseDto;
import dz.a2s.a2spreparation.entities.Bac;
import dz.a2s.a2spreparation.entities.Colis;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Tag(name = "Gestion des commandes en général", description = "APIs pour la gestion des opération communes et générales relatives aux commandes")
public interface CommandeApi {

    @Operation(summary = "Récupération de la liste des commandes", description = "Récupération de la liste des commandes pour génération des étiquettes")
    @ApiResponse(responseCode = "200", description = "Liste des commandes récupérée avec succès")
    @PostMapping("/liste-commandes")
    ResponseEntity<PaginatedResponse<CommandeColisageResponse>> getCommandesColisage(CommandeColisageRequest request);

    @Operation(summary = "Récupération des commandes préparées par zones", description = "Récupération des commandes déjà préparées par zones")
    @ApiResponse(responseCode = "200", description = "Commandes par zones récupérées avec succès")
    @GetMapping("/commandes-zones")
    ResponseEntity<SuccessResponseDto<List<CommandeZoneResponseDto>>> getCommandesZone(Optional<String> date);

    @Operation(summary = "Récupération des commandes préparées", description = "Récupération des commandes déjà préparées")
    @ApiResponse(responseCode = "200", description = "Commandes récupérées avec succès")
    @GetMapping("/commandes")
    ResponseEntity<SuccessResponseDto<List<CommandeResponseDto>>> getControlledCommandes(Optional<String> date);

    @Operation(summary = "Récupération de toutes les commandes", description = "Récupération de toutes les commandes")
    @ApiResponse(responseCode = "200", description = "Commandes récupérées avec succès")
    @GetMapping("/all-commandes")
    ResponseEntity<SuccessResponseDto<List<CommandeResponseDto>>> getAllCommandes(Optional<String> date, Optional<String> search);

    @Operation(summary = "Récupération de toutes les commandes par zone", description = "Récupération de toutes les commandes par zone")
    @ApiResponse(responseCode = "200", description = "Commandes récupérées avec succès")
    @GetMapping("/all-commandes-zones")
    ResponseEntity<SuccessResponseDto<List<CommandeZoneResponseDto>>> getAllCommandesZone(Optional<String> date, Optional<String> search);

    @Operation(summary = "Saisi du colisage d'une commande", description = "Saisi du colisage d'une commande")
    @ApiResponse(responseCode = "200", description = "Colisage saisi avec succès")
    @PostMapping("/commandes/saisir-colisage")
    ResponseEntity<SuccessResponseDto<Integer>> saisirColisageCommande(CmdColisageDto cmdColisageDto);

    @Operation(summary = "Récupération du colisage d'une commande", description = "Récupération du colisage d'une commande")
    @ApiResponse(responseCode = "200", description = "Colisage récupéré avec succès")
    @PostMapping("/commandes/get-colisage")
    ResponseEntity<SuccessResponseDto<ColisageDto>> getColisageCommande(CmdIdDto id);

    @Operation(summary = "Saisi du colisage d'une commande par zone", description = "Saisi du colisage d'une commande par zone")
    @ApiResponse(responseCode = "200", description = "Colisage saisi avec succès")
    @PostMapping("/commandes-zones/saisir-colisage")
    ResponseEntity<SuccessResponseDto<Integer>> saisirColisageZone(CmdZoneColisageDto cmdZoneColisageDto);

    @Operation(summary = "Récupération du colisage d'une commande par zone", description = "Récupération du colisage d'une commande par zone")
    @ApiResponse(responseCode = "200", description = "Colisage récupéré avec succès")
    @PostMapping("/commandes-zones/get-colisage")
    ResponseEntity<SuccessResponseDto<ColisageDto>> getColisageZone(CmdZoneIdDto id);

    @Operation(summary = "Récupération de la liste des bacs", description = "Récupération de la liste des bacs")
    @ApiResponse(responseCode = "200", description = "Liste des bacs récupérée avec succès")
    @GetMapping("/liste-bacs")
    ResponseEntity<SuccessResponseDto<List<Bac>>> getListeBacs();

    @Operation(summary = "Génération des étiquettes", description = "Mise à jour du colisage global et génération des étiquettes des colis")
    @ApiResponse(responseCode = "200", description = "Colisage mis à jour avec succès")
    @ApiResponse(responseCode = "422", description = "Colisage non mis à jour")
    @PostMapping("/update-colisage")
    ResponseEntity<SuccessResponseDto<CommandeColisageResponse>> updateColisageGlobal(UpdateColisageRequest request);

    @Operation(summary = "Récupération des étiquettes générées", description = "Récupération des étiquettes générées d'une commande")
    @ApiResponse(responseCode = "200", description = "Etiquettes récupérées avec succès")
    @PostMapping("/get-etiquettes")
    ResponseEntity<SuccessResponseDto<ListeEtiquettesResponse>> getEtiquettesColisage(ListeEtiquetteRequest request);

}
