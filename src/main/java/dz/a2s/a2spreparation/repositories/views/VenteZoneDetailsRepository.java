package dz.a2s.a2spreparation.repositories.views;

import dz.a2s.a2spreparation.entities.keys.VenteZoneDetailsId;
import dz.a2s.a2spreparation.entities.views.VenteDetails;
import dz.a2s.a2spreparation.entities.views.VenteZoneDetails;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VenteZoneDetailsRepository extends JpaRepository<VenteZoneDetails, VenteZoneDetailsId> {

    @Query(value = """
            SELECT *
            FROM PRP_ZONE_DETAILS
            WHERE VND_CMP_ID = :cmpId
              AND VND_VNT_ID = :id
              AND VND_VNT_TYPE = :type
              AND VND_STK_CODE = :code
              AND VBZ_ZONE = :zone
            """, nativeQuery = true)
    List<VenteZoneDetails> getDetailsByVenteZone(
            @Param("cmpId") Integer cmpId,
            @Param("id") Integer id,
            @Param("type") String type,
            @Param("code") String code,
            @Param("zone") Integer zone
    );

    @Transactional
    @Modifying
    @Query(value = """
                    update (
                       select v.vnd_vnt_id,
                               v.vnd_vnt_type,
                               v.vnd_cmp_id,
                               v.vnd_stk_code,
                               v.vnd_no,
                               z.vbz_zone,
                               v.vnd_prepare_flag,
                               v.vnd_qte_prepare,
                               v.vnd_prepare_motif
                        from vnt_bon_details v
                        join vnt_bon_zones z
                        on z.VBZ_CMP_ID = v.VND_CMP_ID
                        AND z.VBZ_VNT_ID = v.VND_VNT_ID
                        AND z.VBZ_VNT_TYPE = v.VND_VNT_TYPE
                        AND z.VBZ_STK_CODE = v.VND_STK_CODE
                        where z.vbz_zone = :zone
                        and v.vnd_vnt_id = :id
                        and v.vnd_cmp_id = :cmpId
                        and v.vnd_vnt_type = :type
                        and v.vnd_stk_code = :stkCode
                        and v.vnd_no = :no
                )
                set vnd_qte_prepare = :qte, vnd_prepare_flag = 1, vnd_prepare_motif = :motif
            """, nativeQuery = true)
    Integer setPreparedQuantityZone(
            @Param("cmpId") Integer cmpId,
            @Param("id") Integer id,
            @Param("type") String type,
            @Param("stkCode") String stkCode,
            @Param("zone") Integer zone,
            @Param("no") Integer no,
            @Param("qte") Integer qte,
            @Param("motif") String motif
    );

    @Procedure(procedureName = "logistiques.P_SET_ZONE_PREPARED", outputParameterName = "p_msg")
    Integer setCommandeZonePrepared(
            @Param("P_CMP") Integer cmpId,
            @Param("P_VNT") Integer id,
            @Param("P_TYPE") String type,
            @Param("P_STK") String stkCode,
            @Param("P_ZONE") Integer zone
    );

}
