package dz.a2s.a2spreparation.services.impl;

import dz.a2s.a2spreparation.entities.Company;
import dz.a2s.a2spreparation.repositories.CompanyRepository;
import dz.a2s.a2spreparation.services.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;

    @Override
    public List<Company> findAll() {
        log.info("findAll method from companyService");
        return this.companyRepository.findAll();
    }
}
