package dz.a2s.a2spreparation.services.impl;

import dz.a2s.a2spreparation.entities.views.Statistique;
import dz.a2s.a2spreparation.repositories.views.StatistiqueRepository;
import dz.a2s.a2spreparation.services.StatistiqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StatistiqueServiceImpl implements StatistiqueService {
    private final StatistiqueRepository statistiqueRepository;

    @Override
    public List<Statistique> getStatistiques(String dateDebut, String dateFin) {
        List<Statistique> statistiques = this.statistiqueRepository.getStatistiques(dateDebut, dateFin);

        return statistiques;
    }
}
