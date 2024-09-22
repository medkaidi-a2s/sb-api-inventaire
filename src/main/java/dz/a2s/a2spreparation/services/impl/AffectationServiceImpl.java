package dz.a2s.a2spreparation.services.impl;

import dz.a2s.a2spreparation.entities.views.PrpCdeZone;
import dz.a2s.a2spreparation.entities.views.PrpCommande;
import dz.a2s.a2spreparation.entities.views.PrpPrepareControle;
import dz.a2s.a2spreparation.exceptions.RessourceNotFoundException;
import dz.a2s.a2spreparation.repositories.views.PrpCommandeRepository;
import dz.a2s.a2spreparation.repositories.views.PrpListeCdeZonesRepository;
import dz.a2s.a2spreparation.repositories.views.PrpPrepareControleRepository;
import dz.a2s.a2spreparation.services.AffectationService;
import dz.a2s.a2spreparation.services.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AffectationServiceImpl implements AffectationService {

    private final PrpListeCdeZonesRepository prpListeCdeZonesRepository;
    private final PrpCommandeRepository prpCommandeRepository;
    private final PrpPrepareControleRepository prpPrepareControleRepository;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public List<PrpCdeZone> getListCmdZones() throws RessourceNotFoundException {
        log.info("Entering getListCmdZones method from the AffectationService");

        Integer companyId = this.customUserDetailsService.getCurrentCompanyId();
        log.info("Fetching orders for the company {}", companyId);

        log.info("Fetching data from the repo");
        List<PrpCdeZone> listeCommandes = this.prpListeCdeZonesRepository.getListCmdZones(companyId);
        log.info("Data fetched from the repo length = {}", listeCommandes.size());

        if(listeCommandes.isEmpty())
            throw new RessourceNotFoundException("Liste des commandes affectées par zone vide");

        return listeCommandes;
    }

    @Override
    public List<PrpCommande> getListCommande() {
        log.info("Entering getListCommande method from the AffectationService");

        Integer companyId = this.customUserDetailsService.getCurrentCompanyId();
        log.info("Fetching orders for the company {}", companyId);

        log.info("Fetching data from the repo");
        List<PrpCommande> listeCommandes = this.prpCommandeRepository.getListCommande(companyId);
        log.info("Data fetched from the repo length = {}", listeCommandes.size());

        if(listeCommandes.isEmpty())
            throw new RessourceNotFoundException("Liste des commandes vide");

        return listeCommandes;
    }

    @Override
    public List<PrpPrepareControle> getAllPreparateurs() {
        log.info("Entering getAllPreparateurs method from the AffectationService");

        log.info("Fetching data from the repository");
        List<PrpPrepareControle> preparateurs = this.prpPrepareControleRepository.getAllPreparateurs();
        log.info("Data fetched from the repository with length {}", preparateurs.size());

        if(preparateurs.isEmpty())
            throw new RessourceNotFoundException(("Liste des préparateurs vide"));

        return preparateurs;
    }

    @Override
    public List<PrpPrepareControle> getAllControleurs() {
        log.info("Entering getAllPreparateurs method from the AffectationService");

        log.info("Fetching data from the repository");
        List<PrpPrepareControle> controleurs = this.prpPrepareControleRepository.getAllControleurs();
        log.info("Data fetched from the repository with length {}", controleurs.size());

        if(controleurs.isEmpty())
            throw new RessourceNotFoundException(("Liste des contrôleurs vide"));

        return controleurs;
    }
}
