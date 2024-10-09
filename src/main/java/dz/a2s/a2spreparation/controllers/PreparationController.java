package dz.a2s.a2spreparation.controllers;

import dz.a2s.a2spreparation.dto.preparation.PrpCdeUsrCodeDto;
import dz.a2s.a2spreparation.dto.preparation.PrpCmdPrlvUsrCodeDto;
import dz.a2s.a2spreparation.dto.response.SuccessResponseDto;
import dz.a2s.a2spreparation.services.PreparationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/preparation")
public class PreparationController {
    private final PreparationService preparationService;

    @GetMapping("/commandes")
    public ResponseEntity<SuccessResponseDto<List<PrpCdeUsrCodeDto>>> getCommandeParPreparateur(@RequestParam String date) {
        log.info("Entering the getCommandeParPreparateur method from the PreparationController with date {}", date);

        List<PrpCdeUsrCodeDto> commandes = this.preparationService.getCommandes(date);
        log.info("Commandes par préparateur fetched from the service with length {}", commandes.size());

        SuccessResponseDto<List<PrpCdeUsrCodeDto>> successResponseDto = new SuccessResponseDto<List<PrpCdeUsrCodeDto>>(
                200,
                "Listes des commandes par préparateur",
                commandes
        );

        return ResponseEntity.ok(successResponseDto);
    }

    @GetMapping("/commandes-preleve")
    public ResponseEntity<SuccessResponseDto<List<PrpCmdPrlvUsrCodeDto>>> getCommandesPrlvParPreparateur(@RequestParam String date) {
        log.info("Entering the getCommandesPrlvParPreparateur method from the PreparationController with date {}", date);

        List<PrpCmdPrlvUsrCodeDto> commandes = this.preparationService.getCommandesPrlv(date);
        log.info("Commandes prélevement par préparateur fetched from the service with length {}", commandes.size());

        SuccessResponseDto successResponseDto = new SuccessResponseDto<>(
                200,
                "Listes des commandes par préparateur",
                commandes
        );

        return ResponseEntity.ok(successResponseDto);
    }

}
