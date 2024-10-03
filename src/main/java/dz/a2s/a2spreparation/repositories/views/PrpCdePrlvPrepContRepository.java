package dz.a2s.a2spreparation.repositories.views;

import dz.a2s.a2spreparation.entities.keys.StkListesId;
import dz.a2s.a2spreparation.entities.views.PrpCdePrlvPrepCont;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PrpCdePrlvPrepContRepository extends JpaRepository<PrpCdePrlvPrepCont, StkListesId> {

    @Query(value = "SELECT * FROM PRP_CDE_PRLV_PREP_CONT WHERE SLT_CMP_ID = :cmpId AND SLT_ID = :id AND SLT_TYPE = :type AND SLT_ANNEE = :annee", nativeQuery = true)
    PrpCdePrlvPrepCont getPrepCont (
            @Param("cmpId") Integer cmpId,
            @Param("id") Integer id,
            @Param("type") String type,
            @Param("annee") Integer annee
    );

}
