package dz.a2s.a2sinventaire.services.impl;

import dz.a2s.a2sinventaire.dto.common.ListResponse;
import dz.a2s.a2sinventaire.dto.inventaire.request.SaisiEcartRequest;
import dz.a2s.a2sinventaire.dto.inventaire.request.SaisiRequest;
import dz.a2s.a2sinventaire.dto.inventaire.response.ComptageAccessResponse;
import dz.a2s.a2sinventaire.dto.inventaire.response.EcartLineResponse;
import dz.a2s.a2sinventaire.dto.inventaire.response.InventaireLineResponse;
import dz.a2s.a2sinventaire.dto.inventaire.response.SaisiResponse;
import dz.a2s.a2sinventaire.dto.response.PaginatedDataDto;
import dz.a2s.a2sinventaire.entities.Inventaire;
import dz.a2s.a2sinventaire.exceptions.ActionNotAllowedException;
import dz.a2s.a2sinventaire.exceptions.DatabaseErrorException;
import dz.a2s.a2sinventaire.exceptions.RessourceNotFoundException;
import dz.a2s.a2sinventaire.mappers.InventaireMappers;
import dz.a2s.a2sinventaire.repositories.InventaireRepository;
import dz.a2s.a2sinventaire.services.CustomUserDetailsService;
import dz.a2s.a2sinventaire.services.InventaireService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class InventaireServiceImpl implements InventaireService {

    private final CustomUserDetailsService customUserDetailsService;
    private final InventaireRepository inventaireRepository;

    @Override
    public List<Inventaire> getListInventaires() {
        log.info("| Entry | InventaireService.getListInventaires()");

        try {
            var projections = this.inventaireRepository.getListInventaires();
            log.info("Fetched the list of inventaire from the repo | projections.size={}", projections.size());

            var inventaires = projections.stream().map(InventaireMappers::fromInventaireProjection).toList();
            log.info("Mapped projections to inventaire entities | inventaires={}", inventaires);

            return inventaires;
        } catch (DataAccessException ex) {
            log.error("Failed to fetch the list of inventaire from the repo | error={}", ex.getMessage());
            throw new DatabaseErrorException("Une erreur est survenue lors de la récupération de la liste des inventaires");
        }
    }

    @Override
    public List<ListResponse> getListComptages() {
        log.info("| Entry | InventaireService.getListComptage()");

        try {
            var projections = this.inventaireRepository.getListComptage();
            log.info("Fetched the list of comptage from the repo | projections.size={}", projections.size());

            var comptages = projections.stream().map(InventaireMappers::fromListProjection).toList();
            log.info("Mapped projections to list response entities | comptages={}", comptages);

            return comptages;
        } catch (DataAccessException ex) {
            log.error("Failed to fetch the list of comptages from the repo | error={}", ex.getMessage());
            throw new DatabaseErrorException("Une erreur est survenue lors de la récupération de la liste des comptages");
        }
    }

    @Override
    public ComptageAccessResponse getComptageAccess(Integer invId, String depot) {
        log.info("| Entry | InventaireService.getComptageAccess() | Args | invId={}, depot={}", invId, depot);

        var username = this.customUserDetailsService.getCurrentUserCode();
        log.info("Fetched username : {}", username);

        try {
            var projection = this.inventaireRepository.getComptageAccess(depot, invId, username);

            if (projection == null) {
                log.error("Projection is null | user does not have access to the inventory");
                throw new ActionNotAllowedException("Accès refusé : Vous n'avez pas accès à cette campagne d'inventaire");
            }

            var access = InventaireMappers.fromComptageAccessProjection(projection);
            log.info("Mapped projection to comptage access response | access={}", access);

            return access;
        } catch (DataAccessException ex) {
            log.error("Failed to fetch the comptage access from the repo | error={}", ex.getMessage());
            throw new DatabaseErrorException("Une erreur est survenue lors de la récupération des droits d'accès au comptage");
        }
    }

    @Override
    public String checkEmplacement(String emplacement) {
        log.info("| Entry | InventaireService.checkEmplacement() | Args | emplacement={}", emplacement);

        var response = this.inventaireRepository.checkEmplacement(emplacement.trim());
        log.info("Checked the inventory placement | response = {}", response);

        if (response == null) {
            log.error("Emplacement is null | emplacement does not exist");
            throw new RessourceNotFoundException("L'emplacement spécifié est introuvable");
        }

        return response;
    }

    @Override
    public PaginatedDataDto<InventaireLineResponse> getInventaireLines(Integer invId, String depot, Integer comptage, String emplacement, Integer stockZero, String search, Integer page) {
        log.info("| Entry | InventaireService.getInventaireLines() | Args | invId={}, comptage={}, emplacement={}, stockZero={}, search={}, page={}", invId, comptage, emplacement, stockZero, search, page);

        var size = 10;
        int start = (page * size) - size + 1;
        int end = page * size;

        var projections = this.inventaireRepository.getInventaireLines(invId, depot, comptage, emplacement, stockZero, search, start, end);
        log.info("Fetched the list of inventaire lines from the repo | projections.size={}", projections.size());

        var totalRecords = projections.isEmpty() ? 0 : projections.stream().findFirst().get().getTotalRecords();
        log.info("Fetched the total records from the projections | totalRecords={}", totalRecords);

        var lines = projections.stream().map(InventaireMappers::fromInventaireLineProjection).toList();
        log.info("Mapped projections to inventaire line responses | lines.size={}", lines.size());

        return new PaginatedDataDto<>(lines, totalRecords, (totalRecords + size - 1) / size, page, size);
    }

    @Override
    public PaginatedDataDto<EcartLineResponse> getEcartLines(Integer invId, Integer isEcart, String search, Integer page) {
        log.info("| Entry | InventaireService.getEcartLines() | Args | invId={}, isEcart={}, search={}, page={}", invId, isEcart, search, page);

        Integer cmpId = this.customUserDetailsService.getCurrentCompanyId();

        var size = 10;
        int start = (page * size) - size + 1;
        int end = page * size;

        var projections = this.inventaireRepository.getEcartLines(cmpId, invId, search, isEcart, start, end);
        log.info("Fetched the list of ecart lines from the repo | projections.size={}", projections.size());

        var totalRecords = projections.isEmpty() ? 0 : projections.stream().findFirst().get().getTotalRecords();
        log.info("Fetched the total records from the projections | totalRecords={}", totalRecords);

        var lines = projections.stream().map(InventaireMappers::fromEcartLineProjection).toList();
        log.info("Mapped projections to ecart line responses | lines.size={}", lines.size());

        return new PaginatedDataDto<>(lines, totalRecords, (totalRecords + size - 1) / size, page, size);
    }

    @Override
    public SaisiResponse saisirInventaire(SaisiRequest request) {
        log.info("| Entry | InventaireService.saisirInventaire() | Args | request={}", request);

        var access = this.getComptageAccess(request.getInvId(), request.getDepot());
        log.info("Fetched inventaire access rights from the service | access={}", access);

        Integer comptageAccess = 0;

        switch (request.getComptage()) {
            case 1:
                comptageAccess = access.comptage1();
                break;
            case 2:
                comptageAccess = access.comptage2();
                break;
            case 3:
                comptageAccess = access.comptage3();
                break;
            default:
                comptageAccess = 0;
                break;
        }

        if (comptageAccess == 0) {
            log.error("User does not have access to the comptage");
            throw new ActionNotAllowedException("Accès refusé : Vous n'avez pas accès à ce comptage");
        }

        var username = this.customUserDetailsService.getCurrentUserCode();
        var companyId = this.customUserDetailsService.getCurrentCompanyId();

        try {
            this.inventaireRepository.saisirInventaire(
                    companyId,
                    request.getInvId(),
                    request.getNlotInterne(),
                    request.getMedId(),
                    request.getDepot(),
                    request.getComptage(),
                    request.getQuantite(),
                    request.getMotif(),
                    request.getEmplacement(),
                    request.getNoLigne(),
                    username
            );
            log.info("Persisted saisi de l'inventaire into the database");

            return new SaisiResponse(request.getQuantite(), request.getMotif());
        } catch (DataAccessException ex) {
            log.error("Failed to save the inventaire line to the repo | error={}", ex.getMessage());
            throw new DatabaseErrorException("Une erreur est survenue lors de la saisie de la ligne d'inventaire");
        }
    }

    @Transactional
    @Override
    public int updateEcartLine(SaisiEcartRequest request) {
        log.info("| Entry | InventaireService.updateEcartLine() | Args | request={}", request);

        var companyId = this.customUserDetailsService.getCurrentCompanyId();

        var rowsNumber = this.inventaireRepository.updateEcartLine(
                companyId,
                request.getInvId(),
                request.getNlotInterne(),
                request.getMedId(),
                request.getDepot(),
                request.getQuantite(),
                request.getNoLigne()
        );
        log.info("Updated rows number = {}", rowsNumber);

        if(rowsNumber == 0)
            throw new DatabaseErrorException("La correction de l’écart n’a pas pu être effectuée.");

        if(rowsNumber != 1)
            throw new DatabaseErrorException("Erreur de mise à jour, plusieurs enregistrements ont été affectés.");
        return rowsNumber;
    }
}
