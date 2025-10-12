package dz.a2s.a2sinventaire.mappers.preparation;

import dz.a2s.a2sinventaire.dto.preparation.LignePrlvDto;
import dz.a2s.a2sinventaire.entities.views.VentePrlvDetails;

public class VentePrlvDetailsMapper {

    public static LignePrlvDto toLignePrlvDto(VentePrlvDetails ligne) {

        LignePrlvDto dto = new LignePrlvDto(
                ligne.getId(),
                ligne.getVntId(),
                ligne.getVntType(),
                ligne.getStkCode(),
                ligne.getVndNo(),
                ligne.getMedId(),
                ligne.getPrdId(),
                ligne.getCommercialName(),
                ligne.getRemarque(),
                ligne.getVntReference(),
                ligne.getPrdNLot(),
                ligne.getDatePeremption(),
                ligne.getPpa(),
                ligne.getQteCde(),
                ligne.getQtePrlv(),
                ligne.getEmplacement(),
                ligne.getId().getCmpId() + "-" + ligne.getId().getId() + "-" + ligne.getId().getType() + "-" + ligne.getId().getAnnee() + "-" + ligne.getVntId() + "-" + ligne.getVntType() + "-" + ligne.getStkCode() + "-" + ligne.getVndNo() + "-" + ligne.getMedId() + "-" + ligne.getPrdId()
        );

        return dto;
    }

}
