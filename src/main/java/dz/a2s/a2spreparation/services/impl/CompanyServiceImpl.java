package dz.a2s.a2spreparation.services.impl;

import dz.a2s.a2spreparation.entities.Company;
import dz.a2s.a2spreparation.exceptions.RessourceNotFoundException;
import dz.a2s.a2spreparation.repositories.CompanyRepository;
import dz.a2s.a2spreparation.repositories.ParamsRepository;
import dz.a2s.a2spreparation.services.CompanyService;
import dz.a2s.a2spreparation.services.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final ParamsRepository paramsRepository;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public List<Company> findAll() {
        log.info("findAll method from companyService");
        return this.companyRepository.findAllCompanies();
    }

    @Override
    public Integer getMethod() {
        int companyId = this.customUserDetailsService.getCurrentCompanyId();
        var method = this.paramsRepository.getMethod(companyId);
        log.info("companyId={}", companyId);
        log.info("Fetched the preparation method from the repo | method={}", method);
        return this.paramsRepository.getMethod(companyId).orElseThrow(() -> new RessourceNotFoundException("Méthode non initialisée"));
    }
}
