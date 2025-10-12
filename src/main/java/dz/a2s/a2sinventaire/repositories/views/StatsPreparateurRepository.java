package dz.a2s.a2sinventaire.repositories.views;

import dz.a2s.a2sinventaire.entities.views.StatsPreparateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatsPreparateurRepository extends JpaRepository<StatsPreparateur, Integer> {

    @Query(value = """
            SELECT ROW_NUMBER() OVER (ORDER BY t.VNT_STATUT_PREPARE) AS row_id,
                   t.VNT_STATUT_PREPARE,
                   statut,
                   t.Preparateur,
                   count(*) nbr_de,
                   sum(t.nbr_ligne) nbr_ligne,
                   sum(t.VNT_TOTAL_TTC) total
              from PRP_STATS_CDES t
              where (TRUNC(t.vnt_date) between to_date(:dateDebut,'DD/MM/RRRR') AND to_date(:dateFin,'DD/MM/RRRR')) AND (t.Preparateur is not null)
             group by t.VNT_STATUT_PREPARE, statut,t.Preparateur
             order by VNT_STATUT_PREPARE
            """, nativeQuery = true)
    List<StatsPreparateur> getStatistiquesParPreparateur(@Param("dateDebut") String dateDebut, @Param("dateFin") String dateFin);

    @Query(value = """
                    SELECT ROW_NUMBER() OVER (ORDER BY t.VNT_STATUT_PREPARE) AS row_id,
                       t.VNT_STATUT_PREPARE,
                       statut,
                       t.Controleur1 Preparateur,
                       count(*) nbr_de,
                       sum(t.nbr_ligne) nbr_ligne,
                       sum(t.VNT_TOTAL_TTC) total
                  from PRP_STATS_CDES t
                  where (TRUNC(t.vnt_date) between to_date(:dateDebut,'DD/MM/RRRR') AND to_date(:dateFin,'DD/MM/RRRR')) AND (t.Preparateur is not null)
                 group by t.VNT_STATUT_PREPARE, statut,t.Controleur1
                 order by VNT_STATUT_PREPARE
            """, nativeQuery = true)
    List<StatsPreparateur> getStatistiquesParControleur(@Param("dateDebut") String dateDebut, @Param("dateFin") String dateFin);

}
