package dz.a2s.a2spreparation.services.impl;

import dz.a2s.a2spreparation.dto.CommandeResponseDto;
import dz.a2s.a2spreparation.dto.CommandeZoneResponseDto;
import dz.a2s.a2spreparation.dto.affectation.CmdColisageDto;
import dz.a2s.a2spreparation.dto.affectation.CmdIdDto;
import dz.a2s.a2spreparation.dto.affectation.CmdZoneColisageDto;
import dz.a2s.a2spreparation.dto.affectation.CmdZoneIdDto;
import dz.a2s.a2spreparation.dto.commande.response.ColisageDto;
import dz.a2s.a2spreparation.entities.enums.TIER_TYPES;
import dz.a2s.a2spreparation.entities.views.Commande;
import dz.a2s.a2spreparation.mappers.CommandeMapper;
import dz.a2s.a2spreparation.mappers.CommandeZoneMapper;
import dz.a2s.a2spreparation.repositories.views.CommandeRepository;
import dz.a2s.a2spreparation.repositories.views.CommandeZoneRepository;
import dz.a2s.a2spreparation.services.CommandeService;
import dz.a2s.a2spreparation.services.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class CommandeServiceImpl implements CommandeService {

    private final CustomUserDetailsService customUserDetailsService;
    private final CommandeRepository commandeRepository;
    private final CommandeZoneRepository commandeZoneRepository;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public List<CommandeResponseDto> getAllCommandes(String search, String date) {
        log.info("| Entry | CommandeService.getAllCommandes | Args | date : {}, search : {}", date, search);

        if(search == null || search.isEmpty()) return List.of();

        var cmpId = this.customUserDetailsService.getCurrentCompanyId();
        log.info("Company ID fetched from the service {}", cmpId);

        var commandes = this.commandeRepository.getAllCommandes(cmpId, date, search);
        log.info("Fetched the orders from the repo | commandes.size={}", commandes.size());

        var response = commandes.stream().map(CommandeMapper::toCommandeResponseDto).toList();
        log.info("Mapped the orders to the response | response.size={}", response.size());

        return response;
    }

    @Override
    public List<CommandeZoneResponseDto> getAllCommandesZone(String search, String date) {
        log.info("| Entry | CommandeService.getAllCommandesZone | Args | date : {}, search : {}", date, search);

        if(search == null || search.isEmpty()) return List.of();

        var cmpId = this.customUserDetailsService.getCurrentCompanyId();
        log.info("Company ID fetched from the service {}", cmpId);

        var commandes = this.commandeZoneRepository.getAllCommandesZone(cmpId, date, search);
        log.info("Fetched the orders from the repo | commandes.size={}", commandes.size());

        var response = commandes.stream().map(CommandeZoneMapper::toCommandeZoneResponseDto).toList();
        log.info("Mapped the orders to the response | response.size={}", response.size());

        return response;
    }

    @Override
    public List<CommandeResponseDto> getControlledCommandes(String date) {
        log.info("| Entry | CommandeService.getControlledCommandes | Args | date : {}", date);

        Integer companyId = this.customUserDetailsService.getCurrentCompanyId();
        log.info("Company ID fetched from the service {}", companyId);

        List<Commande> commandes = this.commandeRepository.getControlledCommandes(companyId, date);
        log.info("Fetched the controlled orders from the repo | commandes.size={}", commandes.size());

        List<CommandeResponseDto> response = commandes.stream().map(CommandeMapper::toCommandeResponseDto).toList();

        return response;
    }

    @Override
    public List<CommandeZoneResponseDto> getControlledCommandesZone(String date) {
        log.info("| Entry | CommandeService.getControlledCommandesZone | Args | date : {}", date);

        Integer companyId = this.customUserDetailsService.getCurrentCompanyId();
        log.info("Company ID fetched from the service {}", companyId);

        var commandes = this.commandeZoneRepository.getControlledCommandesZone(companyId, date);
        log.info("Fetched the controlled orders from the repo | commandes.size={}", commandes.size());

        return commandes.stream().map(CommandeZoneMapper::toCommandeZoneResponseDto).toList();
    }

    @Transactional
    @Override
    public Integer saisirColisageCommande(CmdColisageDto cmdColisageDto) {
        log.info("| Entry | CommandeService.saisirColisageCommande | Args | cmdColisageDto={}", cmdColisageDto);

        Integer companyId = this.customUserDetailsService.getCurrentCompanyId();
        log.info("Company ID fetched from the service {}", companyId);

        Integer response = 0;

        try {
            response = this.commandeRepository.saisirColisageCommande(
                    companyId,
                    cmdColisageDto.getId(),
                    cmdColisageDto.getType(),
                    cmdColisageDto.getStkCode(),
                    null,
                    null,
                    cmdColisageDto.getColisV(),
                    cmdColisageDto.getColisD(),
                    cmdColisageDto.getFrigo(),
                    cmdColisageDto.getPsycho(),
                    cmdColisageDto.getChers(),
                    cmdColisageDto.getSachet(),
                    null,
                    1
            );
        } catch(DataAccessException ex) {
            log.error("Une erreur s'est produite lors de l'exécution de la procédure stocké  saisirColisageCommande | original message = {}", ex.getMessage());
            throw new RuntimeException("Une erreur s'est produite lors de l'exécution de la procédure");
        }
        log.info("Reponse of the stored proceudre saisirColisagecommande | response={}", response);

        if(response != 0)
            throw new RuntimeException("L'action n'a pas pu été accomplie");

        return response;
    }

    @Transactional
    @Override
    public Integer saisirColisageZone(CmdZoneColisageDto cmdZoneColisageDto) {
        log.info("| Entry | CommandeService.saisirColisageZone | Args | cmdZoneColisageDto={}", cmdZoneColisageDto);

        Integer companyId = this.customUserDetailsService.getCurrentCompanyId();
        log.info("Company ID fetched from the service {}", companyId);

        Integer response = 0;

        try {
            response = this.commandeRepository.saisirColisageCommande(
                    companyId,
                    cmdZoneColisageDto.getId(),
                    cmdZoneColisageDto.getType(),
                    cmdZoneColisageDto.getStkCode(),
                    cmdZoneColisageDto.getZone(),
                    null,
                    cmdZoneColisageDto.getColisV(),
                    cmdZoneColisageDto.getColisD(),
                    cmdZoneColisageDto.getFrigo(),
                    cmdZoneColisageDto.getPsycho(),
                    cmdZoneColisageDto.getChers(),
                    cmdZoneColisageDto.getSachet(),
                    null,
                    2
            );
        } catch(DataAccessException ex) {
            log.error("Une erreur s'est produite lors de l'exécution de la procédure stocké  saisirColisageZone | original message = {}", ex.getMessage());
            throw new RuntimeException("Une erreur s'est produite lors de l'exécution de la procédure");
        }
        log.info("Reponse of the stored proceudre saisirColisageZone | response={}", response);

        if(response != 0)
            throw new RuntimeException("L'action n'a pas pu été accomplie");

        return response;
    }

    @Override
    public ColisageDto getColisageZone(CmdZoneIdDto id) {
        log.info("| Entry | CommandeService.getColisageZone | Args | id={}", id);

        var projection = this.commandeRepository.getColisageZone(
                id.getCmpId(),
                id.getId(),
                id.getType(),
                id.getStkCode(),
                id.getZone()
        );
        log.info("Fetched the colisage projection from the repo | projection={}", projection);

        return new ColisageDto(
                projection.getColisV(),
                projection.getColisD(),
                projection.getColisF(),
                projection.getPsycho(),
                projection.getChers(),
                projection.getSachet()
        );
    }

    @Override
    public ColisageDto getColisageCommande(CmdIdDto id) {
        log.info("| Entry | CommandeService.getColisageCommande | Args | id={}", id);

        var projection = this.commandeRepository.getColisageCommande(id.getCmpId(), id.getId(), id.getType(), id.getStkCode());
        log.info("Fetched the colisage from the repo | projection={}", projection);

        return new ColisageDto(projection.getColisV(), projection.getColisD(), projection.getColisF(), projection.getPsycho(), projection.getChers(), projection.getSachet());
    }
}
