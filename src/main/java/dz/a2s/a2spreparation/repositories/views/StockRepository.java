package dz.a2s.a2spreparation.repositories.views;

import dz.a2s.a2spreparation.dto.stock.projections.StockMasterProjection;
import dz.a2s.a2spreparation.entities.keys.StockId;
import dz.a2s.a2spreparation.entities.views.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, StockId> {

    @Query(value = """
            SELECT *
              FROM (SELECT COUNT(*) OVER() AS TOTAL_COUNT,
                           V.PRD_CMP_ID AS CMP_ID,
                           V.PRD_ID AS ID,
                           V.PRD_MED_ID AS MED_ID,
                           V.PRD_STK_CODE AS STK_CODE,
                           V.MED_COMMERCIAL_NAME AS DESIGNATION,
                           V.PRD_ATTRIBUT2 AS FOURNISSEUR,
                           V.PRD_NLOT AS LOT,
                           V.PRD_DATE_PEREMPTION AS DATE_PEREMPTION,
                           V.PRD_PRIX_PPA AS PPA,
                           V.PRD_QTE AS QUANTITE,
                           V.PRD_PRIX_PH AS PRIX_VENTE,
                           V.PRD_PRIX_GR AS PRIX_GROS,
                           V.PRD_PRIX_SHP AS SHP,
                           V.PRD_UG_VNETE AS UG_VENTE,
                           V.PRD_ETAT_FLAG AS ETAT,
                           V.PRD_CREER_DATE AS DATE_ARRIVAGE,
                           V.PRD_COLIS AS COLISAGE,
                           ROW_NUMBER() OVER(ORDER BY MED_COMMERCIAL_NAME) AS ROW_NUM
                      FROM PRP_STOCKS V
                     WHERE LOWER(v.MED_COMMERCIAL_NAME || v.PRD_ATTRIBUT2 || v.PRD_NLOT) LIKE
                           LOWER('%' || :search || '%'))
             WHERE ROW_NUM BETWEEN :start AND :end
            """, nativeQuery = true)
    List<StockMasterProjection> getAllStocks(@Param("start") Integer start, @Param("end") Integer end, @Param("search") String search);

}
