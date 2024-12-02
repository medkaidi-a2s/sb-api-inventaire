package dz.a2s.a2spreparation.repositories.views;

import dz.a2s.a2spreparation.entities.views.Statistique;
import dz.a2s.a2spreparation.entities.views.StatsPreparateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StatistiqueRepository extends JpaRepository<Statistique, Integer> {

    @Query(value = """
            select t.VNT_STATUT_PREPARE,
                   statut,
                   count(*) nbr_de,
                   sum(t.nbr_ligne) nbr_ligne,
                   sum(t.VNT_TOTAL_TTC) total
              from PRP_STATS_CDES t
              where TRUNC(t.vnt_date) between to_date(:dateDebut,'DD/MM/RRRR') AND to_date(:dateFin,'DD/MM/RRRR')
             group by t.VNT_STATUT_PREPARE, statut
             order by VNT_STATUT_PREPARE
            """, nativeQuery = true)
    List<Statistique> getStatistiques(@Param("dateDebut") String dateDebut, @Param("dateFin") String dateFin);

}
