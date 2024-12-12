package dz.a2s.a2spreparation.api;

import dz.a2s.a2spreparation.dto.response.SuccessResponseDto;
import dz.a2s.a2spreparation.dto.statistique.StatsPreparateurDto;
import dz.a2s.a2spreparation.entities.views.Statistique;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Gestion des statistiques", description = "APIs relatifs aux opérations liées au calcul des statistiques")
public interface StatistiqueApi {

    @Operation(summary = "Génération des statistiques généraux", description = "Génération des statistiques relatis à toutes les commandes pendant une période détérminée par les paramètres passé, les commandes sont triées par statut")
    @ApiResponse(responseCode = "200", description = "Statistiques générés avec succès")
    @Parameter(name = "dateDebut", description = "Date début de la période des statistiques sous forme de chaine de caractère", required = true)
    @Parameter(name = "dateFin", description = "Date fin de la période des statistiques sous forme de chaine de caractère", required = true)
    @GetMapping("/general-stats")
    public ResponseEntity<SuccessResponseDto<List<Statistique>>> getStatistiques(@RequestParam String dateDebut, @RequestParam String dateFin);

    @Operation(summary = "Génération des statistiques par préparateurs", description = "Génération des statistiques relatis aux préparateurs pendant une période détérminée par les paramètres passé, les commandes sont triées par statut")
    @ApiResponse(responseCode = "200", description = "Statistiques générés avec succès")
    @Parameter(name = "dateDebut", description = "Date début de la période des statistiques sous forme de chaine de caractère", required = true)
    @Parameter(name = "dateFin", description = "Date fin de la période des statistiques sous forme de chaine de caractère", required = true)
    @GetMapping("/stats-par-preparateur")
    public ResponseEntity<SuccessResponseDto<List<StatsPreparateurDto>>> getStatistiquesParPreparateur(@RequestParam String dateDebut, @RequestParam String dateFin);

    @Operation(summary = "Génération des statistiques par contrôleurs", description = "Génération des statistiques relatis aux contrôleurs pendant une période détérminée par les paramètres passé, les commandes sont triées par statut")
    @ApiResponse(responseCode = "200", description = "Statistiques générés avec succès")
    @Parameter(name = "dateDebut", description = "Date début de la période des statistiques sous forme de chaine de caractère", required = true)
    @Parameter(name = "dateFin", description = "Date fin de la période des statistiques sous forme de chaine de caractère", required = true)
    @GetMapping("/stats-par-controleur")
    public ResponseEntity<SuccessResponseDto<List<StatsPreparateurDto>>> getStatistiquesParControleur(@RequestParam String dateDebut, @RequestParam String dateFin);

}
