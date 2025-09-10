package dz.a2s.a2spreparation.repositories;

import dz.a2s.a2spreparation.entities.VntBonBacs;
import dz.a2s.a2spreparation.entities.keys.VntBonBacsId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VntBonBacsRepository extends JpaRepository<VntBonBacs, VntBonBacsId> {
}
