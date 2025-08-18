package dz.a2s.a2spreparation.repositories.views;

import dz.a2s.a2spreparation.entities.keys.VentePrlvDetailsId;
import dz.a2s.a2spreparation.entities.views.VentePrlvDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VentePrlvDetailsRepository extends JpaRepository<VentePrlvDetails, VentePrlvDetailsId> {

    @Query(value = """
            SELECT * FROM PRP_PRELEV_DETAILS
            WHERE SLD_CMP_ID = :cmpId
              AND SLD_SLT_ID = :id
              AND SLD_SLT_TYPE = :type
              AND SLD_SLT_ANNEE = :annee
            """, nativeQuery = true)
    List<VentePrlvDetails> getDetailsByVente(
            @Param("cmpId") Integer cmpId,
            @Param("id") Integer id,
            @Param("type") String type,
            @Param("annee") Integer annee
    );

}
