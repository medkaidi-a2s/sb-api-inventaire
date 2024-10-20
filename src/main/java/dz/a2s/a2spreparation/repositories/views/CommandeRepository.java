package dz.a2s.a2spreparation.repositories.views;

import dz.a2s.a2spreparation.entities.keys.VenteId;
import dz.a2s.a2spreparation.entities.views.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommandeRepository extends JpaRepository<Commande, VenteId> {

    @Query(value = """
                SELECT *
                FROM PRP_LISTE_CDE_CONTROLES
                WHERE VNT_CMP_ID = :companyId
                  AND (:date IS NULL OR TRUNC(VNT_DATE) = TO_DATE(:date, 'yyyy-MM-dd'))
                  AND VERIFICATEUR_ID = :utilisateurId
            """, nativeQuery = true)
    List<Commande> getPreparedCommandes(@Param("companyId") Integer companyId, @Param("utilisateurId") Integer utilisateurId, @Param("date") String date);

}
