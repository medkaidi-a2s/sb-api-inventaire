package dz.a2s.a2spreparation.mappers.preparation;

import dz.a2s.a2spreparation.dto.preparation.LigneDto;
import dz.a2s.a2spreparation.dto.preparation.LigneZoneDto;
import dz.a2s.a2spreparation.entities.keys.VenteDetailsId;
import dz.a2s.a2spreparation.entities.views.VenteDetails;
import dz.a2s.a2spreparation.entities.views.VenteZoneDetails;

public class VenteDetailsMapper {

    public static LigneDto toLigneDto(VenteDetails ligne) {
        LigneDto dto = new LigneDto(
                ligne.getId(),
                ligne.getVndAnnee(),
                ligne.getVndMedId(),
                ligne.getVndNLot(),
                ligne.getPrdId(),
                ligne.getDatePeremption(),
                ligne.getQte() + ligne.getQteUg(),
                ligne.getMontantTtc(),
                ligne.getVndRemarque(),
                ligne.getPrepareFlag(),
                ligne.getQtePrepare(),
                ligne.getPrepareMotif(),
                ligne.getControlFlag(),
                ligne.getQteControle(),
                ligne.getControlMotif(),
                ligne.getControlFlag2(),
                ligne.getQteControle2(),
                ligne.getControlMotif2(),
                ligne.getMedCommercialName(),
                ligne.getPpa()
        );

        return dto;
    }

    public static LigneZoneDto toLigneZoneDto(VenteZoneDetails ligne) {
        LigneZoneDto dto = new LigneZoneDto(
                ligne.getId(),
                ligne.getVndAnnee(),
                ligne.getVndMedId(),
                ligne.getVndNLot(),
                ligne.getPrdId(),
                ligne.getDatePeremption(),
                ligne.getQte() + ligne.getQteUg(),
                ligne.getMontantTtc(),
                ligne.getVndRemarque(),
                ligne.getPrepareFlag(),
                ligne.getQtePrepare(),
                ligne.getPrepareMotif(),
                ligne.getControlFlag(),
                ligne.getQteControle(),
                ligne.getControlMotif(),
                ligne.getControlFlag2(),
                ligne.getQteControle2(),
                ligne.getControlMotif2(),
                ligne.getMedCommercialName(),
                ligne.getPpa()
        );

        return dto;
    }

}
