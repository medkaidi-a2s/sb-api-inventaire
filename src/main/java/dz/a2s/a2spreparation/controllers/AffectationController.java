package dz.a2s.a2spreparation.controllers;

import dz.a2s.a2spreparation.dto.response.SuccessResponseDto;
import dz.a2s.a2spreparation.entities.views.PrpListeCdeZones;
import dz.a2s.a2spreparation.services.AffectationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/affectation")
public class AffectationController {
    private final AffectationService affectationService;

    @GetMapping("listeCmdPrpZone")
    public ResponseEntity<SuccessResponseDto<List<PrpListeCdeZones>>> getListeCmdPrpZone() {
        log.info("Entering the getListeCmdPrpZone from the AffectationController");

        log.info("Fetching liste des commandes from the service");
        List<PrpListeCdeZones> listeCommandes = this.affectationService.getAll();
        log.info("Data fetched from the service length = {}", listeCommandes.size());

        SuccessResponseDto<List<PrpListeCdeZones>> successResponseDto = new SuccessResponseDto<List<PrpListeCdeZones>>(
                200,
                "Liste des commandes déjà affectées par zone",
                listeCommandes
        );

        return ResponseEntity.ok(successResponseDto);
    }

}
