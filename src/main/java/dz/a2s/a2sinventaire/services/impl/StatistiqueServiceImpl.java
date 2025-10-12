package dz.a2s.a2sinventaire.services.impl;

import dz.a2s.a2sinventaire.dto.statistique.StatsPreparateurDto;
import dz.a2s.a2sinventaire.entities.views.Statistique;
import dz.a2s.a2sinventaire.entities.views.StatsPreparateur;
import dz.a2s.a2sinventaire.mappers.statistique.StatistiqueMapper;
import dz.a2s.a2sinventaire.repositories.views.StatistiqueRepository;
import dz.a2s.a2sinventaire.repositories.views.StatsPreparateurRepository;
import dz.a2s.a2sinventaire.services.StatistiqueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class StatistiqueServiceImpl implements StatistiqueService {
    private final StatistiqueRepository statistiqueRepository;
    private final StatsPreparateurRepository statsPreparateurRepository;

    @Override
    public List<Statistique> getStatistiques(String dateDebut, String dateFin) {
        log.info("Point d'entrée de la méthode getStatistiques de StatistiqueService");

        List<Statistique> statistiques = this.statistiqueRepository.getStatistiques(dateDebut, dateFin);

        return statistiques;
    }

    @Override
    public List<StatsPreparateurDto> getStatistiquesParPreparateur(String dateDebut, String dateFin) {
        log.info("Point d'entrée de la méthode getStatistiquesParPreparateur de StatistiqueService");

        List<StatsPreparateur> statistiques = this.statsPreparateurRepository.getStatistiquesParPreparateur(dateDebut, dateFin);

        List<StatsPreparateurDto> dto = StatistiqueMapper.toStatsPreparateurDtoList(statistiques);

        return dto;
    }

    @Override
    public List<StatsPreparateurDto> getStatistiquesParControleur(String dateDebut, String dateFin) {
        log.info("Point d'entrée de la méthode getStatistiquesParControleur de StatistiqueService");

        List<StatsPreparateur> statistiques = this.statsPreparateurRepository.getStatistiquesParControleur(dateDebut, dateFin);

        List<StatsPreparateurDto> dto = StatistiqueMapper.toStatsPreparateurDtoList(statistiques);

        return dto;
    }
}
