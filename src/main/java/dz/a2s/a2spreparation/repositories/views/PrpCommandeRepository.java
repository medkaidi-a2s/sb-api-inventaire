package dz.a2s.a2spreparation.repositories.views;

import dz.a2s.a2spreparation.entities.keys.VenteId;
import dz.a2s.a2spreparation.entities.views.PrpCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PrpCommandeRepository extends JpaRepository<PrpCommande, VenteId> {

    @Query(value = "SELECT * FROM PRP_LISTE_CDES WHERE VNT_CMP_ID = :companyId AND (:date IS NULL OR TRUNC(VNT_DATE) = TO_DATE(:date, 'yyyy-MM-dd'))", nativeQuery = true)
    List<PrpCommande> getListCommande(@Param("companyId") Integer companyId, @Param("date") String date);

    @Query(value = "SELECT * FROM PRP_LISTE_CDE_AFFECTES WHERE VNT_CMP_ID = :companyId AND (:date IS NULL OR TRUNC(VNT_DATE) = TO_DATE(:date, 'yyyy-MM-dd'))", nativeQuery = true)
    List<PrpCommande> getListCommandeAssigned(@Param("companyId") Integer companyId, @Param("date") String date);

    @Procedure(procedureName = "logistiques.p_affcte_cde_prepare", outputParameterName = "p_msg")
    Integer affectCommandePrp(
            @Param("p_cmp") int p_cmp,
            @Param("p_vnt") int p_vnt,
            @Param("p_stk") int p_stk,
            @Param("p_type") int p_type,
            @Param("p_prp") int p_prp,
            @Param("p_cnt1") int p_cnt1,
            @Param("p_cnt2") int p_cnt2,
            @Param("p_user") String p_user
    );

}
