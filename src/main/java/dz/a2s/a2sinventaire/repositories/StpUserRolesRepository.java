package dz.a2s.a2sinventaire.repositories;

import dz.a2s.a2sinventaire.entities.StpUserRoles;
import dz.a2s.a2sinventaire.entities.keys.StpUserRolesId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StpUserRolesRepository extends JpaRepository<StpUserRoles, StpUserRolesId> {

    @Query(value = """
            select nvl(URL_LECTURE, 0) valeur
            from STP_USER_ROLES
            WHERE URL_CMP_ID = :companyId
                and URL_FORME_CODE = :formCode
                and URL_USR_CODE = (
                    select USR_TYPE
                    from STP_USERS u
                    where u.USR_CMP_ID = URL_CMP_ID
                    and USR_CODE = :username
                )
            """, nativeQuery = true)
    Integer getAuthorization(@Param("username") String username,@Param("companyId") Integer companyId,@Param("formCode") Integer formCode);

}
