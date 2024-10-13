package dz.a2s.a2spreparation.mappers.preparation;

import dz.a2s.a2spreparation.dto.preparation.LignePrlvDto;
import dz.a2s.a2spreparation.entities.views.VentePrlvDetails;

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
                ligne.getEmplacement()
        );

        return dto;
    }

}
