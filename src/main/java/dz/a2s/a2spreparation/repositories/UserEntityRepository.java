package dz.a2s.a2spreparation.repositories;

import dz.a2s.a2spreparation.entities.UserEntity;
import dz.a2s.a2spreparation.entities.keys.UserEntityId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, UserEntityId> {

    @Query(value = "SELECT USR_CODE, USR_CMP_ID, USR_NOM_LOC, USR_PASSWORD FROM STP_USERS WHERE LOWER(USR_CODE) = LOWER(:username) AND USR_CMP_ID = :companyId", nativeQuery = true)
    Optional<UserEntity> findByUsernameAndCompanyId(@Param("username") String username, @Param("companyId") int companyId);

    @Query(value = """
            select ter_zone_preparation
            from stp_tier_users u, stp_tiers t
            where u.tru_cmp_id = t.ter_cmp_id
              and u.tru_ter_type = t.ter_type
              and u.tru_ter_id = t.ter_id
              and LOWER(u.tru_usr_code) = LOWER(:username)
              and t.ter_cmp_id = :companyId
            """, nativeQuery = true)
    String getPreparationZone(@Param("username") String username, @Param("companyId") Integer companyId);

    @Query(value = """
            select ter_id
            from stp_tier_users u, stp_tiers t
            where u.tru_cmp_id = t.ter_cmp_id
              and u.tru_ter_type = t.ter_type
              and u.tru_ter_id = t.ter_id
              and u.tru_usr_code = :username
              and t.ter_cmp_id = :companyId
              and (:tierType is null or t.ter_type = :tierType)
            """, nativeQuery = true)
    List<Integer> getUtilisateurId(@Param("username") String username, @Param("companyId") Integer companyId, @Param("tierType") Integer tierType);

    @Query(value = """
                SELECT USR_PASSWORD 
                FROM STP_USERS 
                WHERE USR_CODE = :username AND USR_CMP_ID = :companyId
            """, nativeQuery = true)
    String getUserPassword(@Param("username") String username, @Param("companyId") Integer companyId);

    @Transactional
    @Modifying
    @Query(value = """
                UPDATE STP_USERS
                SET USR_PASSWORD = :password
                WHERE USR_CODE = :username AND USR_CMP_ID = :companyId
            """, nativeQuery = true)
    Integer changePassword(@Param("username") String username, @Param("companyId") Integer companyId, @Param("password") String password);

}
