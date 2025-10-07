package dz.a2s.a2spreparation.repositories;

import dz.a2s.a2spreparation.entities.Colis;
import dz.a2s.a2spreparation.entities.keys.ColisId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ColisRepository extends JpaRepository<Colis, ColisId> {

    @Query(value = """
            SELECT t.VCO_CMP_ID,
                   t.VCO_VNT_ID,
                   t.VCO_VNT_TYPE,
                   t.VCO_STK_CODE,
                   t.VCO_CODE,
                   s.VNT_REFERENCE,
                   r.TER_NOM,
                   r.TER_ADRESSE,
                   r.TER_REGION_LIB
              FROM VNT_COLIS t
              JOIN VNT_BONS s
                ON t.VCO_CMP_ID = s.VNT_CMP_ID
               AND t.VCO_VNT_ID = s.VNT_ID
               AND t.VCO_VNT_TYPE = s.VNT_TYPE
               AND t.VCO_STK_CODE = s.VNT_STK_CODE
              JOIN STP_TIERS r
                ON s.VNT_CMP_ID = r.TER_CMP_ID
               AND s.VNT_TER_ID = r.TER_ID
               AND s.VNT_TER_TYPE = r.TER_TYPE
             WHERE T.VCO_CMP_ID = :cmp_id
               AND T.VCO_VNT_ID = :id
               AND T.VCO_VNT_TYPE = :type
               AND T.VCO_STK_CODE = :stk_code
               AND (:zone IS NULL OR T.VBZ_ZONE = :zone)
            """, nativeQuery = true)
    List<Colis> getEtiquettesColis(@Param("cmp_id") Integer cmpId, @Param("id") Integer id, @Param("type") String type, @Param("stk_code") String stkCode, @Param("zone") Integer zone);

}
