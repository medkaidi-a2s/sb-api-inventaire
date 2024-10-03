package dz.a2s.a2spreparation.repositories.views;

import dz.a2s.a2spreparation.entities.keys.StkListesId;
import dz.a2s.a2spreparation.entities.views.PrpCdePrlvPrepCont;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface PrpCdePrlvPrepContRepository extends JpaRepository<PrpCdePrlvPrepCont, StkListesId> {

    @Query(value = "SELECT * FROM PRP_CDE_PRLV_PREP_CONT WHERE SLT_CMP_ID = :cmpId AND SLT_ID = :id AND SLT_TYPE = :type AND SLT_ANNEE = :annee", nativeQuery = true)
    PrpCdePrlvPrepCont getPrepCont (
            @Param("cmpId") Integer cmpId,
            @Param("id") Integer id,
            @Param("type") String type,
            @Param("annee") Integer annee
    );

    @Procedure(procedureName = "logistiques.P_EDIT_AFFECT_PLV_PREPARE", outputParameterName = "p_msg")
    Integer editAffectCommandePrpPrlv(
            @Param("p_cmp") int p_cmp,
            @Param("p_slt_id") int p_slt_id,
            @Param("p_slt_type") String p_slt_type,
            @Param("p_slt_annee") int p_slt_annee,
            @Param("p_prp") int p_prp,
            @Param("p_cnt1") int p_cnt1,
            @Param("p_cnt2") int p_cnt2
    );

}
