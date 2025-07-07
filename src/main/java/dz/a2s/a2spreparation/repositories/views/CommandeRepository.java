package dz.a2s.a2spreparation.repositories.views;

import dz.a2s.a2spreparation.dto.commande.projections.ColisageProjection;
import dz.a2s.a2spreparation.dto.preparation.CommandeReceiptProjection;
import dz.a2s.a2spreparation.entities.keys.VenteId;
import dz.a2s.a2spreparation.entities.views.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommandeRepository extends JpaRepository<Commande, VenteId> {

    @Query(value = """
                SELECT * 
                FROM PRP_LISTE_CDES 
                WHERE VNT_CMP_ID = :companyId 
                  AND (:date IS NULL OR TRUNC(VNT_DATE) = TO_DATE(:date, 'yyyy-MM-dd')) 
                  AND REFERENCE IS NOT NULL
            """, nativeQuery = true)
    List<Commande> getListCommande(@Param("companyId") Integer companyId, @Param("date") String date);

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

}
