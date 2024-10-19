package dz.a2s.a2spreparation.repositories.views;

import dz.a2s.a2spreparation.entities.keys.VenteId;
import dz.a2s.a2spreparation.entities.views.PrpCdeUsrCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrpCdeUsrCodeRepository extends JpaRepository<PrpCdeUsrCode, VenteId> {

    @Query(value = "SELECT * FROM PRP_LISTE_CDE_AFFECTES2 WHERE PREPARATEUR_CODE_USER = :userCode AND (:date IS NULL OR TRUNC(VNT_DATE) = TO_DATE(:date, 'yyyy-MM-dd'))", nativeQuery = true)
    List<PrpCdeUsrCode> getCmdParPreparateur(@Param("userCode") String userCode, @Param("date") String date);

    @Procedure(procedureName = "logistiques.P_EDIT_START_PREPAR_CDE", outputParameterName = "p_msg")
    Integer startPrepareCde(
            @Param("p_vnt_cmp_id") int p_vnt_cmp_id,
            @Param("p_vnt_id") int p_vnt_id,
            @Param("p_vnt_type") String p_vnt_type,
            @Param("p_vnt_stk_code") String p_vnt_stk_code
    );

    @Procedure(procedureName = "logistiques.P_SET_CDE_PREPARED", outputParameterName = "p_msg")
    Integer setCommandePrepared(
            @Param("p_cmp") int p_cmp,
            @Param("p_vnt") int p_vnt,
            @Param("p_stk") String p_stk,
            @Param("p_type") String p_type,
            @Param("p_user") String p_user
    );

}
