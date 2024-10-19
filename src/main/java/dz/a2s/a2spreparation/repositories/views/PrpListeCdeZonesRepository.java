package dz.a2s.a2spreparation.repositories.views;

import dz.a2s.a2spreparation.entities.keys.VenteId;
import dz.a2s.a2spreparation.entities.keys.VenteZoneId;
import dz.a2s.a2spreparation.entities.views.PrpCdeZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrpListeCdeZonesRepository extends JpaRepository<PrpCdeZone, VenteZoneId> {

    @Query(value = """
                SELECT * 
                FROM PRP_LISTE_CDE_ZONES_GLOB 
                WHERE VNT_CMP_ID = :companyId
                  AND VBZ_ZONE = :zone 
                  AND (VBZ_STATUT_PREPARE = 0  OR VBZ_STATUT_PREPARE IS NULL)
                  OR (VBZ_STATUT_PREPARE > 0 AND VBZ_PREPAR_ID = :preparId)
            """, nativeQuery = true)
    List<PrpCdeZone> getListCmdZones(@Param("companyId") Integer companyId, @Param("zone") String zone, @Param("preparId") Integer preparId);

    @Procedure(procedureName = "logistiques.P_EDIT_START_PREPAR_ZONE", outputParameterName = "p_msg")
    Integer startPrepareZone(
            @Param("V_VBZ_CMP_ID") int v_vbz_cmp_id,
            @Param("V_VBZ_VNT_ID") int v_vbz_vnt_id,
            @Param("V_VBZ_VNT_TYPE") String v_vbz_vnt_type,
            @Param("V_VBZ_STK_CODE") String v_vbz_stk_code,
            @Param("V_VBZ_ZONE") int v_vbz_zone,
            @Param("V_VBZ_PREPAR_ID") int v_vbz_prepar_id
    );

}
