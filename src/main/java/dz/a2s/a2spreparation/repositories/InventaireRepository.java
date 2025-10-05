package dz.a2s.a2spreparation.repositories;

import dz.a2s.a2spreparation.dto.common.ListProjection;
import dz.a2s.a2spreparation.dto.inventaire.projections.ComptageAccessProjection;
import dz.a2s.a2spreparation.dto.inventaire.projections.EcartLineProjection;
import dz.a2s.a2spreparation.dto.inventaire.projections.InventaireLineProjection;
import dz.a2s.a2spreparation.dto.inventaire.projections.InventaireProjection;
import dz.a2s.a2spreparation.entities.Inventaire;
import dz.a2s.a2spreparation.entities.keys.InventaireId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventaireRepository extends JpaRepository<Inventaire, InventaireId> {

    @Query(value = """
            SELECT INV_CMP_ID     AS CMP_ID,
                   INV_ID         AS ID,
                   INV_DATE       AS INV_DATE,
                   T.INV_REMARQUE AS REMARQUE
              FROM STP_INVENTAIRES T
             WHERE T.INV_CMP_ID = :cmp_id
               AND NVL(T.INV_VALIDE_FLAG, 0) = 1
               AND NVL(T.INV_CLOTURE_FLAG, 0) = 0
            """, nativeQuery = true)
    List<InventaireProjection> getListInventaires(@Param("cmp_id") Integer cmpId);

    @Query(value = """
            SELECT LVD_ID AS CODE, LVD_NOM1 AS NOM
              FROM GMS_LVD T
             WHERE LVD_CMP_ID = :cmp_id
               AND LVD_LVM_ID = 63
               AND LVD_ACTIVE_FLAG = 1
            """, nativeQuery = true)
    List<ListProjection> getListComptage(@Param("cmp_id") Integer cmpId);

    @Query(value = """
            SELECT INU_CMP_ID,
                   INU_INV_ID,
                   INU_USR_CODE,
                   NVL(INU_COMPTAGE1, 0) COMPTAGE1,
                   NVL(INU_COMPTAGE2, 0) COMPTAGE2,
                   NVL(INU_COMPTAGE3, 0) COMPTAGE3
              FROM STP_INVENTAIRE_USERS T
             WHERE T.INU_CMP_ID = :cmp_id
               AND T.INU_INV_ID = :inv_id
               AND LOWER(T.INU_USR_CODE) = LOWER(:username)
            """, nativeQuery = true)
    ComptageAccessProjection getComptageAccess(@Param("cmp_id") Integer cmpId, @Param("inv_id") Integer invId, @Param("username") String username);

    @Query(value = """
            SELECT SIE_REFERENCE
              FROM STP_INVENTAIRE_EMPLACEMENTS T
             WHERE SIE_ACTIVE_FLAG = 1
               AND SIE_CMP_ID = :cmp_id
               AND LOWER(T.SIE_REFERENCE) = LOWER(:emplacement)
            """, nativeQuery = true)
    String checkEmplacement(@Param("cmp_id") Integer cmpId, @Param("emplacement") String emplacement);

    @Query(value = """
            SELECT *
              FROM (SELECT COUNT(*) OVER() AS TOTAL_RECORDS,
                           IND_CMP_ID SITE,
                           IND_INV_ID INVENTAIRE,
                           IND_STK_CODE DEPOT,
                           IND_MED_ID NUM_PRODUIT,
                           IND_ID NLOT_INTERNE,
                           IND_LIGNE NUM_LIGNE,
                           MED_AMM CODE,
                           M.MED_COMMERCIAL_NAME NOM_PRODUIT,
                           T.IND_NLOT NLOT,
                           T.IND_DATE_PEREMPTION DATE_PEREMPTION,
                           T.IND_PRIX_PPA PPA,
                           IND_PRIX_SHP SHP,
                           (SELECT GFM_NOM
                              FROM STP_GALENICFORMS T
                             WHERE GFM_CMP_ID = MED_CMP_ID
                               AND GFM_CODE = MED_GFM_CODE) FORME,
                           (SELECT TER_NOM
                              FROM STP_TIERS
                             WHERE TER_CMP_ID = IND_CMP_ID
                               AND TER_ID = IND_TER_ID
                               AND TER_TYPE = IND_TER_TYPE) LABO,
                           T.IND_ATTRIBUT5 AS QTE_STOCK,
                           (SELECT T.ZNS_NOM FROM STP_ZONES T WHERE T.ZNS_ID = MED_ZNS_ID) ZONE_PRODUIT,
                           IND_ATTRIBUT3 MOTIF_SAISIE,
                           (SELECT SUM(D.INS_QTE)
                              FROM STP_INVENTAIRE_SAISIES D
                             WHERE T.IND_CMP_ID = D.INS_CMP_ID
                               AND T.IND_INV_ID = INS_INV_ID
                               AND T.IND_STK_CODE = INS_STK_CODE
                               AND T.IND_MED_ID = INS_MED_ID
                               AND T.IND_ID = D.INS_PRD_ID
                               AND T.IND_LIGNE = D.INS_IND_LIGNE
                               AND D.INS_TYPE = :comptage
                               AND D.INS_CREER_USER = :emplacement) QTE_SAISIE,
                           ROW_NUMBER() OVER(ORDER BY M.MED_COMMERCIAL_NAME) AS RNUM
                      FROM STP_INVENTAIRE_DETAILS T, STP_MEDICAMENT M
                     WHERE IND_CMP_ID = M.MED_CMP_ID
                       AND IND_MED_ID = M.MED_ID
                       AND IND_INV_ID = :inv_id
                       AND T.IND_CMP_ID = :cmp_id
                       AND NVL(IND_ATTRIBUT5, 0) > :stock_zero
                       AND LOWER(M.MED_COMMERCIAL_NAME || ' ' || T.IND_NLOT) LIKE
                           LOWER('%' || :search || '%'))
             WHERE RNUM BETWEEN :start AND :end
            """, nativeQuery = true)
    List<InventaireLineProjection> getInventaireLines(
            @Param("cmp_id") Integer cmpId,
            @Param("inv_id") Integer invId,
            @Param("comptage") Integer comptage,
            @Param("emplacement") String emplacement,
            @Param("stock_zero") Integer stockZero,
            @Param("search") String search,
            @Param("start") Integer start,
            @Param("end") Integer end);

    @Query(value = """
            SELECT *
              FROM (SELECT COUNT(*) OVER() AS TOTAL_RECORDS,
                           T.INV_CMP_ID AS SITE,
                           T.INV_ID AS INV_ID,
                           T.IND_STK_CODE AS DEPOT,
                           T.IND_MED_ID AS MED_ID,
                           T.IND_ID AS NLOT_INTERNE,
                           T.IND_LIGNE AS LIGNE,
                           T.INV_DATE AS DATE_INVENTAIRE,
                           T.ZONE AS MED_ZONE,
                           T.MED_ZNS_ID AS ZONE,
                           T.PRD_STK_CODE AS STK_CODE,
                           T.MED_COMMERCIAL_NAME AS COMMERCIAL_NAME,
                           T.CODE AS CODE,
                           T.FORME AS FORME,
                           T.PRD_NLOT AS NLOT,
                           T.PRD_DATE_PEREMPTION AS DATE_PEREMPTION,
                           T.PRD_PRIX_PPA AS PPA,
                           T.PRD_PRIX_SHP AS SHP,
                           T.PRD_COLIS AS COLIS,
                           T.STOCK AS STOCK,
                           T.FOURNISSEUR AS FOURNISSEUR,
                           T.ZONE_PRODUIT AS LIBELLE_ZONE,
                           T.CMP3 AS COMPTAGE3,
                           T.CMP1 AS COMPTAGE1,
                           T.CMP2 AS COMPTAGE2,
                           ROW_NUMBER() OVER(ORDER BY T.MED_COMMERCIAL_NAME) AS RNUM
                      FROM PRP_INVENTAIRES T
                     WHERE T.INV_CMP_ID = :cmp_id
                       AND T.INV_ID = :inv_id
                       AND (:search IS NULL OR
                           LOWER(T.MED_COMMERCIAL_NAME || ' ' || T.PRD_NLOT) LIKE
                           LOWER('%' || :search || '%'))
                       AND (:is_ecart = 0 OR T.CMP1 <> T.CMP2))
             WHERE RNUM BETWEEN :start AND :end
            """, nativeQuery = true)
    List<EcartLineProjection> getEcartLines(
            @Param("cmp_id") Integer cmpId,
            @Param("inv_id") Integer invId,
            @Param("search") String search,
            @Param("is_ecart") Integer isEcart,
            @Param("start") Integer start,
            @Param("end") Integer end
    );

    @Procedure(procedureName = "stocks_new.saisie_inv")
    void saisirInventaire(
            @Param("P_CMP") Integer cmpId,
            @Param("P_INV") Integer invId,
            @Param("P_PRD_ID") Integer nlotInterne,
            @Param("P_MED_ID") Integer medId,
            @Param("P_DEPOT") String depot,
            @Param("P_TYPE") Integer comptage,
            @Param("P_QTE") Integer quantite,
            @Param("P_MOTIF") String motif,
            @Param("XUSER") String emplacement,
            @Param("P_LIGNE") Integer noLigne,
            @Param("P_USR") String username
    );

}
