package dz.a2s.a2spreparation.services;

import dz.a2s.a2spreparation.dto.affectation.*;
import dz.a2s.a2spreparation.entities.views.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AffectationService {

    List<AffCmdDto> getListCmdZones();

    List<PrpCmdDto> getListCmd(String date);

    List<AffCmdDto> getListCmdAssigned(String date);

    List<PrpCmdPrlvDto> getListCmdPrlv(String date);

    List<AffCmdPrlvDto> getListCmdPrlvAssigned(String date);

    List<PrpPrepareControle> getAllPreparateurs();

    List<PrpPrepareControle> getAllControleurs();

    AffectCmdResultDto affectCmdPrp(int p_cmp,
                                    int p_vnt,
                                    int p_stk,
                                    int p_type,
                                    int p_prp,
                                    int p_cnt1,
                                    int p_cnt2,
                                    String p_user,
                                    String reference);

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
