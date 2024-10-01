package dz.a2s.a2spreparation.repositories.views;

import dz.a2s.a2spreparation.entities.keys.StkListesId;
import dz.a2s.a2spreparation.entities.views.PrpCdePrlv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrpCdePrlvRepository extends JpaRepository<PrpCdePrlv, StkListesId> {

    @Query(value = "SELECT * FROM PRP_LISTE_PLVS WHERE (:date IS NULL OR TRUNC(SLT_DATE) = TO_DATE(:date, 'yyyy-MM-dd'))", nativeQuery = true)
    List<PrpCdePrlv> getListeCommandesPrlv(@Param("date") String date);

    @Query(value = "SELECT * FROM PRP_LISTE_PLVS_AFFECTE WHERE (:date IS NULL OR TRUNC(SLT_DATE) = TO_DATE(:date, 'yyyy-MM-dd'))", nativeQuery = true)
    List<PrpCdePrlv> getListCmdPrlvAssigned(@Param("date") String date);

    @Procedure(procedureName = "logistiques.p_affcte_plv_prepare", outputParameterName = "p_msg")
    Integer affectCommandePrpPrlv(
            @Param("p_cmp") int p_cmp,
            @Param("p_slt_id") int p_slt_id,
            @Param("p_slt_type") String p_slt_type,
            @Param("p_slt_annee") int p_slt_annee,
            @Param("p_prp") int p_prp,
            @Param("p_cnt1") int p_cnt1,
            @Param("p_cnt2") int p_cnt2,
            @Param("p_user") String p_user
    );

}
