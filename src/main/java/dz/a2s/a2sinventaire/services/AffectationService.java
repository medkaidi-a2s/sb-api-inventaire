package dz.a2s.a2sinventaire.services;

import dz.a2s.a2sinventaire.dto.CommandeResponseDto;
import dz.a2s.a2sinventaire.dto.CommandeZoneResponseDto;
import dz.a2s.a2sinventaire.dto.affectation.*;
import dz.a2s.a2sinventaire.entities.views.*;

import java.util.List;

public interface AffectationService {

    List<CommandeZoneResponseDto> getListCmdZones(String date);

    List<CommandeResponseDto> getListCmd(String date);

    List<CommandeResponseDto> getListCmdAssigned(String date);

    List<PrpCmdPrlvDto> getListCmdPrlv(String date);

    List<AffCmdPrlvDto> getListCmdPrlvAssigned(String date);

    List<PrpPrepareControle> getAllPreparateurs();

    List<PrpPrepareControle> getAllControleurs();

    AffectCmdResultDto affectCmdPrp(int p_cmp,
                                    int p_vnt,
                                    String p_stk,
                                    String p_type,
                                    int p_prp,
                                    int p_cnt1,
                                    int p_cnt2,
                                    String p_user,
                                    String reference);

    PrpCdePrepCont editAffectCmdPrp(int p_cmp,
                                        int p_vnt,
                                        String p_stk,
                                        String p_type,
                                        int p_prp,
                                        int p_cnt1,
                                        int p_cnt2) throws Exception;

    AffectCmdResultDto affectCmdPrpPrlv(
            int p_cmp,
            int p_slt_id,
            String p_slt_type,
            int p_slt_annee,
            int p_prp,
            int p_cnt1,
            int p_cnt2,
            String p_user,
            String reference
    );

    PrpCdePrlvPrepCont editAffectCmdPrpPrlv(
            int p_cmp,
            int p_slt_id,
            String p_slt_type,
            int p_slt_annee,
            int p_prp,
            int p_cnt1,
            int p_cnt2
    ) throws Exception;

    PrpCdePrepCont getPrepCont(
            Integer vntId,
            String vntType,
            String vntStkCode
    );

    PrpCdePrlvPrepCont getPrepContPrlv(
      Integer id,
      String type,
      Integer annee
    );

}
