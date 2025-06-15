package dz.a2s.a2spreparation.api;

import dz.a2s.a2spreparation.dto.CommandeResponseDto;
import dz.a2s.a2spreparation.dto.CommandeZoneResponseDto;
import dz.a2s.a2spreparation.dto.affectation.CmdColisageDto;
import dz.a2s.a2spreparation.dto.affectation.CmdZoneColisageDto;
import dz.a2s.a2spreparation.dto.response.SuccessResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Tag(name = "Gestion des commandes en général", description = "APIs pour la gestion des opération communes et générales relatives aux commandes")
public interface CommandeApi {

    @Operation(summary = "Récupération des commandes préparées par zones", description = "Récupération des commandes déjà préparéespar zones")
    @ApiResponse(responseCode = "200", description = "Commandes par zones récupérées avec succès")
    @GetMapping("/commandes-zones")
    public ResponseEntity<SuccessResponseDto<List<CommandeZoneResponseDto>>> getCommandesZone(Optional<String> date);

    @Operation(summary = "Récupération des commandes préparées", description = "Récupération des commandes déjà préparées")
    @ApiResponse(responseCode = "200", description = "Commandes récupérées avec succès")
    @GetMapping("/commandes")
    public ResponseEntity<SuccessResponseDto<List<CommandeResponseDto>>> getCommandes(Optional<String> date);

    @Operation(summary = "Saisi du colisage d'une commande", description = "Saisi du colisage d'une commande")
    @ApiResponse(responseCode = "200", description = "Colisage saisi avec succès")
    @PostMapping("/commandes/saisir-colisage")
    public ResponseEntity<SuccessResponseDto<Integer>> saisirColisageCommande(CmdColisageDto cmdColisageDto);

    @Operation(summary = "Saisi du colisage d'une commande par zone", description = "Saisi du colisage d'une commande par zone")
    @ApiResponse(responseCode = "200", description = "Colisage saisi avec succès")
    @PostMapping("/commandes-zones/saisir-colisage")
    public ResponseEntity<SuccessResponseDto<Integer>> saisirColisageZone(CmdZoneColisageDto cmdZoneColisageDto);

}
