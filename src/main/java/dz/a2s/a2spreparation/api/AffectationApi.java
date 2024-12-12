package dz.a2s.a2spreparation.api;

import dz.a2s.a2spreparation.dto.CommandeResponseDto;
import dz.a2s.a2spreparation.dto.CommandeZoneResponseDto;
import dz.a2s.a2spreparation.dto.affectation.*;
import dz.a2s.a2spreparation.dto.response.SuccessResponseDto;
import dz.a2s.a2spreparation.entities.views.PrpCdePrepCont;
import dz.a2s.a2spreparation.entities.views.PrpCdePrlvPrepCont;
import dz.a2s.a2spreparation.entities.views.PrpPrepareControle;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Tag(name = "Gestion de l'affectation", description = "APIs pour la récupération, affectation et réaffectation des commandes")
public interface AffectationApi {

    @Operation(summary = "Récupération des commandes par zones", description = "Récupération des commandes par zones qui n'ont pas été affectées encore par date, si on passe une chaine vide au paramètre date, toutes les commandes seront récupérées")
    @ApiResponse(responseCode = "200", description = "Commandes par zones récupérées avec succès")
    @GetMapping("commandes-zones")
    public ResponseEntity<SuccessResponseDto<List<CommandeZoneResponseDto>>> getListeCmdPrpZone(@RequestParam Optional<String> date);

    @Operation(summary = "Récupération des commandes à préparer", description = "Récupération des commandes qui n'ont pas été affectées par date, si on passe une chaine vide au paramètre date, toutes les commandes seront récupérées")
    @ApiResponse(responseCode = "200", description = "Commandes récupérées avec succès")
    @GetMapping("commandes")
    public ResponseEntity<SuccessResponseDto<List<CommandeResponseDto>>> getListeCmdPrp(@RequestParam Optional<String> date);

    @Operation(summary = "Récupération des commandes déjà affectées", description = "Récupération des commandes qui ont été déjà affectées par date, si on passe une chaine vide au paramètre date, toutes les commandes affectées seront récupérées")
    @ApiResponse(responseCode = "200", description = "Commandes affectées récupérées avec succès")
    @GetMapping("commandes/assigned")
    public ResponseEntity<SuccessResponseDto<List<CommandeResponseDto>>> getListeCmdAssigned(@RequestParam Optional<String> date);

    @Operation(summary = "Récupération des préparateurs et contrôleurs des commandes", description = "Récupération du préparateur et contrôleur des commandes par id, type et code stock de la vente en question")
    @ApiResponse(responseCode = "200", description = "Préparateur et contrôleur récupérés avec succès")
    @GetMapping("commandes/preparateur-controleurs")
    public ResponseEntity<SuccessResponseDto<PrpCdePrepCont>> getCmdPrepCont(@RequestParam Integer id, @RequestParam String type, @RequestParam String stkCode) throws Exception;

    @Operation(summary = "Récupération des commandes par prélèvement", description = "Récupération des commandes par prélèvement et qui n'ont pas été affectées par date, si on passe une chaine vide au paramètre date, toutes les commandes seront récupérées")
    @ApiResponse(responseCode = "200", description = "Commandes par prélèvement récupérées avec succès")
    @GetMapping("commandes-preleve")
    public ResponseEntity<SuccessResponseDto<List<PrpCmdPrlvDto>>> getListeCmdPrlv(@RequestParam Optional<String> date);

    @Operation(summary = "Récupération des commandes par prélèvement déjà affectées", description = "Récupération des commandes par prélèvement et qui ont été déjà affectées par date, si on passe une chaine vide au paramètre date, toutes les commandes seront récupérées")
    @ApiResponse(responseCode = "200", description = "Commandes par prélèvement déjà affectées récupérées avec succès")
    @GetMapping("commandes-preleve/assigned")
    public ResponseEntity<SuccessResponseDto<List<AffCmdPrlvDto>>> getListCmdPrlvAssigned(@RequestParam Optional<String> date);

    @Operation(summary = "Récupération du préparateur et contrôleur des commandes par prélèvement", description = "Récupération du préparateur et contrôleur des commandes par prélèvement via id, type et code stock de la vente en question")
    @ApiResponse(responseCode = "200", description = "Préparateur et contrôleur récupérés avec succès")
    @GetMapping("commandes-preleve/preparateur-controleurs")
    public ResponseEntity<SuccessResponseDto<PrpCdePrlvPrepCont>> getPrepContPrlv(@RequestParam Integer id, @RequestParam String type, @RequestParam Integer annee) throws Exception;

    @Operation(summary = "Modification du préparateur et contrôleur des commandes par prélèvement", description = "Modification du préparateur et contrôleur des commandes par prélèvement")
    @ApiResponse(responseCode = "200", description = "Préparateur et contrôleur modifiés avec succès")
    @PatchMapping("commandes-preleve/preparateur-controleurs")
    public ResponseEntity<SuccessResponseDto<PrpCdePrlvPrepCont>> editAffectCmdPrpPrlv(@RequestBody @Valid PrpCdePrlvPrepContDto dto) throws Exception;

    @Operation(summary = "Modification du préparateur et contrôleur des commandes", description = "Modification du préparateur et contrôleur des commandes")
    @ApiResponse(responseCode = "200", description = "Préparateur et contrôleur modifiés avec succès")
    @PatchMapping("commandes/preparateur-controleurs")
    public ResponseEntity<SuccessResponseDto<PrpCdePrepCont>> editAffectCmdPrp(@RequestBody @Valid AffectCmdRequestDto dto) throws Exception;

    @Operation(summary = "Affectation du préparateur et contrôleur des commandes", description = "Affectation du préparateur et contrôleur aux commandes")
    @ApiResponse(responseCode = "200", description = "Préparateur et contrôleur affectés avec succès")
    @PostMapping("/affect-commande")
    public ResponseEntity<SuccessResponseDto<ArrayList<AffectCmdResultDto>>> affectCommandePrp(@RequestBody @Valid List<AffectCmdRequestDto> commandes);

    @Operation(summary = "Affectation du préparateur et contrôleur des commandes par prélèvement", description = "Affectation du préparateur et contrôleur aux commandes par prélèvement")
    @ApiResponse(responseCode = "200", description = "Préparateur et contrôleur affectés avec succès")
    @PostMapping("/affect-preleve")
    public ResponseEntity<SuccessResponseDto<ArrayList<AffectCmdResultDto>>> affectCommandePrlv(@RequestBody @Valid List<AffectCmdPrlvReqDto> commandes);

    @Operation(summary = "Récupération des préparateurs", description = "Récupération de la liste de tous les préparateurs")
    @ApiResponse(responseCode = "200", description = "Préparateurs récupérés avec succès")
    @GetMapping("preparateurs")
    public ResponseEntity<SuccessResponseDto<List<PrpPrepareControle>>> getAllPreparateurs();

    @Operation(summary = "Récupération des contrôleurs", description = "Récupération de la liste de tous les contrôleurs")
    @ApiResponse(responseCode = "200", description = "Contrôleurs récupérés avec succès")
    @GetMapping("controleurs")
    public ResponseEntity<SuccessResponseDto<List<PrpPrepareControle>>> getAllControleurs();
}
