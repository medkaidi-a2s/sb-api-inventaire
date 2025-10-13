package dz.a2s.a2sinventaire.services;

import dz.a2s.a2sinventaire.dto.common.ListResponse;
import dz.a2s.a2sinventaire.dto.inventaire.request.SaisiEcartRequest;
import dz.a2s.a2sinventaire.dto.inventaire.request.SaisiRequest;
import dz.a2s.a2sinventaire.dto.inventaire.response.ComptageAccessResponse;
import dz.a2s.a2sinventaire.dto.inventaire.response.EcartLineResponse;
import dz.a2s.a2sinventaire.dto.inventaire.response.InventaireLineResponse;
import dz.a2s.a2sinventaire.dto.inventaire.response.SaisiResponse;
import dz.a2s.a2sinventaire.dto.response.PaginatedDataDto;
import dz.a2s.a2sinventaire.entities.Inventaire;

import java.util.List;

public interface InventaireService {

    List<Inventaire> getListInventaires();
    List<ListResponse> getListComptages();
    ComptageAccessResponse getComptageAccess(Integer invId, String depot);
    String checkEmplacement(String emplacement);
    PaginatedDataDto<InventaireLineResponse> getInventaireLines(Integer invId, String depot, Integer comptage, String emplacement, Integer stockZero, String search, Integer page);
    PaginatedDataDto<EcartLineResponse> getEcartLines(Integer invId, Integer isEcart, String search, Integer page);
    SaisiResponse saisirInventaire(SaisiRequest request);
    int updateEcartLine(SaisiEcartRequest request);

}
