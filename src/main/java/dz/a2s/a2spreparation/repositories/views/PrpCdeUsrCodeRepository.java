package dz.a2s.a2spreparation.repositories.views;

import dz.a2s.a2spreparation.entities.keys.VenteId;
import dz.a2s.a2spreparation.entities.views.PrpCdeUsrCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrpCdeUsrCodeRepository extends JpaRepository<PrpCdeUsrCode, VenteId> {

    @Query(value = "SELECT * FROM PRP_LISTE_CDE_AFFECTES2 WHERE PREPARATEUR_CODE_USER = :userCode AND (:date IS NULL OR TRUNC(VNT_DATE) = TO_DATE(:date, 'yyyy-MM-dd'))", nativeQuery = true)
    List<PrpCdeUsrCode> getCmdParPreparateur(@Param("userCode") String userCode, @Param("date") String date);

}
