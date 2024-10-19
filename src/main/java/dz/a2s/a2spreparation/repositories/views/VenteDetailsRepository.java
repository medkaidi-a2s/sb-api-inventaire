package dz.a2s.a2spreparation.repositories.views;

import dz.a2s.a2spreparation.entities.keys.VenteDetailsId;
import dz.a2s.a2spreparation.entities.views.VenteDetails;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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
            UPDATE VNT_BON_DETAILS v
            SET v.vnd_qte_prepare = :qte, v.vnd_prepare_flag = 1, v.vnd_prepare_motif = :motif
            WHERE v.vnd_vnt_id = :id
              AND v.vnd_vnt_type = :type
              AND v.vnd_cmp_id = :cmpId
              AND v.vnd_stk_code = :stkCode
              AND v.vnd_no = :no
            """, nativeQuery = true)
    Integer setPreparedQuantity(
            @Param("cmpId") Integer cmpId,
            @Param("id") Integer id,
            @Param("type") String type,
            @Param("stkCode") String stkCode,
            @Param("no") Integer no,
            @Param("qte") Integer qte,
            @Param("motif") String motif
    );

}
