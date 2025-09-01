package dz.a2s.a2spreparation.controllers;

import dz.a2s.a2spreparation.api.CompanyApi;
import dz.a2s.a2spreparation.dto.auth.AuthorizationDto;
import dz.a2s.a2spreparation.dto.response.SuccessResponseDto;
import dz.a2s.a2spreparation.entities.Company;
import dz.a2s.a2spreparation.services.AuthorizationService;
import dz.a2s.a2spreparation.services.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
//@RequestMapping("/auth/companies")
public class CompanyController implements CompanyApi {

    private final CompanyService companyService;
    private final AuthorizationService authorizationService;

    public ResponseEntity<SuccessResponseDto<List<Company>>> findAllCompanies() {
        log.info("findAllCompanies from the CompanyController");
        List<Company> companies = this.companyService.findAll();
        log.info("Companies fetched from the service {}", companies);

        SuccessResponseDto<List<Company>> successResponseDto = new SuccessResponseDto<List<Company>>(
                HttpStatus.OK.value(),
                "Liste des compagnies",
                companies
        );

        return ResponseEntity.ok(successResponseDto);
    }

    public ResponseEntity<SuccessResponseDto<Integer>> getMethod() {
        log.info("Entering getMethod method from CompanyController");

        Integer method = this.companyService.getMethod();
        SuccessResponseDto<Integer> successResponseDto = new SuccessResponseDto<Integer>(
                HttpStatus.OK.value(),
                "Méthode d'affectation",
                method
        );
        log.info("Returning the value of the method {}", method);

        return ResponseEntity.ok(successResponseDto);
    }

    @Override
    public ResponseEntity<SuccessResponseDto<Integer>> getFormatImpression() {
        log.info("| Entry | CompanyController.getFormatImpression");

        var formatImpression = this.companyService.getFormatImpression();
        var response = new SuccessResponseDto<Integer>(
                200,
                "Format d'impression récupéré avec succès",
                formatImpression
        );

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<SuccessResponseDto<Integer>> getMethodInventaire() {
        log.info("| Entry | CompanyController.getMethodInventaire");

        var methodInventaire = this.companyService.getMethodInventaire();
        var response = new SuccessResponseDto<Integer>(
                200,
                "Méthode d'inventaire récupérée avec succès",
                methodInventaire
        );

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<SuccessResponseDto<List<AuthorizationDto>>> getAuthorizations() {
        log.info("| Entry | CompanyController.getAuthorizations");

        var authorizations = this.authorizationService.getAuthorizations();
        var response = new SuccessResponseDto<List<AuthorizationDto>>(
                200,
                "Autorisations récupérées avec succès",
                authorizations
        );

        return ResponseEntity.ok(response);
    }

}
