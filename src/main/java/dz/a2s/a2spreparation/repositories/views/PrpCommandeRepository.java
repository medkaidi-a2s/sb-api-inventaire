package dz.a2s.a2spreparation.repositories.views;

import dz.a2s.a2spreparation.entities.keys.VenteId;
import dz.a2s.a2spreparation.entities.views.PrpCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrpCommandeRepository extends JpaRepository<PrpCommande, VenteId> {

    @Query(value = "SELECT * FROM PRP_LISTE_CDES WHERE VNT_CMP_ID = :companyId", nativeQuery = true)
    List<PrpCommande> getListCommande(@Param("companyId") Integer companyId);

}
