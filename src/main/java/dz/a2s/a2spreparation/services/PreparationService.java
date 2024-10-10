package dz.a2s.a2spreparation.services;

import dz.a2s.a2spreparation.dto.affectation.PrpCmdPrlvDto;
import dz.a2s.a2spreparation.dto.preparation.PrpCdeUsrCodeDto;
import dz.a2s.a2spreparation.dto.preparation.PrpCmdPrlvUsrCodeDto;
import dz.a2s.a2spreparation.entities.views.PrpCdeUsrCode;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PreparationService {

    List<PrpCdeUsrCodeDto> getCommandes(String date);

    List<PrpCmdPrlvUsrCodeDto> getCommandesPrlv(String date);

    Integer startPreparePrlv(int p_cmp,
                             int p_slt_id,
                             String p_slt_type,
                             int p_slt_annee) throws Exception;

    Integer startPrepareCde(
            int p_vnt_cmp_id,
            int p_vnt_id,
            String p_vnt_type,
            String p_vnt_stk_code
    ) throws Exception;


    PrpCmdPrlvUsrCodeDto getOneCmdPrlv(Integer id, String type, Integer annee);
}
