package dz.a2s.a2spreparation.repositories;

import dz.a2s.a2spreparation.entities.Bac;
import dz.a2s.a2spreparation.entities.keys.BacId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BacRepository extends JpaRepository<Bac, BacId> {

    List<Bac> findAllByCmpIdAndStatsId(Integer cmpId, Integer statsId);

    @Query(value = """
            SELECT B.LBC_CMP_ID, B.LBC_LBT_ID, B.LBC_ID, B.LBC_CODE, B.LBC_LBS_ID, T.LBT_LIBELLE
              FROM LGS_BACS B
              JOIN LGS_BAC_TYPS T
                ON T.LBT_CMP_ID = B.LBC_CMP_ID
               AND T.LBT_ID = B.LBC_LBT_ID
             WHERE LBC_CMP_ID = :cmp_id
               AND LBC_LBS_ID = 1
            """, nativeQuery = true)
    List<Bac> findListeBacs(@Param("cmp_id") Integer cmpId);

}
