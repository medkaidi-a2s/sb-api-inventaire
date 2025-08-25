package dz.a2s.a2spreparation.services;

import dz.a2s.a2spreparation.dto.common.ListResponse;
import dz.a2s.a2spreparation.dto.inventaire.response.ComptageAccessResponse;
import dz.a2s.a2spreparation.entities.Inventaire;

import java.util.List;

public interface InventaireService {

    List<Inventaire> getListInventaires();
    List<ListResponse> getListComptages();
    ComptageAccessResponse getComptageAccess(Integer invId);

}
