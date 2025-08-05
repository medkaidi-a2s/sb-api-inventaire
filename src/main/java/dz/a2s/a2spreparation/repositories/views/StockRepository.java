package dz.a2s.a2spreparation.repositories.views;

import dz.a2s.a2spreparation.dto.stock.projections.ProductLotProjection;
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
                     WHERE V.PRD_CMP_ID = :cmpId
                       AND LOWER(v.MED_COMMERCIAL_NAME || v.PRD_ATTRIBUT2 || v.PRD_NLOT) LIKE
                           LOWER('%' || :search || '%'))
             WHERE ROW_NUM BETWEEN :start AND :end
            """, nativeQuery = true)
    List<StockMasterProjection> getAllStocks(@Param("start") Integer start, @Param("end") Integer end, @Param("cmpId") Integer cmpId, @Param("search") String search);

    @Query(value = """
            SELECT PRD_CMP_ID          AS CMP_ID, --Site
                   PRD_ID              AS ID, --n°lot interne
                   PRD_MED_ID          AS MED_ID, --n°Produit interne
                   PRD_STK_CODE        AS STK_CODE, --n°Dépôt
                   PRD_NLOT            AS NLOT, --N°LOT
                   PRD_DATE_PEREMPTION AS DATE_PEREMPTION, --DDP
                   PRD_QTE             AS QTE, --Quantité
                   PRD_PRIX_PH         AS PRIX_PH, --Prix de vente
                   PRD_PRIX_PPA        AS PRIX_PPA, --PPA
                   PRD_PRIX_SHP        AS PRIX_SHP, --SHP
                   PRD_UG_VNETE        AS UG_VENTE --Tx_Vente
              FROM PRP_STOCKS t
             where PRD_cmp_id = :cmpId
               and PRD_MED_ID = :medId
               and PRD_STK_CODE = 1
               and PRD_ID <> :oldLotId
               and PRD_QTE >= :qte
               and nvl(PRD_ETAT_FLAG, 0) = 0
            """, nativeQuery = true)
    List<ProductLotProjection> getAvailableLots(@Param("cmpId") Integer cmpId, @Param("medId") Integer medId, @Param("oldLotId") Integer oldLotId, @Param("qte") Integer qte);

}
