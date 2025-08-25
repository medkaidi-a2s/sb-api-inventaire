package dz.a2s.a2spreparation.mappers;

import dz.a2s.a2spreparation.dto.common.ListProjection;
import dz.a2s.a2spreparation.dto.common.ListResponse;
import dz.a2s.a2spreparation.dto.inventaire.projections.ComptageAccessProjection;
import dz.a2s.a2spreparation.dto.inventaire.projections.InventaireProjection;
import dz.a2s.a2spreparation.dto.inventaire.response.ComptageAccessResponse;
import dz.a2s.a2spreparation.entities.Inventaire;

public class InventaireMappers {

    public static Inventaire fromInventaireProjection(InventaireProjection projection) {
        return new Inventaire(
                projection.getCmpId(),
                projection.getId(),
                projection.getInvDate(),
                projection.getRemarque()
        );
    }

    public static ListResponse fromListProjection(ListProjection projection) {
        return new ListResponse(
                projection.getCode(),
                projection.getNom()
        );
    }

    public static ComptageAccessResponse fromComptageAccessProjection(ComptageAccessProjection projection) {
        return new ComptageAccessResponse(
                projection.getComptage1(),
                projection.getComptage2(),
                projection.getComptage3()
        );
    }

}
