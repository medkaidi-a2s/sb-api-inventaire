package dz.a2s.a2sinventaire.repositories;

import dz.a2s.a2sinventaire.entities.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, String> {

    @Query(value = "SELECT USR_CODE, USR_NOM_LOC, USR_PASSWORD FROM STP_USERS WHERE LOWER(USR_CODE) = LOWER(:username)", nativeQuery = true)
    Optional<UserEntity> findUserByUsername(@Param("username") String username);

//    Optional<UserEntity> findByUsername(String username);

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
                WHERE USR_CODE = :username
            """, nativeQuery = true)
    String getUserPassword(@Param("username") String username);

    @Transactional
    @Modifying
    @Query(value = """
                UPDATE STP_USERS
                SET USR_PASSWORD = :password
                WHERE USR_CODE = :username
            """, nativeQuery = true)
    Integer changePassword(@Param("username") String username, @Param("password") String password);

}
