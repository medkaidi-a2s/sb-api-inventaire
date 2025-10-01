package dz.a2s.a2spreparation.repositories.views;

import dz.a2s.a2spreparation.dto.commande.projections.ColisageProjection;
import dz.a2s.a2spreparation.dto.commande.projections.CommandeColisageProjection;
import dz.a2s.a2spreparation.dto.preparation.CommandeReceiptProjection;
import dz.a2s.a2spreparation.entities.keys.VenteId;
import dz.a2s.a2spreparation.entities.views.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, VenteId> {

    @Query(value = """
                SELECT * 
                FROM PRP_LISTE_CDES 
                WHERE VNT_CMP_ID = :companyId 
                  AND (:date IS NULL OR TRUNC(VNT_DATE) = TO_DATE(:date, 'yyyy-MM-dd')) 
                  AND REFERENCE IS NOT NULL
            """, nativeQuery = true)
    List<Commande> getListCommande(@Param("companyId") Integer companyId, @Param("date") String date);

    @Query(value = """
            SELECT *
              FROM PRP_LISTE_CDES T
             WHERE VNT_CMP_ID = :cmp_id
               AND (:date IS NULL OR TRUNC(VNT_DATE) = TO_DATE(:date, 'yyyy-MM-dd'))
               AND REFERENCE IS NOT NULL
               AND (:search IS NULL OR
                   LOWER(T.REFERENCE || ' ' || T.CLIENT || ' ' || T.PORTEFEUILLE || ' ' || T.REGION) LIKE
                   LOWER('%' || :search || '%'))
            """, nativeQuery = true)
    List<Commande> getAllCommandes(@Param("cmp_id") Integer companyId, @Param("date") String date, @Param("search") String search);

    @Procedure(procedureName = "logistiques.p_edit_affcte_cde_prepare", outputParameterName = "p_msg")
    Integer editAffectCommandePrp(
            @Param("p_cmp") int cmd,
            @Param("p_vnt") int vnt,
            @Param("p_stk") String stk,
            @Param("p_type") String type,
            @Param("p_prp") int prp,
            @Param("p_cnt1") int cnt1,
            @Param("p_cnt2") int cnt2,
            @Param("p_user") String user
    );

    @Query(value = """
                SELECT * 
                FROM PRP_LISTE_CDE_AFFECTES 
                WHERE VNT_CMP_ID = :companyId 
                  AND (:date IS NULL OR TRUNC(VNT_DATE) = TO_DATE(:date, 'yyyy-MM-dd'))
            """, nativeQuery = true)
    List<Commande> getListCommandeAssigned(@Param("companyId") Integer companyId, @Param("date") String date);

    @Procedure(procedureName = "logistiques.p_affcte_cde_prepare", outputParameterName = "p_msg")
    Integer affectCommandePrp(
            @Param("p_cmp") int cmp,
            @Param("p_vnt") int vnt,
            @Param("p_stk") String stk,
            @Param("p_type") String type,
            @Param("p_prp") int prp,
            @Param("p_cnt1") int cnt1,
            @Param("p_cnt2") int cnt2,
            @Param("p_user") String user
    );

    @Query(value = """
                SELECT * 
                FROM PRP_LISTE_CDE_AFFECTES 
                WHERE PREPARATEUR_ID = :preparateurId 
                AND VNT_CMP_ID = :companyId
                AND (:date IS NULL OR TRUNC(VNT_DATE) = TO_DATE(:date, 'yyyy-MM-dd'))
                AND VNT_STATUT_PREPARE IN (1, 2, 3)
            """, nativeQuery = true)
    List<Commande> getCommandesParPreparateur(@Param("preparateurId") Integer preparateurId, @Param("companyId") Integer companyId,@Param("date") String date);

    @Procedure(procedureName = "logistiques.P_VALIDE_CDE_PREPARE", outputParameterName = "p_msg")
    Integer setCommandePrepared(
            @Param("p_cmp") int cmp,
            @Param("p_vnt") int vnt,
            @Param("p_stk") String stk,
            @Param("p_type") String type,
            @Param("p_user") String user
    );

    @Procedure(procedureName = "logistiques.P_EDIT_START_PREPAR_CDE", outputParameterName = "p_msg")
    Integer startPrepareCde(
            @Param("p_vnt_cmp_id") int cmpId,
            @Param("p_vnt_id") int vntId,
            @Param("p_vnt_type") String type,
            @Param("p_vnt_stk_code") String stkCode
    );

    @Query(value = """
                SELECT *
                FROM PRP_LISTE_CDE_CONTROLES
                WHERE VNT_CMP_ID = :companyId
                  AND (:date IS NULL OR TRUNC(VNT_DATE) = TO_DATE(:date, 'yyyy-MM-dd'))
                  AND VERIFICATEUR_ID = :utilisateurId
            """, nativeQuery = true)
    List<Commande> getPreparedCommandes(@Param("companyId") Integer companyId, @Param("utilisateurId") Integer utilisateurId, @Param("date") String date);

    @Procedure(procedureName = "logistiques.P_EDIT_START_CONTROL_CDE", outputParameterName = "p_msg")
    Integer startControleCde(
            @Param("p_vnt_cmp_id") int cmpId,
            @Param("p_vnt_id") int vntId,
            @Param("p_vnt_type") String type,
            @Param("p_vnt_stk_code") String stkCode
    );

    @Procedure(procedureName = "logistiques.P_SET_CDE_CONTROLLED", outputParameterName = "p_msg")
    Integer setCommandeControlled(
            @Param("p_cmp") int cmp,
            @Param("p_vnt") int vnt,
            @Param("p_stk") String stk,
            @Param("p_type") String type,
            @Param("p_user") String user,
            @Param("P_COLIS_V") Integer colisV,
            @Param("P_COLIS_D") Integer colisD,
            @Param("P_FRIGO") Integer frigo,
            @Param("P_PSYCHO") Integer psycho,
            @Param("P_CHERS") Integer chers,
            @Param("P_SACHET") Integer sachet,
            @Param("P_BACS") Integer bacs
    );

    @Query(value = """
            SELECT v.vnt_reference,
                   v.vnt_date,
                   r.ter_nom,
                   r.ter_adresse,
                   r.ter_region_lib,
                   v.VNT_REF_ASSOCIE2 AS xtable
              FROM vnt_bons v
              JOIN stp_tiers r
                ON v.vnt_cmp_id = r.ter_cmp_id
               AND v.vnt_ter_id = r.ter_id
               AND v.vnt_ter_type = r.ter_type
             WHERE v.vnt_cmp_id = :cmpId
               AND v.vnt_id = :vntId
               AND v.vnt_type = :type
               AND v.vnt_stk_code = :stkCode
            """, nativeQuery = true)
    CommandeReceiptProjection getReceiptData(@Param("cmpId") Integer cmpId, @Param("vntId") Integer vntId, @Param("type") String type, @Param("stkCode") String stkCode);

    @Query(value = """
                SELECT *
                FROM PRP_LISTE_CDE_CONTROLES
                WHERE VNT_CMP_ID = :companyId
                  AND (:date IS NULL OR TRUNC(VNT_DATE) = TO_DATE(:date, 'yyyy-MM-dd'))
                  AND STATUT = 'En attente de facturation'
            """, nativeQuery = true)
    List<Commande> getControlledCommandes(@Param("companyId") Integer companyId, @Param("date") String date);

    @Procedure(procedureName = "logistiques.P_SAISI_COLISAGE", outputParameterName = "p_msg")
    Integer saisirColisageCommande(
            @Param("P_CMP_ID") int cmp,
            @Param("P_VNT_ID") int vnt,
            @Param("P_TYPE") String type,
            @Param("P_STK_CODE") String stkCode,
            @Param("P_ZONE") Integer zone,
            @Param("P_ANNEE") Integer annee,
            @Param("P_COLIS_V") Integer colisV,
            @Param("P_COLIS_D") Integer colisD,
            @Param("P_FRIGO") Integer frigo,
            @Param("P_PSYCHO") Integer psycho,
            @Param("P_CHERS") Integer chers,
            @Param("P_SACHET") Integer sachet,
            @Param("P_BACS") Integer bacs,
            @Param("P_METHOD") Integer method
    );

    @Query(value = """
            SELECT NVL(VBZ_COLIS_D, 0) AS COLIS_D,
                   NVL(VBZ_COLIS_F, 0) AS COLIS_F,
                   NVL(VBZ_COLIS_V, 0) AS COLIS_V,
                   NVL(VBZ_SACHET, 0)  AS SACHET,
                   NVL(VBZ_PSYCHO, 0)  AS PSYCHO,
                   NVL(VBZ_CHERS, 0)   AS CHERS
              FROM VNT_BON_ZONES Z
             WHERE VBZ_CMP_ID = :cmpId
               AND VBZ_VNT_ID = :id
               AND VBZ_VNT_TYPE = :type
               AND VBZ_STK_CODE = :stkCode
               AND VBZ_ZONE = :zone
            """, nativeQuery = true)
    ColisageProjection getColisageZone(@Param("cmpId") Integer cmpId, @Param("id") Integer id, @Param("type") String type, @Param("stkCode") String stkCode, @Param("zone") Integer zone);

    @Query(value = """
             SELECT NVL(T.VNT_COLIS, 0) AS COLIS_D,
                    NVL(T.VNT_NBR_FRIGO, 0) AS COLIS_F,
                    NVL(T.VNT_NBR_PSYCHO, 0) AS PSYCHO,
                    NVL(T.VNT_COLIS_VRAG, 0) AS COLIS_V,
                    NVL(T.VNT_NBR_CHERS, 0) AS CHERS,
                    NVL(T.VNT_NBR_SACHETS, 0) AS SACHET,
                    NVL(T.VNT_BACS, 0) AS BACS,
                    NVL(T.VNT_PALETTES, 0) AS PALETTES
               FROM VNT_BONS T
              WHERE VNT_CMP_ID = :cmpId
                AND VNT_ID = :id
                AND VNT_TYPE = :type
                AND VNT_STK_CODE = :stkCode
            """, nativeQuery = true)
    ColisageProjection getColisageCommande(@Param("cmpId") Integer cmpId, @Param("id") Integer id, @Param("type") String type, @Param("stkCode") String stkCode);

    @Query(value = """
            WITH VNT_COLIS_COUNT AS
             (
              -- PRÉ-CALCUL DES COMPTAGES POUR ÉVITER LA SOUS-REQUÊTE CORRÉLÉE
              SELECT VCO_CMP_ID,
                      VCO_VNT_ID,
                      VCO_STK_CODE,
                      VCO_VNT_TYPE,
                      COUNT(*) AS NBR_ETIQUETE
                FROM VNT_COLIS
               WHERE VCO_CMP_ID = :cmp_id
               GROUP BY VCO_CMP_ID, VCO_VNT_ID, VCO_STK_CODE, VCO_VNT_TYPE)
            
            SELECT *
              FROM (SELECT /*+ LEADING(T) USE_NL(R) INDEX(T IDX_VNT_BONS_COMPOSITE) */
                     COUNT(*) OVER() AS TOTAL_RECORDS,
                     T.VNT_CMP_ID,
                     T.VNT_ID,
                     T.VNT_TYPE,
                     T.VNT_DATE,
                     T.VNT_STK_CODE,
                     T.VNT_REFERENCE,
                     T.VNT_ATT3 ROTATION,
                     T.VNT_TOTAL_TTC,
                     R.TER_NOM AS LIBELLE_TIER,
                     R.TER_REGION_LIB AS REGION,
                     T.VNT_TOTAL_COLIS,
                     T.VNT_BACS,
                     NVL(VC.NBR_ETIQUETE, 0) AS NBR_ETIQUETE,
                     NVL(T.VNT_PREP_FLAG, 0) AS VNT_PREP_FLAG,
                     ROW_NUMBER() OVER(ORDER BY VNT_ID) AS RNUM
                      FROM VENTES_CA T
                     INNER JOIN STP_TIERS R
                        ON R.TER_CMP_ID = T.VNT_CMP_ID
                       AND R.TER_ID = T.VNT_TER_ID
                       AND R.TER_TYPE = T.VNT_TER_TYPE
                      LEFT JOIN VNT_COLIS_COUNT VC
                        ON VC.VCO_CMP_ID = T.VNT_CMP_ID
                       AND VC.VCO_VNT_ID = T.VNT_ID
                       AND VC.VCO_STK_CODE = T.VNT_STK_CODE
                       AND VC.VCO_VNT_TYPE = T.VNT_TYPE
                     WHERE T.VNT_CMP_ID = :cmp_id
                       AND T.VNT_TYPE = 1
                       AND NVL(T.VNT_ETAT_FLAG, 0) = 1
                       AND NVL(T.VNT_AVOIR_FLAG, 0) = 0
                       AND NVL(T.VNT_ANNULE_FLAG, 0) = 0
                       AND T.VNT_DATE >= TO_DATE(:date_debut, 'DD/MM/RRRR')
                       AND T.VNT_DATE < TO_DATE(:date_fin, 'DD/MM/RRRR') + 1
                       AND (:rotation IS NULL OR T.VNT_ATT3 = :rotation)
                       AND NVL(T.VNT_PREP_FLAG, 0) IN (0, NVL(:statut_prepare, 0))
                       AND (:search IS NULL OR
                           LOWER(VNT_REFERENCE || ' ' || R.TER_NOM || ' ' ||
                                  TER_REGION_LIB) LIKE LOWER('%' || :search || '%')))
             WHERE RNUM BETWEEN :start AND :end
            """, nativeQuery = true)
    List<CommandeColisageProjection> getListeCommandesColisage(
            @Param("cmp_id") Integer cmpId,
            @Param("date_debut") String dateDebut,
            @Param("date_fin") String dateFin,
            @Param("statut_prepare") Integer statutPrepare,
            @Param("start") Integer start,
            @Param("end") Integer end,
            @Param("rotation") String rotation,
            @Param("search") String search);

    @Query(value = """
            WITH vnt_colis_count AS
             (
              -- Pré-calcul des comptages pour éviter la sous-requête corrélée
              SELECT vco_cmp_id,
                      vco_vnt_id,
                      vco_stk_code,
                      vco_vnt_type,
                      COUNT(*) as nbr_etiquete
                FROM vnt_colis
               WHERE vco_cmp_id = :cmp_id
               GROUP BY vco_cmp_id, vco_vnt_id, vco_stk_code, vco_vnt_type)
            SELECT /*+ LEADING(T) USE_NL(R) INDEX(T IDX_VNT_BONS_COMPOSITE) */
             T.vnt_cmp_id,
             T.VNT_ID,
             T.VNT_TYPE,
             T.VNT_STK_CODE,
             T.VNT_TOTAL_COLIS,
             T.VNT_BACS,
             NVL(VC.nbr_etiquete, 0) AS nbr_etiquete
              FROM ventes_ca T
              LEFT JOIN vnt_colis_count VC
                ON VC.vco_cmp_id = T.VNT_cmp_id
               AND VC.vco_vnt_id = T.vnt_id
               AND VC.vco_stk_code = T.VNT_stk_code
               AND VC.vco_vnt_type = T.vnt_type
             WHERE T.VNT_CMP_ID = :cmp_id
               AND T.VNT_ID = :id
               AND T.VNT_TYPE = :type
               AND T.VNT_STK_CODE = :stk_code
               AND T.VNT_TYPE = 1
               AND NVL(T.VNT_ETAT_FLAG, 0) = 1
               AND NVL(T.VNT_AVOIR_FLAG, 0) = 0
               AND NVL(T.vnt_annule_flag, 0) = 0
            """, nativeQuery = true)
    CommandeColisageProjection getCommandeColisageGlobal(
            @Param("cmp_id") Integer cmpId,
            @Param("id") Integer id,
            @Param("type") String type,
            @Param("stk_code") String stkCode);

    @Transactional
    @Modifying
    @Query(value = """
            UPDATE VNT_BONS B
               SET VNT_COLIS       = :xcolis,
                   VNT_COLIS_VRAG  = :xcolis_v,
                   VNT_NBR_FRIGO   = :xfrigo,
                   VNT_NBR_PSYCHO  = :xpsycho,
                   VNT_NBR_CHERS   = :xchers,
                   VNT_NBR_SACHETS = :xsachet,
                   VNT_BACS        = :xbac,
                   VNT_PALETTES    = :xpalette,
                   VNT_PREP_FLAG   = 1
             WHERE B.VNT_CMP_ID = :cmp_id
               AND B.VNT_ID = :id
               AND B.VNT_TYPE = :type
               AND B.VNT_STK_CODE = :stk_code
            """, nativeQuery = true)
    int updateColisageGlobal(
            @Param("cmp_id") Integer cmpId,
            @Param("id") Integer id,
            @Param("type") String type,
            @Param("stk_code") String stkCode,
            @Param("xcolis") Integer xcolis,
            @Param("xcolis_v") Integer xcolis_v,
            @Param("xfrigo") Integer xfrigo,
            @Param("xpsycho") Integer xpsycho,
            @Param("xchers") Integer xchers,
            @Param("xsachet") Integer xsachet,
            @Param("xbac") Integer xbac,
            @Param("xpalette") Integer xpalette);

    @Transactional
    @Modifying
    @Query(value = """
            DELETE FROM VNT_COLIS T
             WHERE T.VCO_CMP_ID = :cmp_id
               AND T.VCO_VNT_ID = :id
               AND T.VCO_VNT_TYPE = :type
               AND T.VCO_STK_CODE = :stk_code
            """, nativeQuery = true)
    int deleteEtiquettes(@Param("cmp_id") Integer cmpId, @Param("id") Integer id, @Param("type") String type, @Param("stk_code") String stkCode);

    @Procedure(procedureName = "SETUP_NEW.P_GENERE_ETIQUTE")
    void generateEtiquette(
            @Param("P_CMP") Integer cmpId,
            @Param("P_VNT") Integer id,
            @Param("P_STK") String stkCode,
            @Param("P_TYPE") String type,
            @Param("P_USER") String user
    );

}
