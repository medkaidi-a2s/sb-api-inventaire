package dz.a2s.a2spreparation.services;

import dz.a2s.a2spreparation.dto.affectation.PrpCmdPrlvDto;
import dz.a2s.a2spreparation.dto.preparation.PrpCdeUsrCodeDto;
import dz.a2s.a2spreparation.dto.preparation.PrpCmdPrlvUsrCodeDto;
import dz.a2s.a2spreparation.entities.views.PrpCdeUsrCode;

import java.util.List;

public interface PreparationService {

    List<PrpCdeUsrCodeDto> getCommandes(String date);

    List<PrpCmdPrlvUsrCodeDto> getCommandesPrlv(String date);

}
