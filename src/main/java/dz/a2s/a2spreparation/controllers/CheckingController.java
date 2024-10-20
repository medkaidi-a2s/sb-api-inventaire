package dz.a2s.a2spreparation.controllers;

import dz.a2s.a2spreparation.dto.CommandeResponseDto;
import dz.a2s.a2spreparation.dto.response.SuccessResponseDto;
import dz.a2s.a2spreparation.services.CheckingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
