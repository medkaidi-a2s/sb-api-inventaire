package dz.a2s.a2spreparation.repositories;

import dz.a2s.a2spreparation.dto.auth.AuthorizationProjection;
import dz.a2s.a2spreparation.entities.Params;
import dz.a2s.a2spreparation.entities.keys.ParamsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

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
            SELECT URL_FORME_CODE AS CODE, NVL(URL_LECTURE, 0) AS VALEUR
              FROM STP_USER_ROLES
             WHERE URL_CMP_ID = :cmpId
               AND URL_FORME_CODE IN (:codes)
               AND URL_USR_CODE = (SELECT USR_TYPE
                                     FROM STP_USERS U
                                    WHERE U.USR_CMP_ID = URL_CMP_ID
                                      AND LOWER(USR_CODE) = LOWER(:username))
            """, nativeQuery = true)
    List<AuthorizationProjection> getAuthorizations(@Param("cmpId") Integer cmpId, @Param("codes") List<Integer> codes, @Param("username") String username);

}
