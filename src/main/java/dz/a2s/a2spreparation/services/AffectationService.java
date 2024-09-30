package dz.a2s.a2spreparation.services;

import dz.a2s.a2spreparation.dto.affectation.AffectCmdResultDto;
import dz.a2s.a2spreparation.entities.views.PrpCdePrlv;
import dz.a2s.a2spreparation.entities.views.PrpCdeZone;
import dz.a2s.a2spreparation.entities.views.PrpCommande;
import dz.a2s.a2spreparation.entities.views.PrpPrepareControle;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface AffectationService {

    List<PrpCdeZone> getListCmdZones();

    List<PrpCommande> getListCommande(String date);

    List<PrpCommande> getListCommandeAssigned(String date);

    List<PrpCdePrlv> getListeCommandesPrlv(String date);

    List<PrpPrepareControle> getAllPreparateurs();

    List<PrpPrepareControle> getAllControleurs();

    AffectCmdResultDto affectCommandePrp(int p_cmp,
                                         int p_vnt,
                                         int p_stk,
                                         int p_type,
                                         int p_prp,
                                         int p_cnt1,
                                         int p_cnt2,
                                         String p_user,
                                         String reference);

    AffectCmdResultDto affectCommandePrpPrlv(
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

}
