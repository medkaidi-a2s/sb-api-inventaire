package dz.a2s.a2spreparation.controllers;

import dz.a2s.a2spreparation.dto.CommandeResponseDto;
import dz.a2s.a2spreparation.dto.affectation.CmdIdDto;
import dz.a2s.a2spreparation.dto.preparation.LigneQteDto;
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

}
