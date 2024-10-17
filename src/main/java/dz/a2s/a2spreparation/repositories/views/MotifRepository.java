package dz.a2s.a2spreparation.repositories.views;

import dz.a2s.a2spreparation.entities.views.Motif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MotifRepository extends JpaRepository<Motif, Integer> {

    @Query(value = """
                SELECT * 
                FROM BI_MOTIF_LIV
            """, nativeQuery = true)
    List<Motif> getAll();

}
