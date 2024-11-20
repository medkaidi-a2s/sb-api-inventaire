package dz.a2s.a2spreparation.controllers;

import dz.a2s.a2spreparation.dto.response.SuccessResponseDto;
import dz.a2s.a2spreparation.entities.views.Statistique;
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

}
