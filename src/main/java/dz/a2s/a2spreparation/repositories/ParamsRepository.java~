package dz.a2s.a2spreparation.repositories;

import dz.a2s.a2spreparation.entities.Params;
import dz.a2s.a2spreparation.entities.keys.ParamsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ParamsRepository extends JpaRepository<Params, ParamsId> {

    @Query(value = "select PRM_METHODE_PREPARE from STP_PARAMS where PRM_CMP_ID=:companyId and PRM_TYPE=1", nativeQuery = true)
    Optional<Integer> getMethod(@Param("companyId") int companyId);

}
