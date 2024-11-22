package dz.a2s.a2spreparation.services;

import dz.a2s.a2spreparation.dto.statistique.StatsPreparateurDto;
import dz.a2s.a2spreparation.entities.views.Statistique;
import dz.a2s.a2spreparation.entities.views.StatsPreparateur;

import java.util.List;

public interface StatistiqueService {

    List<Statistique> getStatistiques(String dateDebut, String dateFin);

    List<StatsPreparateurDto> getStatistiquesParPreparateur(String dateDebut, String dateFin);

    List<StatsPreparateurDto> getStatistiquesParControleur(String dateDebut, String dateFin);

}
