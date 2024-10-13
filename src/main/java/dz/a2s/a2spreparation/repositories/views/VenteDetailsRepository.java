package dz.a2s.a2spreparation.repositories.views;

import dz.a2s.a2spreparation.entities.keys.VenteDetailsId;
import dz.a2s.a2spreparation.entities.views.VenteDetails;
import org.springframework.data.jpa.repository.JpaRepository;
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

}
