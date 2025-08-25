package dz.a2s.a2spreparation.repositories;

import dz.a2s.a2spreparation.dto.common.ListProjection;
import dz.a2s.a2spreparation.dto.inventaire.projections.ComptageAccessProjection;
import dz.a2s.a2spreparation.dto.inventaire.projections.InventaireProjection;
import dz.a2s.a2spreparation.entities.Inventaire;
import dz.a2s.a2spreparation.entities.keys.InventaireId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventaireRepository extends JpaRepository<Inventaire, InventaireId> {

    @Query(value = """
            SELECT INV_CMP_ID     AS CMP_ID,
                   INV_ID         AS ID,
                   INV_DATE       AS INV_DATE,
                   T.INV_REMARQUE AS REMARQUE
              FROM STP_INVENTAIRES T
             WHERE T.INV_CMP_ID = :cmp_id
               AND NVL(T.INV_VALIDE_FLAG, 0) = 1
               AND NVL(T.INV_CLOTURE_FLAG, 0) = 0
            """, nativeQuery = true)
    List<InventaireProjection> getListInventaires(@Param("cmp_id") Integer cmpId);

    @Query(value = """
            SELECT LVD_ID AS CODE, LVD_NOM1 AS NOM
              FROM GMS_LVD T
             WHERE LVD_CMP_ID = :cmp_id
               AND LVD_LVM_ID = 63
               AND LVD_ACTIVE_FLAG = 1
            """, nativeQuery = true)
    List<ListProjection> getListComptage(@Param("cmp_id") Integer cmpId);

    @Query(value = """
            SELECT INU_CMP_ID,
                   INU_INV_ID,
                   INU_USR_CODE,
                   NVL(INU_COMPTAGE1, 0) COMPTAGE1,
                   NVL(INU_COMPTAGE2, 0) COMPTAGE2,
                   NVL(INU_COMPTAGE3, 0) COMPTAGE3
              FROM STP_INVENTAIRE_USERS T
             WHERE T.INU_CMP_ID = :cmp_id
               AND T.INU_INV_ID = :inv_id
               AND LOWER(T.INU_USR_CODE) = LOWER(:username)
            """, nativeQuery = true)
    ComptageAccessProjection getComptageAccess(@Param("cmp_id") Integer cmpId, @Param("inv_id") Integer invId, @Param("username") String username);

}
