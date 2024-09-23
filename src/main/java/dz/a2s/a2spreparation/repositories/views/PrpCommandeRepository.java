package dz.a2s.a2spreparation.repositories.views;

import dz.a2s.a2spreparation.entities.keys.VenteId;
import dz.a2s.a2spreparation.entities.views.PrpCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrpCommandeRepository extends JpaRepository<PrpCommande, VenteId> {

    @Query(value = "SELECT * FROM PRP_LISTE_CDES WHERE VNT_CMP_ID = :companyId AND STATUT = :status", nativeQuery = true)
    List<PrpCommande> getListCommande(@Param("companyId") Integer companyId, @Param("status") String status);

    @Procedure("logistiques.p_affcte_cde_prepare")
    Integer affectCommandePrp(
            int p_cmp,
            int p_vnt,
            int p_stk,
            int p_type,
            int p_prp,
            int p_cnt1,
            int p_cnt2,
            String p_user
    );

}
