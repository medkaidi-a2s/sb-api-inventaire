package dz.a2s.a2spreparation.controllers;

import dz.a2s.a2spreparation.dto.affectation.CmdIdDto;
import dz.a2s.a2spreparation.dto.affectation.CmdPrlvIdDto;
import dz.a2s.a2spreparation.dto.preparation.PrpCdeUsrCodeDto;
import dz.a2s.a2spreparation.dto.preparation.PrpCmdPrlvUsrCodeDto;
import dz.a2s.a2spreparation.dto.response.SuccessResponseDto;
import dz.a2s.a2spreparation.services.PreparationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/commande-preleve")
    public ResponseEntity<SuccessResponseDto<PrpCmdPrlvUsrCodeDto>> getOnePrlvCommande(@RequestParam String type, @RequestParam Integer annee, @RequestParam Integer id) {
        log.info("Entering the getOnePrlvCommande from PreparationController");

        PrpCmdPrlvUsrCodeDto commande = this.preparationService.getOneCmdPrlv(id, type, annee);

        SuccessResponseDto<PrpCmdPrlvUsrCodeDto> response = new SuccessResponseDto<>(
                200,
                "Commande par prélevement retournée",
                commande
        );

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/commande/start-prep-cde")
    public ResponseEntity<SuccessResponseDto<Integer>> startPrepareCde(@RequestBody CmdIdDto commande) throws Exception {
        log.info("Entering the method startPrepareCde from the PreparationController");

        Integer response = this.preparationService.startPrepareCde(
                commande.getCmpId(),
                commande.getId(),
                commande.getType(),
                commande.getStkCode()
        );

        SuccessResponseDto<Integer> successResponseDto = new SuccessResponseDto<>(
                200,
                "Commande mis à jour avec succès",
                response
        );

        return ResponseEntity.ok(successResponseDto);
    }

    @PatchMapping("/commande/start-prep-preleve")
    public ResponseEntity<SuccessResponseDto<Integer>> startPreparePrlv(@RequestBody CmdPrlvIdDto commande) throws Exception {
        log.info("Entering the method startPreparePrlv from the PreparationController");

        Integer response = this.preparationService.startPreparePrlv(
                commande.getCmpId(),
                commande.getId(),
                commande.getType(),
                commande.getAnnee()
        );

        SuccessResponseDto<Integer> successResponseDto = new SuccessResponseDto<>(
                200,
                "Commande mis à jour avec succès",
                response
        );
        return ResponseEntity.ok(successResponseDto);
    }

}
