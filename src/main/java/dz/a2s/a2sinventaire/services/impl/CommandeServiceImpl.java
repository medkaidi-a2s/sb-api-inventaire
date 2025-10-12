package dz.a2s.a2sinventaire.services.impl;

import dz.a2s.a2sinventaire.dto.CommandeResponseDto;
import dz.a2s.a2sinventaire.dto.CommandeZoneResponseDto;
import dz.a2s.a2sinventaire.dto.affectation.CmdColisageDto;
import dz.a2s.a2sinventaire.dto.affectation.CmdIdDto;
import dz.a2s.a2sinventaire.dto.affectation.CmdZoneColisageDto;
import dz.a2s.a2sinventaire.dto.affectation.CmdZoneIdDto;
import dz.a2s.a2sinventaire.dto.commande.request.ListeEtiquetteRequest;
import dz.a2s.a2sinventaire.dto.commande.request.UpdateColisageRequest;
import dz.a2s.a2sinventaire.dto.commande.response.ColisageDto;
import dz.a2s.a2sinventaire.dto.commande.response.CommandeColisageResponse;
import dz.a2s.a2sinventaire.dto.commande.response.ListeEtiquettesResponse;
import dz.a2s.a2sinventaire.dto.response.PaginatedDataDto;
import dz.a2s.a2sinventaire.entities.Bac;
import dz.a2s.a2sinventaire.entities.VntBonBacs;
import dz.a2s.a2sinventaire.entities.views.Commande;
import dz.a2s.a2sinventaire.exceptions.DatabaseErrorException;
import dz.a2s.a2sinventaire.exceptions.ResourceNotUpdatedException;
import dz.a2s.a2sinventaire.exceptions.RessourceNotFoundException;
import dz.a2s.a2sinventaire.mappers.CommandeMapper;
import dz.a2s.a2sinventaire.mappers.CommandeZoneMapper;
import dz.a2s.a2sinventaire.repositories.BacRepository;
import dz.a2s.a2sinventaire.repositories.ColisRepository;
import dz.a2s.a2sinventaire.repositories.VntBonBacsRepository;
import dz.a2s.a2sinventaire.repositories.views.CommandeRepository;
import dz.a2s.a2sinventaire.repositories.views.CommandeZoneRepository;
import dz.a2s.a2sinventaire.services.CommandeService;
import dz.a2s.a2sinventaire.services.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class CommandeServiceImpl implements CommandeService {

    private final CustomUserDetailsService customUserDetailsService;
    private final CommandeRepository commandeRepository;
    private final CommandeZoneRepository commandeZoneRepository;
    private final BacRepository bacRepository;
    private final ColisRepository colisRepository;
    private final VntBonBacsRepository vntBonBacsRepository;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public PaginatedDataDto<CommandeColisageResponse> getCommandesColisage(
            String dateDebut,
            String dateFin,
            Integer statutPrepare,
            String rotation,
            Integer page,
            String search) {
        log.info("| Entry | CommandeService.getCommandesColisage | Args | dateDebut : {}, dateFin : {}, statutPrepare : {}, page : {}, rotation: {}", dateDebut, dateFin, statutPrepare, page, rotation);

        var size = 10;

        int start = (page * size) - size + 1;
        int end = page * size;

        Integer companyId = this.customUserDetailsService.getCurrentCompanyId();
        log.info("Company ID fetched from the service {}", companyId);

        var projections = this.commandeRepository.getListeCommandesColisage(companyId, dateDebut, dateFin, statutPrepare, start, end, rotation, search);
        log.info("Fetched the orders from the repo | projections.size={}", projections.size());

        var totalRecords = projections.isEmpty() ? 0 : projections.stream().findFirst().get().getTotalRecords();
        log.info("Fetched the total records from the projections | totalRecords={}", totalRecords);

        var listeCommandes = projections.stream().map(CommandeMapper::toCommandeColisageResponse).toList();
        log.info("Mapped the orders to the response | listeCommandes.size={}", listeCommandes.size());

        return new PaginatedDataDto<>(listeCommandes, totalRecords, (totalRecords + size - 1) / size, page, size);
    }

    @Override
    public List<CommandeResponseDto> getAllCommandes(String search, String date) {
        log.info("| Entry | CommandeService.getAllCommandes | Args | date : {}, search : {}", date, search);

        if (search == null || search.isEmpty()) return List.of();

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

        if (search == null || search.isEmpty()) return List.of();

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

        var username = this.customUserDetailsService.getCurrentUserCode();
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

            var deletedRows = this.commandeRepository.deleteEtiquettes(
                    companyId,
                    cmdColisageDto.getId(),
                    cmdColisageDto.getType(),
                    cmdColisageDto.getStkCode(),
                    null
            );
            log.info("Deleted the etiquettes from the repo | deletedRows={}", deletedRows);

            log.info("Generating labels for this order par zone");
            this.commandeRepository.generateEtiquette(
                    companyId,
                    cmdColisageDto.getId(),
                    cmdColisageDto.getStkCode(),
                    cmdColisageDto.getType(),
                    username
            );
        } catch (DataAccessException ex) {
            log.error("Une erreur s'est produite lors de l'exécution de la procédure stocké  saisirColisageCommande | original message = {}", ex.getMessage());
            throw new RuntimeException("Une erreur s'est produite lors de l'exécution de la procédure");
        }
        log.info("Reponse of the stored proceudre saisirColisagecommande | response={}", response);

        if (response != 0)
            throw new RuntimeException("L'action n'a pas pu été accomplie");

        return response;
    }

    @Transactional
    @Override
    public Integer saisirColisageZone(CmdZoneColisageDto cmdZoneColisageDto) {
        log.info("| Entry | CommandeService.saisirColisageZone | Args | cmdZoneColisageDto={}", cmdZoneColisageDto);

        Integer companyId = this.customUserDetailsService.getCurrentCompanyId();
        var username = this.customUserDetailsService.getCurrentUserCode();
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

            var deletedRows = this.commandeRepository.deleteEtiquettes(
                    companyId,
                    cmdZoneColisageDto.getId(),
                    cmdZoneColisageDto.getType(),
                    cmdZoneColisageDto.getStkCode(),
                    cmdZoneColisageDto.getZone()
            );
            log.info("Deleted the etiquettes from the repo | deletedRows={}", deletedRows);

            log.info("Generating labels for this order par zone");
            this.commandeRepository.generateEtiquetteZone(
                    companyId,
                    cmdZoneColisageDto.getId(),
                    cmdZoneColisageDto.getStkCode(),
                    cmdZoneColisageDto.getType(),
                    cmdZoneColisageDto.getZone(),
                    username
            );
        } catch (DataAccessException ex) {
            log.error("Une erreur s'est produite lors de l'exécution de la procédure stocké  saisirColisageZone | original message = {}", ex.getMessage());
            throw new RuntimeException("Une erreur s'est produite lors de l'exécution de la procédure");
        }
        log.info("Reponse of the stored proceudre saisirColisageZone | response={}", response);

        if (response != 0)
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
                projection.getSachet(),
                projection.getBacs(),
                projection.getPalettes()
        );
    }

    @Override
    public ColisageDto getColisageCommande(CmdIdDto id) {
        log.info("| Entry | CommandeService.getColisageCommande | Args | id={}", id);

        var projection = this.commandeRepository.getColisageCommande(id.getCmpId(), id.getId(), id.getType(), id.getStkCode());
        log.info("Fetched the colisage from the repo | projection={}", projection);

        return new ColisageDto(
                projection.getColisV(),
                projection.getColisD(),
                projection.getColisF(),
                projection.getPsycho(),
                projection.getChers(),
                projection.getSachet(),
                projection.getBacs(),
                projection.getPalettes());
    }

    @Override
    public List<Bac> getListeBacs() {
        log.info("| Entry | CommandeService.getListeBacs");

        var companyId = this.customUserDetailsService.getCurrentCompanyId();
        log.info("Company ID fetched from the service {}", companyId);

        var bacs = this.bacRepository.findListeBacs(companyId);
        log.info("Fetched the bacs from the repo | bacs.size={}", bacs.size());
        log.trace("Fetched the bacs from the repo | bacs={}", bacs);

        return bacs.stream().peek(bac -> bac.setKey(bac.getCmpId() + "-" + bac.getId() + "-" + bac.getTypeId() + "-" + bac.getCode())).toList();
    }

    @Transactional
    @Override
    public CommandeColisageResponse updateColisageGlobal(UpdateColisageRequest request) {
        log.info("| Entry | CommandeService.updateColisageGlobal | Args | request={}", request);

        try {
            var updatedRows = 0;

                updatedRows = this.commandeRepository.updateColisageGlobal(
                        request.getCmpId(),
                        request.getId(),
                        request.getType(),
                        request.getStkCode(),
                        request.getColisD(),
                        request.getColisV(),
                        request.getFrigo(),
                        request.getPsycho(),
                        request.getChers(),
                        request.getSachet(),
                        request.getBacs(),
                        request.getPalettes()
                );

                var savedBacs = this.vntBonBacsRepository.saveAll(request.getBacsIds().stream().map(bacId -> new VntBonBacs(
                        request.getCmpId(),
                        request.getId(),
                        request.getType(),
                        request.getStkCode(),
                        bacId.getId(),
                        bacId.getTypeId()
                )).toList());
                log.info("Saved the bacs from the repo | savedBacs.size={}", savedBacs.size());
                if (savedBacs.size() != request.getBacs() || savedBacs.size() != request.getBacsIds().size())
                    throw new DatabaseErrorException("Le nombre de bacs sauvegardé ne correspond pas au nombre de bacs fourni");

            log.info("Updated the colisage global from the repo | updatedRows={}", updatedRows);

            if (updatedRows == 0)
                throw new ResourceNotUpdatedException("La mise à jour du colisage global n'a pas pu être accomplie");

            var deletedRows = this.commandeRepository.deleteEtiquettes(
                    request.getCmpId(),
                    request.getId(),
                    request.getType(),
                    request.getStkCode(),
                    null
            );
            log.info("Deleted the etiquettes from the repo | deletedRows={}", deletedRows);

            var username = this.customUserDetailsService.getCurrentUserCode();

            this.commandeRepository.generateEtiquette(
                    request.getCmpId(),
                    request.getId(),
                    request.getStkCode(),
                    request.getType(),
                    username
            );

            var colisage = this.commandeRepository.getCommandeColisageGlobal(
                    request.getCmpId(),
                    request.getId(),
                    request.getType(),
                    request.getStkCode()
            );
            log.info("Fetched the colisage from the repo | colisage={}", colisage);

            return CommandeMapper.toCommandeColisageResponse(colisage);
        } catch (DataAccessException ex) {
            log.error("Une erreur s'est produite lors de la mise à jour des données du colisage | original message = {}", ex.getMessage());
            throw new DatabaseErrorException("Une erreur liée à la base de données s'est produite lors de la mise à jour des données du colisage");
        }
    }

    @Override
    public ListeEtiquettesResponse getEtiquettesColis(ListeEtiquetteRequest request) {
        log.info("| Entry | CommandeService.getEtiquettesColis | Args | request={}", request);

        var etiquettes = this.colisRepository.getEtiquettesColis(request.getCmpId(), request.getId(), request.getType(), request.getStkCode(), request.getZone());
        log.info("Fetched the etiquettes from the repo | etiquettes.size={}", etiquettes.size());

        var username = this.customUserDetailsService.getCurrentUserCode();

        if (!etiquettes.isEmpty()) {
            var firstRecord = etiquettes.stream().findFirst().get();

            return new ListeEtiquettesResponse(
                    firstRecord.getReference(),
                    firstRecord.getClient(),
                    firstRecord.getAdresse(),
                    firstRecord.getRegion(),
                    firstRecord.getZone(),
                    username,
                    etiquettes.stream().map(colis -> new ListeEtiquettesResponse.EtiquetteResponse(colis.getCode(), colis.getCmpId() + "-" + colis.getId() + "-" + colis.getType() + "-" + colis.getStkCode() + "-" + colis.getCode())).toList()
            );
        } else
            throw new RessourceNotFoundException("Aucune étiquette n'a été générée pour cette commande");
    }
}
