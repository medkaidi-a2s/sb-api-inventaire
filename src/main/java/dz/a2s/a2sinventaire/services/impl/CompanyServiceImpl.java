package dz.a2s.a2sinventaire.services.impl;

import dz.a2s.a2sinventaire.entities.Company;
import dz.a2s.a2sinventaire.exceptions.RessourceNotFoundException;
import dz.a2s.a2sinventaire.repositories.CompanyRepository;
import dz.a2s.a2sinventaire.repositories.ParamsRepository;
import dz.a2s.a2sinventaire.services.CompanyService;
import dz.a2s.a2sinventaire.services.CustomUserDetailsService;
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

    @Override
    public Integer getFormatImpression() {
        log.info("| Entry | CompanyService.getFormatImpression");

        Integer companyId = this.customUserDetailsService.getCurrentCompanyId();
        var formatImpression = this.paramsRepository.getFormatImpression(companyId);
        log.info("Fetched the format impression from the repo | formatImpression={}", formatImpression);

        return formatImpression.orElseThrow(() -> new RessourceNotFoundException("Format impression non initialisé"));
    }

    @Override
    public Integer getMethodInventaire() {
        log.info("| Entry | CompanyService.getMethodInventaire");

        var methodInventaire = this.paramsRepository.getMethodInventaire();
        log.info("Fetched the inventaire method from the repo | methodInventaire={}", methodInventaire);

        return methodInventaire.orElseThrow(() -> new RessourceNotFoundException("Méthode d'inventaire non initialisée"));
    }
}
