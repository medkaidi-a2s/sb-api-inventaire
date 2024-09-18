package dz.a2s.a2spreparation.services.impl;

import dz.a2s.a2spreparation.entities.views.PrpListeCdeZones;
import dz.a2s.a2spreparation.exceptions.RessourceNotFoundException;
import dz.a2s.a2spreparation.repositories.views.PrpListeCdeZonesRepository;
import dz.a2s.a2spreparation.services.AffectationService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AffectationServiceImpl implements AffectationService {

    private final PrpListeCdeZonesRepository prpListeCdeZonesRepository;

    @Override
    public List<PrpListeCdeZones> getAll() throws RessourceNotFoundException {
        log.info("Entering findAll method from the AffectationService");

        log.info("Fetching data from the repo");
        List<PrpListeCdeZones> listeCommandes = this.prpListeCdeZonesRepository.getAll();
        log.info("Data fetched from the repo {}", listeCommandes);

        if(listeCommandes.isEmpty())
            throw new RessourceNotFoundException("Liste des commandes affectées par zone vide");

        return listeCommandes;
    }
}
