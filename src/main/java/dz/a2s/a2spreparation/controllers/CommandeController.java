package dz.a2s.a2spreparation.controllers;

import dz.a2s.a2spreparation.api.CommandeApi;
import dz.a2s.a2spreparation.dto.CommandeResponseDto;
import dz.a2s.a2spreparation.dto.CommandeZoneResponseDto;
import dz.a2s.a2spreparation.dto.affectation.CmdColisageDto;
import dz.a2s.a2spreparation.dto.affectation.CmdZoneColisageDto;
import dz.a2s.a2spreparation.dto.response.SuccessResponseDto;
import dz.a2s.a2spreparation.services.CheckingService;
import dz.a2s.a2spreparation.services.CommandeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/commandes")
public class CommandeController implements CommandeApi {

    private final CommandeService commandeService;

    @Override
    public ResponseEntity<SuccessResponseDto<List<CommandeZoneResponseDto>>> getCommandesZone(@RequestParam Optional<String> date) {
        log.info("| Entry | CommandeController.getCommandesZone | Args | date : {}", date);

        var commandes = this.commandeService.getControlledCommandesZone(date.orElse(""));
        log.info("Fetched the controlled commandes from the service | commandes.size={}", date);

        SuccessResponseDto<List<CommandeZoneResponseDto>> successResponseDto = new SuccessResponseDto<>(
                200,
                "Liste des commandes par zone déjà contrôlées",
                commandes
        );

        return ResponseEntity.ok(successResponseDto);
    }

    @Override
    public ResponseEntity<SuccessResponseDto<List<CommandeResponseDto>>> getCommandes(Optional<String> date) {
        log.info("| Entry | CommandeController.getCommandes | Args | date : {}", date);

        List<CommandeResponseDto> commandes = this.commandeService.getControlledCommandes(date.orElse(""));
        log.info("Fetched the controlled commandes from the service | commandes.size={}", date);

        SuccessResponseDto<List<CommandeResponseDto>> successResponseDto = new SuccessResponseDto<>(
                200,
                "Liste des commandes déjà contrôlées",
                commandes
        );

        return ResponseEntity.ok(successResponseDto);
    }

    @Override
    public ResponseEntity<SuccessResponseDto<Integer>> saisirColisageCommande(@RequestBody CmdColisageDto cmdColisageDto) {
        log.info("| Entry | CommandeController.saisirColisageCommande | Args | cmdColisageDto={}", cmdColisageDto);

        var response = this.commandeService.saisirColisageCommande(cmdColisageDto);
        log.info("Réponse de la méthode de saisi du colisage | response={}", response);

        SuccessResponseDto<Integer> successResponseDto = new SuccessResponseDto<>(
                200,
                "Liste des commandes déjà contrôlées",
                response
        );

        return ResponseEntity.ok(successResponseDto);
    }

    @Override
    public ResponseEntity<SuccessResponseDto<Integer>> saisirColisageZone(@RequestBody CmdZoneColisageDto cmdZoneColisageDto) {
        log.info("| Entry | CommandeController.saisirColisageZone | Args | cmdZoneColisageDto={}", cmdZoneColisageDto);

        var response = this.commandeService.saisirColisageZone(cmdZoneColisageDto);
        log.info("Réponse de la méthode de saisi du colisage | response={}", response);

        SuccessResponseDto<Integer> successResponseDto = new SuccessResponseDto<>(
                200,
                "Liste des commandes déjà contrôlées",
                response
        );

        return ResponseEntity.ok(successResponseDto);
    }
}
