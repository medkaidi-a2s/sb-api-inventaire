package dz.a2s.a2sinventaire.repositories;

import dz.a2s.a2sinventaire.entities.VntBonBacs;
import dz.a2s.a2sinventaire.entities.keys.VntBonBacsId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VntBonBacsRepository extends JpaRepository<VntBonBacs, VntBonBacsId> {
}
