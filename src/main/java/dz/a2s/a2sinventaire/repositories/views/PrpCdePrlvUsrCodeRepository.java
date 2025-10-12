package dz.a2s.a2sinventaire.repositories.views;

import dz.a2s.a2sinventaire.entities.keys.StkListesId;
import dz.a2s.a2sinventaire.entities.views.PrpCdePrlvUsrCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrpCdePrlvUsrCodeRepository extends JpaRepository<PrpCdePrlvUsrCode, StkListesId> {

    @Query(value = "SELECT * FROM PRP_LISTE_PLVS_AFFECTE_USRCODE WHERE PREPARATEUR_CODE = :userCode AND (:date IS NULL OR TRUNC(SLT_DATE) = TO_DATE(:date, 'yyyy-MM-dd'))", nativeQuery = true)
    List<PrpCdePrlvUsrCode> getCmdPrlvParPreparateur(@Param("userCode") String userCode, @Param("date") String date);

    @Query(value = "SELECT * FROM PRP_LISTE_PLVS_AFFECTE_USRCODE WHERE SLT_CMP_ID = :cmpId AND SLT_ID = :id AND SLT_TYPE = :type AND SLT_ANNEE = :annee", nativeQuery = true)
    PrpCdePrlvUsrCode getOneCmdPrlv(@Param("cmpId") Integer cmpId, @Param("id") Integer id, @Param("type") String type, @Param("annee") Integer annee);

    @Procedure(procedureName = "logistiques.P_EDIT_START_PREPAR_PLV", outputParameterName = "p_msg")
    Integer startPreparePrlv(
            @Param("p_cmp") int p_cmp,
            @Param("p_slt_id") int p_slt_id,
            @Param("p_slt_type") String p_slt_type,
            @Param("p_slt_annee") int p_slt_annee
    );

}
