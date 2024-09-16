package dz.a2s.a2spreparation.repositories;

import dz.a2s.a2spreparation.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    @Query(value = "Select CMP_ID, CMP_NOM_LOC,CMP_VIL_ID from STP_COMPAGNIES order by CMP_ID", nativeQuery = true)
    List<Company> findAllCompanies();

}
