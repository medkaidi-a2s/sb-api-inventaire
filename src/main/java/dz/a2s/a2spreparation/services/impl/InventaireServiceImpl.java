package dz.a2s.a2spreparation.services.impl;

import dz.a2s.a2spreparation.dto.common.ListResponse;
import dz.a2s.a2spreparation.dto.inventaire.response.ComptageAccessResponse;
import dz.a2s.a2spreparation.entities.Inventaire;
import dz.a2s.a2spreparation.exceptions.ActionNotAllowedException;
import dz.a2s.a2spreparation.exceptions.DatabaseErrorException;
import dz.a2s.a2spreparation.mappers.InventaireMappers;
import dz.a2s.a2spreparation.repositories.InventaireRepository;
import dz.a2s.a2spreparation.services.CustomUserDetailsService;
import dz.a2s.a2spreparation.services.InventaireService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

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

        Integer companyId = this.customUserDetailsService.getCurrentCompanyId();

        try {
            var projections = this.inventaireRepository.getListInventaires(companyId);
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

        Integer companyId = this.customUserDetailsService.getCurrentCompanyId();

        try {
            var projections = this.inventaireRepository.getListComptage(companyId);
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
    public ComptageAccessResponse getComptageAccess(Integer invId) {
        log.info("| Entry | InventaireService.getComptageAccess() | Args | invId={}", invId);

        var username = this.customUserDetailsService.getCurrentUserCode();
        var companyId = this.customUserDetailsService.getCurrentCompanyId();
        log.info("Fetched companyId : {} and username : {}", companyId, username);

        try {
            var projection = this.inventaireRepository.getComptageAccess(companyId, invId, username);

            if(projection == null) {
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
}
