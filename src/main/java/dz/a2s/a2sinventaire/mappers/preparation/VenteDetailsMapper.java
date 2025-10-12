package dz.a2s.a2sinventaire.mappers.preparation;

import dz.a2s.a2sinventaire.dto.preparation.LigneDto;
import dz.a2s.a2sinventaire.dto.preparation.LigneZoneDto;
import dz.a2s.a2sinventaire.entities.views.VenteDetails;
import dz.a2s.a2sinventaire.entities.views.VenteZoneDetails;

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
                ligne.getPpa(),
                ligne.getId().getCmpId() + "-" + ligne.getId().getId() + "-" + ligne.getId().getType() + "-" + ligne.getId().getStkCode() + "-" + ligne.getId().getNo() + "-"  + ligne.getVndMedId() + "-" + ligne.getVndNLot() + "-" + ligne.getPrdId()
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
                ligne.getPpa(),
                ligne.getId().getCmpId() + "-" + ligne.getId().getId() + "-" + ligne.getId().getType() + "-" + ligne.getId().getStkCode() + "-" + ligne.getId().getNo() + "-" + ligne.getId().getZone() + "-"  + ligne.getVndMedId() + "-" + ligne.getVndNLot() + "-" + ligne.getPrdId()
        );

        return dto;
    }

}
