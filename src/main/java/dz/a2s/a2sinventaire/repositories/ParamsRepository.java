package dz.a2s.a2sinventaire.repositories;

import dz.a2s.a2sinventaire.dto.auth.AuthorizationProjection;
import dz.a2s.a2sinventaire.entities.Params;
import dz.a2s.a2sinventaire.entities.keys.ParamsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParamsRepository extends JpaRepository<Params, ParamsId> {

    @Query(value = "select PRM_METHODE_PREPARE from STP_PARAMS where PRM_CMP_ID=:companyId and PRM_TYPE=1", nativeQuery = true)
    Optional<Integer> getMethod(@Param("companyId") int companyId);

    @Query(value = """
            SELECT PRM_IMP_TECKET_TABLETTE
              FROM STP_PARAMS
             WHERE PRM_CMP_ID = :cmpId
               AND PRM_TYPE = 1
            """, nativeQuery = true)
    Optional<Integer> getFormatImpression(@Param("cmpId") Integer cmpId);

    @Query(value = """
            SELECT PRM_METHODE_INVENTAIRE
              FROM STP_PARAMS
             WHERE PRM_TYPE = 1
            """, nativeQuery = true)
    Optional<Integer> getMethodInventaire();

    @Query(value = """
            SELECT URL_FORME_CODE AS CODE, NVL(URL_LECTURE, 0) AS VALEUR
              FROM STP_USER_ROLES
             WHERE URL_FORME_CODE IN (:codes)
               AND URL_USR_CODE = (SELECT USR_TYPE
                                     FROM STP_USERS U
                                    WHERE LOWER(USR_CODE) = LOWER(:username))
            """, nativeQuery = true)
    List<AuthorizationProjection> getAuthorizations(@Param("codes") List<Integer> codes, @Param("username") String username);

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

}
