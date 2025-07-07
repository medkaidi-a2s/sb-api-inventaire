package dz.a2s.a2spreparation.repositories.views;

import dz.a2s.a2spreparation.entities.keys.VenteDetailsId;
import dz.a2s.a2spreparation.entities.views.VenteDetails;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VenteDetailsRepository extends JpaRepository<VenteDetails, VenteDetailsId> {

    @Query(value = """
            SELECT *
            FROM PRP_VENTE_DETAILS
            WHERE VND_CMP_ID = :cmpId
              AND VND_VNT_ID = :id
              AND VND_VNT_TYPE = :type
              AND VND_STK_CODE = :code
            """, nativeQuery = true)
    List<VenteDetails> getDetailsByVente(
            @Param("cmpId") Integer cmpId,
            @Param("id") Integer id,
            @Param("type") String type,
            @Param("code") String code
    );

    @Transactional
    @Modifying
    @Query(value = """
            UPDATE VNT_BON_DETAILS V
               SET V.VND_QTE_PREPARE   = :qte,
                   V.VND_PREPARE_FLAG  = 1,
                   V.VND_PREPARE_MOTIF = :motif
             WHERE (V.VND_PREPARE_FLAG = 0 OR V.VND_PREPARE_FLAG IS NULL)
               AND V.VND_VNT_ID = :id
               AND V.VND_VNT_TYPE = :type
               AND V.VND_CMP_ID = :cmpId
               AND V.VND_STK_CODE = :stkCode
               AND V.VND_NO = :no
            """, nativeQuery = true)
    Integer setPreparedQuantity(
            @Param("cmpId") Integer cmpId,
            @Param("id") Integer id,
            @Param("type") String type,
            @Param("stkCode") String stkCode,
            @Param("no") Integer no,
            @Param("qte") Integer qte,
            @Param("motif") Integer motif
    );

    @Transactional
    @Modifying
    @Query(value = """
            UPDATE VNT_BON_DETAILS V
               SET V.VND_CONTROLE_QTE1   = :qte,
                   V.VND_CONTROLE_FLAG1  = 1,
                   V.VND_CONTROLE_MOTIF1 = :motif
             WHERE NVL(V.VND_CONTROLE_FLAG1, 0) <> 1
               AND V.VND_VNT_ID = :id
               AND V.VND_VNT_TYPE = :type
               AND V.VND_CMP_ID = :cmpId
               AND V.VND_STK_CODE = :stkCode
               AND V.VND_NO = :no
            """, nativeQuery = true)
    Integer setControlledQuantity(
            @Param("cmpId") Integer cmpId,
            @Param("id") Integer id,
            @Param("type") String type,
            @Param("stkCode") String stkCode,
            @Param("no") Integer no,
            @Param("qte") Integer qte,
            @Param("motif") Integer motif
    );

    @Procedure(procedureName = "logistiques.p_del_ligne_cde", outputParameterName = "p_msg")
    Integer deleteLigneCommande(
            @Param("p_cmp") int cmd,
            @Param("p_vnt") int vnt,
            @Param("p_stk") String stk,
            @Param("p_type") String type,
            @Param("p_vnd_no") int no,
            @Param("p_user") String user
    );

    @Procedure(procedureName = "logistiques.p_update_qte_cde", outputParameterName = "p_msg")
    Integer editQuantityCommande(
            @Param("p_cmp") int cmd,
            @Param("p_vnt") int vnt,
            @Param("p_stk") String stk,
            @Param("p_type") String type,
            @Param("p_vnd_no") int no,
            @Param("p_qte") int qte,
            @Param("p_user") String user
    );

}
