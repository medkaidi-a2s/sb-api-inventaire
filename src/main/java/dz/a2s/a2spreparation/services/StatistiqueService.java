package dz.a2s.a2spreparation.services;

import dz.a2s.a2spreparation.entities.views.Statistique;

import java.util.List;

public interface StatistiqueService {

    List<Statistique> getStatistiques(String dateDebut, String dateFin);

}
