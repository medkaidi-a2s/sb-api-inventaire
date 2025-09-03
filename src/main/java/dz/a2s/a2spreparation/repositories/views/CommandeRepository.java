package dz.a2s.a2spreparation.repositories.views;

import dz.a2s.a2spreparation.dto.commande.projections.ColisageProjection;
import dz.a2s.a2spreparation.dto.commande.projections.CommandeColisageProjection;
import dz.a2s.a2spreparation.dto.preparation.CommandeReceiptProjection;
import dz.a2s.a2spreparation.entities.keys.VenteId;
import dz.a2s.a2spreparation.entities.views.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
                    NVL(T.VNT_NBR_SACHETS, 0) AS SACHET
               FROM VNT_BONS T
              WHERE VNT_CMP_ID = :cmpId
                AND VNT_ID = :id
                AND VNT_TYPE = :type
                AND VNT_STK_CODE = :stkCode
            """, nativeQuery = true)
    ColisageProjection getColisageCommande(@Param("cmpId") Integer cmpId, @Param("id") Integer id, @Param("type") String type, @Param("stkCode") String stkCode);

    @Query(value = """
            SELECT *
              FROM (SELECT COUNT(*) OVER() AS TOTAL_RECORDS,
                           VNT_CMP_ID,
                           VNT_ID,
                           VNT_TYPE,
                           VNT_DATE,
                           VNT_STK_CODE,
                           VNT_REFERENCE,
                           VNT_TOTAL_TTC,
                           R.TER_NOM LIBELLE_TIER,
                           TER_REGION_LIB REGION,
                           VNT_TOTAL_COLIS,
                           VNT_BACS,
                           (SELECT COUNT(*)
                              FROM VNT_COLIS
                             WHERE VCO_CMP_ID = VNT_CMP_ID
                               AND VCO_VNT_ID = VNT_ID
                               AND VCO_STK_CODE = VNT_STK_CODE
                               AND VCO_VNT_TYPE = VNT_TYPE) NBR_ETIQUETE,
                           NVL(VNT_PREP_FLAG, 0) VNT_PREP_FLAG,
                           ROW_NUMBER() OVER(ORDER BY VNT_ID) AS RNUM
                      FROM VNT_BONS T, STP_TIERS R
                     WHERE VNT_CMP_ID = :cmp_id
                       AND VNT_TYPE = 1
                       AND VNT_CMP_ID = TER_CMP_ID
                       AND VNT_TER_ID = TER_ID
                       AND VNT_TER_TYPE = TER_TYPE
                       AND NVL(VNT_ETAT_FLAG, 0) = 1
                       AND NVL(VNT_AVOIR_FLAG, 0) = 0
                       AND NVL(VNT_ANNULE_FLAG, 0) = 0
                       AND TO_DATE(VNT_DATE, 'DD/MM/RRRR') BETWEEN
                           TO_DATE(:date_debut, 'DD/MM/RRRR') AND
                           TO_DATE(:date_fin, 'DD/MM/RRRR')
                       AND NVL(NVL(VNT_PREP_FLAG, 0), 0) IN (0, NVL(:statut_prepare, 0))
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
            @Param("search") String search);

}
