package dz.a2s.a2sinventaire.repositories;

import dz.a2s.a2sinventaire.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    @Query(value = "Select CMP_ID, CMP_NOM_ETR,CMP_VIL_ID from STP_COMPAGNIES order by CMP_ID", nativeQuery = true)
    List<Company> findAllCompanies();

}
