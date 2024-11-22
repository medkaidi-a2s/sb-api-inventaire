package dz.a2s.a2spreparation.controllers;

import dz.a2s.a2spreparation.dto.response.SuccessResponseDto;
import dz.a2s.a2spreparation.dto.statistique.StatsPreparateurDto;
import dz.a2s.a2spreparation.entities.views.Statistique;
import dz.a2s.a2spreparation.entities.views.StatsPreparateur;
import dz.a2s.a2spreparation.services.StatistiqueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/statistiques")
public class StatistiqueController {
    private final StatistiqueService statistiqueService;

    @GetMapping("/general-stats")
    public ResponseEntity<SuccessResponseDto<List<Statistique>>> getStatistiques(@RequestParam String dateDebut, @RequestParam String dateFin) {
        log.info("Point d'entrée à la méthode getStatistiques du StatistiqueController avec date début et fin {} - {}", dateDebut, dateFin);

        List<Statistique> statistiques = this.statistiqueService.getStatistiques(dateDebut, dateFin);

        SuccessResponseDto response = new SuccessResponseDto(
                200,
                "Statistiques de la période spécifiée",
                statistiques
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/stats-par-preparateur")
    public ResponseEntity<SuccessResponseDto<List<StatsPreparateurDto>>> getStatistiquesParPreparateur(@RequestParam String dateDebut, @RequestParam String dateFin) {
        log.info("Point d'entrée à la méthode getStatistiquesParPreparateur du StatistiqueController avec date début et fin {} - {}", dateDebut, dateFin);

        List<StatsPreparateurDto> statistiques = this.statistiqueService.getStatistiquesParPreparateur(dateDebut, dateFin);

        SuccessResponseDto response = new SuccessResponseDto(
                200,
                "Statistiques de la période spécifiée par préparateur",
                statistiques
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/stats-par-controleur")
    public ResponseEntity<SuccessResponseDto<List<StatsPreparateurDto>>> getStatistiquesParControleur(@RequestParam String dateDebut, @RequestParam String dateFin) {
        log.info("Point d'entrée à la méthode getStatistiquesParControleur du StatistiqueController avec date début et fin {} - {}", dateDebut, dateFin);

        List<StatsPreparateurDto> statistiques = this.statistiqueService.getStatistiquesParControleur(dateDebut, dateFin);

        SuccessResponseDto response = new SuccessResponseDto(
                200,
                "Statistiques de la période spécifiée par contrôleur",
                statistiques
        );

        return ResponseEntity.ok(response);
    }

}
