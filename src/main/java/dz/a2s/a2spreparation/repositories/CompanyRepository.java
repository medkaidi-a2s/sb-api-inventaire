package dz.a2s.a2spreparation.repositories;

import dz.a2s.a2spreparation.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
}
