package dz.a2s.a2spreparation.repositories.views;

import dz.a2s.a2spreparation.dto.controle.projections.MasterControleProjection;
import dz.a2s.a2spreparation.dto.preparation.CommandeReceiptProjection;
import dz.a2s.a2spreparation.entities.keys.VenteZoneId;
import dz.a2s.a2spreparation.entities.views.CommandeZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandeZoneRepository extends JpaRepository<CommandeZone, VenteZoneId> {

    //    @Query(value = """
//                SELECT *
//                FROM PRP_LISTE_CDE_ZONES_GLOB
//                WHERE VNT_CMP_ID = :companyId
//                  AND VBZ_ZONE = :zone
//                  AND (:date IS NULL OR TRUNC(VNT_DATE) = TO_DATE(:date, 'yyyy-MM-dd'))
//                  AND (
//                      VBZ_STATUT_PREPARE IS NULL OR VBZ_STATUT_PREPARE IN (0, 1, 2, 3)
//                  )
//                  AND (
//                    (VBZ_STATUT_PREPARE = 0  OR VBZ_STATUT_PREPARE IS NULL)
//                    OR (VBZ_STATUT_PREPARE > 0 AND VBZ_PREPAR_ID = :preparId)
//                  )
//            """, nativeQuery = true)
    @Query(value = """
                SELECT *
                  FROM PRP_LISTE_CDE_ZONES_GLOB T
                 WHERE EXISTS
                 (SELECT 'X'
                          FROM STP_TIERS_ZONES X
                         WHERE X.TRZ_CMP_ID = T.VNT_CMP_ID
                           AND X.TRZ_ZNS_ID = T.VBZ_ZONE
                           AND X.TRZ_TER_ID = :preparId
                           AND X.TRZ_TER_TYPE = 4)
                   AND (VNT_CMP_ID = :companyId)
                   AND (:date IS NULL OR
                       TRUNC(VNT_DATE) = TO_DATE(:date, 'yyyy-MM-dd'))
                   /*AND (VBZ_STATUT_PREPARE IS NULL OR VBZ_STATUT_PREPARE IN (0, 1, 2, 3))*/
                   AND ((VBZ_STATUT_PREPARE = 0 OR VBZ_STATUT_PREPARE IS NULL) OR
                       (VBZ_STATUT_PREPARE > 0 AND VBZ_PREPAR_ID = :preparId))
            """, nativeQuery = true)
    List<CommandeZone> getListCmdZones(@Param("companyId") Integer companyId, @Param("preparId") Integer preparId, @Param("date") String date);

    @Query(value = """
            SELECT *
              FROM PRP_LISTE_CDE_ZONES_GLOB T
             WHERE T.VNT_CMP_ID = :cmp_id
               AND (:date IS NULL OR TRUNC(T.VNT_DATE) = TO_DATE(:date, 'yyyy-MM-dd'))
               AND REFERENCE IS NOT NULL
               AND (:search IS NULL OR
                   LOWER(T.REFERENCE || ' ' || T.CLIENT || ' ' || T.PORTEFEUILLE || ' ' ||
                          T.REGION || ' ' || T.ZONE) LIKE LOWER('%' || :search || '%'))
            """, nativeQuery = true)
    List<CommandeZone> getAllCommandesZone(@Param("cmp_id") Integer companyId, @Param("date") String date, @Param("search") String search);

    @Query(value = """
                select count(*) from PRP_LISTE_CDE_ZONES_GLOB t
                where t.VNT_CMP_ID = :V_VBZ_CMP_ID
                  and t.VNT_ID = :V_VBZ_VNT_ID
                  and t.VNT_TYPE = :V_VBZ_VNT_TYPE
                  and t.VNT_STK_CODE = :V_VBZ_STK_CODE
                  and t.VBZ_ZONE = :V_VBZ_ZONE
                  and t.VBZ_PREPAR_ID <> :V_VBZ_PREPAR_ID
                  and t.vbz_statut_prepare = 2
            """, nativeQuery = true)
    Integer isPreparationStartedByOther(@Param("V_VBZ_CMP_ID") int v_vbz_cmp_id, @Param("V_VBZ_VNT_ID") int v_vbz_vnt_id, @Param("V_VBZ_VNT_TYPE") String v_vbz_vnt_type, @Param("V_VBZ_STK_CODE") String v_vbz_stk_code, @Param("V_VBZ_ZONE") int v_vbz_zone, @Param("V_VBZ_PREPAR_ID") int v_vbz_prepar_id);

    @Procedure(procedureName = "logistiques.P_EDIT_START_PREPAR_ZONE", outputParameterName = "p_msg")
    Integer startPrepareZone(@Param("V_VBZ_CMP_ID") int v_vbz_cmp_id, @Param("V_VBZ_VNT_ID") int v_vbz_vnt_id, @Param("V_VBZ_VNT_TYPE") String v_vbz_vnt_type, @Param("V_VBZ_STK_CODE") String v_vbz_stk_code, @Param("V_VBZ_ZONE") int v_vbz_zone, @Param("V_VBZ_PREPAR_ID") int v_vbz_prepar_id);

    //    @Query(value = """
//                SELECT *
//                FROM PRP_LISTE_CDE_ZONE_CONTROLES
//                WHERE VNT_CMP_ID = :companyId
//                  AND VBZ_ZONE = :zone
//                  AND (:date IS NULL OR TRUNC(VNT_DATE) = TO_DATE(:date, 'yyyy-MM-dd'))
//                  AND VBZ_STATUT_PREPARE = 3
//                  OR (VBZ_STATUT_PREPARE > 3 AND VBZ_VERIF_ID = :utilisateurId)
//            """, nativeQuery = true)
//    @Query(value = """
//            SELECT *
//            FROM PRP_LISTE_CDE_ZONE_CONTROLES
//            WHERE VNT_CMP_ID = :companyId
//              AND VBZ_ZONE = :zone
//              AND (:date IS NULL OR TRUNC(VNT_DATE) = TO_DATE(:date, 'yyyy-MM-dd'))
//              AND (VBZ_STATUT_PREPARE = 3 OR (VBZ_STATUT_PREPARE > 3 AND VBZ_VERIF_ID = :utilisateurId))
//            """, nativeQuery = true)
    @Query(value = """
                SELECT *
                  FROM PRP_LISTE_CDE_ZONE_CONTROLES T
                 WHERE EXISTS (SELECT 'X'
                          FROM STP_TIERS_ZONES X
                         WHERE X.TRZ_CMP_ID = T.VNT_CMP_ID
                           AND X.TRZ_ZNS_ID = T.VBZ_ZONE
                           AND X.TRZ_TER_ID = :utilisateurId
                           AND X.TRZ_TER_TYPE = 5)
                   AND VNT_CMP_ID = :companyId
                   AND (:date IS NULL OR
                       TRUNC(VNT_DATE) = TO_DATE(:date, 'yyyy-MM-dd'))
                   AND (VBZ_STATUT_PREPARE = 3 OR
                       (VBZ_STATUT_PREPARE > 3 AND VBZ_VERIF_ID = :utilisateurId))
            
            """, nativeQuery = true)
    List<CommandeZone> getPreparedCommandesZone(@Param("companyId") Integer companyId, @Param("utilisateurId") Integer utilisateurId, @Param("date") String date);

    @Query(value = """
            SELECT B.VNT_CMP_ID AS CMP_ID,
                   B.VNT_ID AS ID,
                   B.VNT_TYPE AS TYPE,
                   B.VNT_STK_CODE AS STK_CODE,
                   B.VNT_REFERENCE AS REFERENCE,
                   B.VNT_DATE AS VNT_DATE,
                   T.REGION AS REGION,
                   T.CLIENT AS CLIENT,
                   B.VNT_TOTAL_TTC AS TOTAL_TTC,
                   PREPARATEUR.TER_NOM AS PREPARATEUR,
                   CONTROLEUR1.TER_NOM AS CONTROLEUR1,
                   CONTROLEUR2.TER_NOM AS CONTROLEUR2,
                   DECODE(B.VNT_STATUT_PREPARE,
                          0,
                          'Non affectée',
                          1,
                          'En attente de préparation',
                          2,
                          'En préparation',
                          3,
                          'En attente de contrôle',
                          4,
                          'En contrôle',
                          5,
                          'En attente de facturation') AS STATUT,
                   SUM(T.NBR_LIGNE) AS NBR_LIGNE,
                   SUM(T.NBR_LIGNE_VALID) AS NBR_LIGNE_VALID
              FROM PRP_LISTE_CDE_ZONE_CONTROLES T
              JOIN VNT_BONS B
                ON B.VNT_CMP_ID = T.VNT_CMP_ID
               AND B.VNT_ID = T.VNT_ID
               AND B.VNT_TYPE = T.VNT_TYPE
               AND B.VNT_STK_CODE = T.VNT_STK_CODE
              LEFT JOIN STP_TIERS PREPARATEUR
                ON PREPARATEUR.TER_ID = B.VNT_CONTROLEUR_ID
               AND PREPARATEUR.TER_TYPE = B.VNT_CONTROLEUR_TYPE
              LEFT JOIN STP_TIERS CONTROLEUR1
                ON CONTROLEUR1.TER_ID = B.VNT_VERIFICATEUR_ID
               AND CONTROLEUR1.TER_TYPE = B.VNT_VERIFICATEUR_TYPE
              LEFT JOIN STP_TIERS CONTROLEUR2
                ON CONTROLEUR2.TER_ID = B.VNT_VERIFICATEUR_ID2
             WHERE B.VNT_CMP_ID = :cmpId
               AND (:date IS NULL OR TRUNC(B.VNT_DATE) = TO_DATE(:date, 'yyyy-MM-dd'))
               AND (B.VNT_STATUT_PREPARE = 3 OR
                   (B.VNT_STATUT_PREPARE > 3 AND B.VNT_VERIFICATEUR_ID = :utilisateurId))
             GROUP BY B.VNT_CMP_ID,
                      B.VNT_ID,
                      B.VNT_TYPE,
                      B.VNT_STK_CODE,
                      B.VNT_REFERENCE,
                      B.VNT_DATE,
                      T.REGION,
                      T.CLIENT,
                      B.VNT_TOTAL_TTC,
                      PREPARATEUR.TER_NOM,
                      CONTROLEUR1.TER_NOM,
                      CONTROLEUR2.TER_NOM,
                      B.VNT_STATUT_PREPARE
             ORDER BY B.VNT_ID DESC
            """, nativeQuery = true)
    List<MasterControleProjection> getPreparedBonCommandeZone(@Param("cmpId") Integer cmpId, @Param("utilisateurId") Integer utilisateurId, @Param("date") String date);

    @Procedure(procedureName = "logistiques.P_VALIDE_CDE_PREPARE_ZONE", outputParameterName = "p_msg")
    Integer setCommandeZonePrepared(@Param("P_CMP") Integer cmpId, @Param("P_VNT") Integer id, @Param("P_TYPE") String type, @Param("P_STK") String stkCode, @Param("P_ZONE") Integer zone, @Param("P_USER") String user);

    @Query(value = """
            select count(*) from PRP_LISTE_CDE_ZONES_GLOB t
            where t.VNT_CMP_ID = :V_VBZ_CMP_ID
              and t.VNT_ID = :V_VBZ_VNT_ID
              and t.VNT_TYPE = :V_VBZ_VNT_TYPE
              and t.VNT_STK_CODE = :V_VBZ_STK_CODE
              and t.VBZ_ZONE = :V_VBZ_ZONE
              and t.VBZ_VERIF_ID <> :V_VBZ_VERIF_ID
              and t.vbz_statut_prepare = 4
            """, nativeQuery = true)
    Integer isControlStartedByOther(@Param("V_VBZ_CMP_ID") int v_vbz_cmp_id, @Param("V_VBZ_VNT_ID") int v_vbz_vnt_id, @Param("V_VBZ_VNT_TYPE") String v_vbz_vnt_type, @Param("V_VBZ_STK_CODE") String v_vbz_stk_code, @Param("V_VBZ_ZONE") int v_vbz_zone, @Param("V_VBZ_VERIF_ID") int v_vbz_verif_id);

    @Procedure(procedureName = "logistiques.P_EDIT_START_CONTROL_ZONE", outputParameterName = "p_msg")
    Integer startControleZone(@Param("V_VBZ_CMP_ID") int v_vbz_cmp_id, @Param("V_VBZ_VNT_ID") int v_vbz_vnt_id, @Param("V_VBZ_VNT_TYPE") String v_vbz_vnt_type, @Param("V_VBZ_STK_CODE") String v_vbz_stk_code, @Param("V_VBZ_ZONE") int v_vbz_zone, @Param("V_VBZ_VERIF_ID") int v_vbz_verif_id);

    @Procedure(procedureName = "logistiques.P_EDIT_START_CONTROL_CMD_ZONE", outputParameterName = "p_msg")
    Integer startControleCommandeZone(
            @Param("V_CMP_ID") int v_vbz_cmp_id,
            @Param("V_VNT_ID") int v_vbz_vnt_id,
            @Param("V_VNT_TYPE") String v_vbz_vnt_type,
            @Param("V_STK_CODE") String v_vbz_stk_code,
            @Param("V_VERIF_ID") int v_vbz_verif_id,
            @Param("V_USER") String v_user
    );

    @Procedure(procedureName = "logistiques.P_SET_ZONE_CONTROLLED", outputParameterName = "p_msg")
    Integer setCommandeZoneControlled(
            @Param("P_CMP") Integer cmpId,
            @Param("P_VNT") Integer id,
            @Param("P_TYPE") String type,
            @Param("P_STK") String stkCode,
            @Param("P_ZONE") Integer zone,
            @Param("P_USER") String username,
            @Param("P_COLIS_V") Integer colisV,
            @Param("P_COLIS_D") Integer colisD,
            @Param("P_FRIGO") Integer frigo,
            @Param("P_PSYCHO") Integer psycho,
            @Param("P_CHERS") Integer chers,
            @Param("P_SACHET") Integer sachet,
            @Param("P_BACS") Integer bacs
    );

    @Procedure(procedureName = "logistiques.P_SET_CDE_ZONE_CONTROLLED", outputParameterName = "p_msg")
    Integer setCommandeZoneGlobalControlled(
            @Param("P_CMP") Integer cmpId,
            @Param("P_VNT") Integer id,
            @Param("P_TYPE") String type,
            @Param("P_STK") String stkCode,
            @Param("P_USER") String username,
            @Param("P_COLIS_V") Integer colisV,
            @Param("P_COLIS_D") Integer colisD,
            @Param("P_FRIGO") Integer frigo,
            @Param("P_PSYCHO") Integer psycho,
            @Param("P_CHERS") Integer chers,
            @Param("P_SACHET") Integer sachet,
            @Param("P_BACS") Integer bacs
    );

    @Query(value = """
            SELECT v.vnt_reference, --Preparation par zone
                   v.vnt_date,
                   r.ter_nom,
                   r.ter_adresse,
                   r.ter_region_lib,
                   v.VNT_REF_ASSOCIE2 AS xtable,
                   ZNS_NOM as zone,
                   count(DISTINCT x.vbz_zone) NBR
              FROM vnt_bons v
              JOIN stp_tiers r
                ON v.vnt_cmp_id = r.ter_cmp_id
               AND v.vnt_ter_id = r.ter_id
               AND v.vnt_ter_type = r.ter_type
              JOIN VNT_BON_ZONES x
                on VBZ_CMP_ID = VNT_CMP_ID
               and VBZ_VNT_ID = VNT_ID
               and VBZ_VNT_TYPE = VNT_TYPE
               and VBZ_STK_CODE = VNT_STK_CODE
              join STP_ZONES
                on ZNS_ID = VBZ_ZONE
             WHERE x.vbz_cmp_id = :cmpId
               AND x.vbz_vnt_id = :vntId
               AND x.vbz_vnt_type = :type
               AND x.vbz_stk_code = :stkCode
               AND x.vbz_zone = :zone
             GROUP BY v.vnt_reference,
                      v.vnt_date,
                      r.ter_nom,
                      r.ter_adresse,
                      ZNS_NOM,
                      r.ter_region_lib,
                      v.VNT_REF_ASSOCIE2
            """, nativeQuery = true)
    CommandeReceiptProjection getReceiptData (@Param("cmpId") Integer cmpId, @Param("vntId") Integer vntId, @Param("type") String type, @Param("stkCode") String stkCode, @Param("zone") Integer zone);

    @Query(value = """
            SELECT *
              FROM PRP_LISTE_CDE_ZONE_CONTROLES T
             WHERE VNT_CMP_ID = :companyId
               AND (:date IS NULL OR TRUNC(VNT_DATE) = TO_DATE(:date, 'yyyy-MM-dd'))
               AND VBZ_STATUT_PREPARE = 5
            """, nativeQuery = true)
    List<CommandeZone> getControlledCommandesZone(@Param("companyId") Integer companyId, @Param("date") String date);

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

}
