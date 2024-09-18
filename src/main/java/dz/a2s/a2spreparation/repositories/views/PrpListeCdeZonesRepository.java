package dz.a2s.a2spreparation.repositories.views;

import dz.a2s.a2spreparation.entities.keys.VenteId;
import dz.a2s.a2spreparation.entities.views.PrpListeCdeZones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PrpListeCdeZonesRepository extends JpaRepository<PrpListeCdeZones, VenteId> {

    @Query(value = "SELECT * FROM PRP_LISTE_CDE_ZONES", nativeQuery = true)
    List<PrpListeCdeZones> getAll();

}
