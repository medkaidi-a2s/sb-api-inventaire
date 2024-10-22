package dz.a2s.a2spreparation.controllers;

import dz.a2s.a2spreparation.dto.CommandeResponseDto;
import dz.a2s.a2spreparation.dto.CommandeZoneResponseDto;
import dz.a2s.a2spreparation.dto.affectation.CmdIdDto;
import dz.a2s.a2spreparation.dto.affectation.CmdZoneIdDto;
import dz.a2s.a2spreparation.dto.preparation.LigneQteDto;
import dz.a2s.a2spreparation.dto.preparation.LigneQteZoneDto;
import dz.a2s.a2spreparation.dto.response.SuccessResponseDto;
import dz.a2s.a2spreparation.services.CheckingService;
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
@RequestMapping("/api/controle")
public class CheckingController {
    private final CheckingService checkingService;

    @GetMapping("/commandes")
    public ResponseEntity<SuccessResponseDto<List<CommandeResponseDto>>> getAllPreparedCommandes(@RequestParam Optional<String> date) {
        String reqDate = date.orElse("");

        log.info("Entering the getAllPreparedCommandes method from the CheckingController with date {}", reqDate);

        List<CommandeResponseDto> commandes = this.checkingService.getAllPreparedCommandes(reqDate);
        log.info("Fetched the commandes from the service with length {}", reqDate);

        SuccessResponseDto<List<CommandeResponseDto>> successResponseDto = new SuccessResponseDto<>(
                200,
                "Liste des commandes à contrôler",
                commandes
        );

        return ResponseEntity.ok(successResponseDto);
    }

    @GetMapping("/commandes-zone")
    public ResponseEntity<SuccessResponseDto<List<CommandeZoneResponseDto>>> getAllPreparedCommandesZone(@RequestParam Optional<String> date) {
        String reqDate = date.orElse("");

        log.info("Entering the getAllPreparedCommandesZone method from the CheckingController with date {}", reqDate);

        List<CommandeZoneResponseDto> commandes = this.checkingService.getAllPreparedCommandesZone(reqDate);
        log.info("Fetched the commandes par zone from the service with length {}", reqDate);

        SuccessResponseDto<List<CommandeZoneResponseDto>> successResponseDto = new SuccessResponseDto<>(
                200,
                "Liste des commandes par zone à contrôler",
                commandes
        );

        return ResponseEntity.ok(successResponseDto);
    }

    @PatchMapping("/commande/start-control")
    public ResponseEntity<SuccessResponseDto<Integer>> startControleCde(@RequestBody @Valid CmdIdDto commande) throws Exception {
        log.info("Entering the method startControleCde from the CheckingController");

        Integer response = this.checkingService.startControleCde(
                commande.getCmpId(),
                commande.getId(),
                commande.getType(),
                commande.getStkCode()
        );

        SuccessResponseDto<Integer> successResponseDto = new SuccessResponseDto<>(
                200,
                "Contrôle de la commande commencé avec succès",
                response
        );

        return ResponseEntity.ok(successResponseDto);
    }

    @PatchMapping("/commande/set-control-quantity")
    public ResponseEntity<SuccessResponseDto<Integer>> setControlledQuantity(@RequestBody @Valid LigneQteDto ligne) throws Exception {
        log.info("Entering the setControlledQuantity method from the CheckingController with {}", ligne);

        Integer response = this.checkingService.setControlledQuantity(
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
                "Quantité contrôlée mis à jour avec succès",
                response
        );

        return ResponseEntity.ok(successResponseDto);
    }

    @PatchMapping("/commande/set-controlled")
    public ResponseEntity<SuccessResponseDto<Integer>> setCommandeControlled(@RequestBody @Valid CmdIdDto id) throws Exception {
        log.info("Entering the setCommandeControlled method from the CheckingController with {}", id);

        Integer response = this.checkingService.setCommandeControlled(id);
        SuccessResponseDto<Integer> successResponseDto = new SuccessResponseDto<>(
                200,
                "Commande contrôlée avec succès",
                response
        );

        return ResponseEntity.ok(successResponseDto);
    }

    @PatchMapping("/commande-zone/start-control")
    public ResponseEntity<SuccessResponseDto<Integer>> startControleZone(@RequestBody @Valid CmdZoneIdDto commande) throws Exception {
        log.info("Entering the method startControleZone from the CheckingController with {}", commande);

        Integer response = this.checkingService.startControleZone(
                commande.getCmpId(),
                commande.getId(),
                commande.getType(),
                commande.getStkCode(),
                commande.getZone()
        );

        SuccessResponseDto<Integer> successResponseDto = new SuccessResponseDto<>(
                200,
                "Contrôle commencé sur la commande par zone avec succès",
                response
        );

        return ResponseEntity.ok(successResponseDto);
    }

    @PatchMapping("/commande-zone/set-control-quantity")
    public ResponseEntity<SuccessResponseDto<Integer>> setControlledQuantityZone(@RequestBody @Valid LigneQteZoneDto ligne) throws Exception {
        log.info("Entering the setControlledQuantityZone method from the CheckingController with {}", ligne);

        Integer response = this.checkingService.setControlledQuantityZone(ligne);

        SuccessResponseDto successResponseDto = new SuccessResponseDto<>(
                200,
                "Quantité contrôlée mis à jour avec succès",
                response
        );

        return ResponseEntity.ok(successResponseDto);
    }

    @PatchMapping("/commande-zone/set-controlled")
    public ResponseEntity<SuccessResponseDto<Integer>> setCommandeZoneControlled(@RequestBody @Valid CmdZoneIdDto id) throws Exception {
        log.info("Entering the setCommandeZoneControlled method from the CheckingController with {}", id);

        Integer response = this.checkingService.setCommandeZoneControlled(id);

        SuccessResponseDto<Integer> successResponseDto = new SuccessResponseDto<>(
                200,
                "Commande par zone contrôlée avec succès",
                response
        );

        return ResponseEntity.ok(successResponseDto);
    }

}
