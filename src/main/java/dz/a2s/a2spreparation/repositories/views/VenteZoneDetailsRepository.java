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
              AND (:zone IS NULL OR VBZ_ZONE = :zone)
            """, nativeQuery = true)
    List<VenteZoneDetails> getDetailsByVenteZone(@Param("cmpId") Integer cmpId, @Param("id") Integer id, @Param("type") String type, @Param("code") String code, @Param("zone") Integer zone);

    @Transactional
    @Modifying
    @Query(value = """
            UPDATE VNT_BON_DETAILS V
               SET VND_QTE_PREPARE   = :qte,
                   VND_PREPARE_FLAG  = 1,
                   VND_PREPARE_MOTIF = :motif
             WHERE NVL(V.VND_PREPARE_FLAG, 0) = 0
               AND V.VND_VNT_ID = :id
               AND V.VND_CMP_ID = :cmpId
               AND V.VND_VNT_TYPE = :type
               AND V.VND_STK_CODE = :stkCode
               AND V.VND_NO = :no
            """, nativeQuery = true)
    Integer setPreparedQuantityZone(@Param("cmpId") Integer cmpId, @Param("id") Integer id, @Param("type") String type, @Param("stkCode") String stkCode, @Param("no") Integer no, @Param("qte") Integer qte, @Param("motif") Integer motif);

//    @Procedure(procedureName = "logistiques.P_VALIDE_CDE_PREPARE_ZONE", outputParameterName = "p_msg")
//    Integer setCommandeZonePrepared(@Param("P_CMP") Integer cmpId, @Param("P_VNT") Integer id, @Param("P_TYPE") String type, @Param("P_STK") String stkCode, @Param("P_ZONE") Integer zone, @Param("P_USER") String user);

    @Transactional
    @Modifying
    @Query(value = """
            UPDATE VNT_BON_DETAILS V
               SET VND_CONTROLE_QTE1   = :qte,
                   VND_CONTROLE_FLAG1  = 1,
                   VND_CONTROLE_MOTIF1 = :motif
             WHERE NVL(V.VND_CONTROLE_FLAG1, 0) <> 1
               AND V.VND_VNT_ID = :id
               AND V.VND_CMP_ID = :cmpId
               AND V.VND_VNT_TYPE = :type
               AND V.VND_STK_CODE = :stkCode
               AND V.VND_NO = :no
            """, nativeQuery = true)
    Integer setControlledQuantityZone(@Param("cmpId") Integer cmpId, @Param("id") Integer id, @Param("type") String type, @Param("stkCode") String stkCode, @Param("no") Integer no, @Param("qte") Integer qte, @Param("motif") Integer motif);

}
