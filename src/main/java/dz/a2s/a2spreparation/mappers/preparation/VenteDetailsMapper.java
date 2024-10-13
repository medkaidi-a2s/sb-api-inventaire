package dz.a2s.a2spreparation.mappers.preparation;

import dz.a2s.a2spreparation.dto.preparation.LigneDto;
import dz.a2s.a2spreparation.entities.keys.VenteDetailsId;
import dz.a2s.a2spreparation.entities.views.VenteDetails;

public class VenteDetailsMapper {

    public static LigneDto toLigneDto(VenteDetails ligne) {
        LigneDto dto = new LigneDto(
                ligne.getId(),
                ligne.getVndAnnee(),
                ligne.getVndMedId(),
                ligne.getVndNLot(),
                ligne.getDatePeremption(),
                ligne.getQteCde(),
                ligne.getMontantTtc(),
                ligne.getVndRemarque(),
                ligne.getPrepareFlag(),
                ligne.getQtePrepare(),
                ligne.getMedCommercialName(),
                ligne.getPpa()
        );

        return dto;
    }

}
