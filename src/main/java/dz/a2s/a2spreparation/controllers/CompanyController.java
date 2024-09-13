package dz.a2s.a2spreparation.controllers;

import dz.a2s.a2spreparation.entities.Company;
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
@RequestMapping("/auth/companies")
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("")
    public ResponseEntity<Object> findAllCompanies() {
        log.info("findAllCompanies from the CompanyController");
        List<Company> companies = this.companyService.findAll();
        log.info("Companies fetched from the service {}", companies);

        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

}
