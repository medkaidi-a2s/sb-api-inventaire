package dz.a2s.a2sinventaire.repositories.views;

import dz.a2s.a2sinventaire.entities.views.Motif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MotifRepository extends JpaRepository<Motif, Integer> {

    @Query(value = """
                SELECT * 
                FROM BI_MOTIF_LIV
            """, nativeQuery = true)
    List<Motif> getAll();

}
