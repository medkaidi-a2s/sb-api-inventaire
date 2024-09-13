package dz.a2s.a2spreparation.repositories;

import dz.a2s.a2spreparation.entities.UserEntity;
import dz.a2s.a2spreparation.entities.keys.UserEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, UserEntityId> {

    @Query(value = "SELECT USR_CODE, USR_CMP_ID, USR_NOM_LOC, USR_PASSWORD FROM STP_USERS WHERE USR_CODE = :username", nativeQuery = true)
    Optional<UserEntity> findByUsername(@Param("username") String username);

}
