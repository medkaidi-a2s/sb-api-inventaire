package dz.a2s.a2spreparation.repositories.views;

import dz.a2s.a2spreparation.entities.keys.VenteId;
import dz.a2s.a2spreparation.entities.views.Commande;
import dz.a2s.a2spreparation.entities.views.PrpCommande;
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
            @Param("p_cmp") int p_cmp,
            @Param("p_vnt") int p_vnt,
            @Param("p_stk") String p_stk,
            @Param("p_type") String p_type,
            @Param("p_prp") int p_prp,
            @Param("p_cnt1") int p_cnt1,
            @Param("p_cnt2") int p_cnt2,
            @Param("p_user") String p_user
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
            @Param("p_cmp") int p_cmp,
            @Param("p_vnt") int p_vnt,
            @Param("p_stk") String p_stk,
            @Param("p_type") String p_type,
            @Param("p_prp") int p_prp,
            @Param("p_cnt1") int p_cnt1,
            @Param("p_cnt2") int p_cnt2,
            @Param("p_user") String p_user
    );

    @Query(value = """
                SELECT * 
                FROM PRP_LISTE_CDE_AFFECTES 
                WHERE PREPARATEUR_ID = :preparateurId 
                AND VNT_CMP_ID = :companyId
                AND (:date IS NULL OR TRUNC(VNT_DATE) = TO_DATE(:date, 'yyyy-MM-dd'))
            """, nativeQuery = true)
    List<Commande> getCommandesParPreparateur(@Param("preparateurId") Integer preparateurId, @Param("companyId") Integer companyId,@Param("date") String date);

    @Procedure(procedureName = "logistiques.P_SET_CDE_PREPARED", outputParameterName = "p_msg")
    Integer setCommandePrepared(
            @Param("p_cmp") int p_cmp,
            @Param("p_vnt") int p_vnt,
            @Param("p_stk") String p_stk,
            @Param("p_type") String p_type,
            @Param("p_user") String p_user
    );

    @Procedure(procedureName = "logistiques.P_EDIT_START_PREPAR_CDE", outputParameterName = "p_msg")
    Integer startPrepareCde(
            @Param("p_vnt_cmp_id") int p_vnt_cmp_id,
            @Param("p_vnt_id") int p_vnt_id,
            @Param("p_vnt_type") String p_vnt_type,
            @Param("p_vnt_stk_code") String p_vnt_stk_code
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
            @Param("p_vnt_cmp_id") int p_vnt_cmp_id,
            @Param("p_vnt_id") int p_vnt_id,
            @Param("p_vnt_type") String p_vnt_type,
            @Param("p_vnt_stk_code") String p_vnt_stk_code
    );

    @Procedure(procedureName = "logistiques.P_SET_CDE_CONTROLLED", outputParameterName = "p_msg")
    Integer setCommandeControlled(
            @Param("p_cmp") int p_cmp,
            @Param("p_vnt") int p_vnt,
            @Param("p_stk") String p_stk,
            @Param("p_type") String p_type,
            @Param("p_user") String p_user
    );

}
