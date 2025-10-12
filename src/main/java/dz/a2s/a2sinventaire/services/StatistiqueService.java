package dz.a2s.a2sinventaire.services;

import dz.a2s.a2sinventaire.dto.statistique.StatsPreparateurDto;
import dz.a2s.a2sinventaire.entities.views.Statistique;

import java.util.List;

public interface StatistiqueService {

    List<Statistique> getStatistiques(String dateDebut, String dateFin);

    List<StatsPreparateurDto> getStatistiquesParPreparateur(String dateDebut, String dateFin);

    List<StatsPreparateurDto> getStatistiquesParControleur(String dateDebut, String dateFin);

}
