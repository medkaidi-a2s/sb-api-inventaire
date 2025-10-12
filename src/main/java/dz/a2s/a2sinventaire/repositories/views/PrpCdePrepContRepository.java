package dz.a2s.a2sinventaire.repositories.views;

import dz.a2s.a2sinventaire.entities.keys.VenteId;
import dz.a2s.a2sinventaire.entities.views.PrpCdePrepCont;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PrpCdePrepContRepository extends JpaRepository<PrpCdePrepCont, VenteId> {

    @Query(value = "SELECT * FROM PRP_CDE_PREP_CONT WHERE VNT_CMP_ID = :vntCmpId AND VNT_ID = :vntId AND VNT_TYPE = :vntType AND VNT_STK_CODE = :vntStkCode", nativeQuery = true)
    PrpCdePrepCont getPrepCont(
            @Param("vntCmpId") Integer vntCmpId,
            @Param("vntId") Integer vntId,
            @Param("vntType") String vntType,
            @Param("vntStkCode") String vntStkCode
    );

}
