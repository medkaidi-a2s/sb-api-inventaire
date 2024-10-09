package dz.a2s.a2spreparation.services.impl;

import dz.a2s.a2spreparation.dto.affectation.PrpCmdPrlvDto;
import dz.a2s.a2spreparation.dto.preparation.PrpCdeUsrCodeDto;
import dz.a2s.a2spreparation.dto.preparation.PrpCmdPrlvUsrCodeDto;
import dz.a2s.a2spreparation.entities.views.PrpCdePrlvUsrCode;
import dz.a2s.a2spreparation.entities.views.PrpCdeUsrCode;
import dz.a2s.a2spreparation.mappers.preparation.PrpCdePrlvUsrCodeMapper;
import dz.a2s.a2spreparation.mappers.preparation.PrpCdeUsrCodeMapper;
import dz.a2s.a2spreparation.repositories.views.PrpCdePrlvUsrCodeRepository;
import dz.a2s.a2spreparation.repositories.views.PrpCdeUsrCodeRepository;
import dz.a2s.a2spreparation.services.CustomUserDetailsService;
import dz.a2s.a2spreparation.services.PreparationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PreparationServiceImpl implements PreparationService {
    private final CustomUserDetailsService customUserDetailsService;
    private final PrpCdePrlvUsrCodeRepository prpCdePrlvUsrCodeRepository;
    private final PrpCdeUsrCodeRepository prpCdeUsrCodeRepository;

    @Override
    public List<PrpCdeUsrCodeDto> getCommandes(String date) {
        log.info("Entering the getCommandes method from the PreparationService with date {}", date);

        String username = this.customUserDetailsService.getCurrentUserCode();
        log.info("Entering the getCommandes method from the PreparationService with username {}", username);

        log.info("Fetching the liste of orders by preparateur from the repo");
        List<PrpCdeUsrCode> commandes = this.prpCdeUsrCodeRepository.getCmdParPreparateur(username, date);
        List<PrpCdeUsrCodeDto> response = commandes.stream().map(PrpCdeUsrCodeMapper::toPrpCdeUsrCodeDto).toList();

        log.info("Returning fetched data to controller with length {}", response.size());

        return response;
    }

    @Override
    public List<PrpCmdPrlvUsrCodeDto> getCommandesPrlv(String date) {
        log.info("Entering the getCommandesPrlv method from the PreparationService with date {}", date);

        String username = this.customUserDetailsService.getCurrentUserCode();
        log.info("Entering the getCommandesPrlv method from the PreparationService with username {}", username);

        log.info("Fetching the liste of orders par prélevement by preparateur from the repo");
        List<PrpCdePrlvUsrCode> commandes = this.prpCdePrlvUsrCodeRepository.getCmdPrlvParPreparateur(username, date);
        List<PrpCmdPrlvUsrCodeDto> response = commandes.stream().map(PrpCdePrlvUsrCodeMapper::toPrpCmdPrlvUsrCodeDto).toList();

        log.info("Returning fetched data to controller with length {}", response.size());

        return response;
    }
}
