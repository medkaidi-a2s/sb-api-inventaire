package dz.a2s.a2sinventaire.repositories.views;

import dz.a2s.a2sinventaire.entities.keys.StpTiersId;
import dz.a2s.a2sinventaire.entities.views.PrpPrepareControle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrpPrepareControleRepository extends JpaRepository<PrpPrepareControle, StpTiersId> {

    @Query(value = "SELECT * FROM PRP_LISTE_PREPARE_CONTROLE WHERE TER_TYPE = 4", nativeQuery = true)
    List<PrpPrepareControle> getAllPreparateurs();

    @Query(value = "SELECT * FROM PRP_LISTE_PREPARE_CONTROLE WHERE TER_TYPE = 5", nativeQuery = true)
    List<PrpPrepareControle> getAllControleurs();

}
