package dz.a2s.a2spreparation.services;

import dz.a2s.a2spreparation.entities.Company;

import java.util.List;

public interface CompanyService {

    List<Company> findAll();

    Integer getMethod();

}
