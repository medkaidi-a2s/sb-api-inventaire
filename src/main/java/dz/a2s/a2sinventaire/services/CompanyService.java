package dz.a2s.a2sinventaire.services;

import dz.a2s.a2sinventaire.entities.Company;

import java.util.List;

public interface CompanyService {

    List<Company> findAll();
    Integer getMethod();
    Integer getFormatImpression();
    Integer getMethodInventaire();

}
