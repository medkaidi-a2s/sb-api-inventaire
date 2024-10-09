package dz.a2s.a2spreparation.repositories.views;

import dz.a2s.a2spreparation.entities.keys.StkListesId;
import dz.a2s.a2spreparation.entities.views.PrpCdePrlvUsrCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrpCdePrlvUsrCodeRepository extends JpaRepository<PrpCdePrlvUsrCode, StkListesId> {

    @Query(value = "SELECT * FROM PRP_LISTE_PLVS_AFFECTE_USRCODE WHERE PREPARATEUR_CODE = :userCode AND (:date IS NULL OR TRUNC(SLT_DATE) = TO_DATE(:date, 'yyyy-MM-dd'))", nativeQuery = true)
    List<PrpCdePrlvUsrCode> getCmdPrlvParPreparateur(@Param("userCode") String userCode, @Param("date") String date);

}
