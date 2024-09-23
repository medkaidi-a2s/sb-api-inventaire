package dz.a2s.a2spreparation.services;

import dz.a2s.a2spreparation.entities.views.PrpCdeZone;
import dz.a2s.a2spreparation.entities.views.PrpCommande;
import dz.a2s.a2spreparation.entities.views.PrpPrepareControle;

import java.util.List;

public interface AffectationService {

    List<PrpCdeZone> getListCmdZones();

    List<PrpCommande> getListCommande(Integer status);

    List<PrpPrepareControle> getAllPreparateurs();

    List<PrpPrepareControle> getAllControleurs();

    Integer affectCommandePrp(int p_cmp,
                              int p_vnt,
                              int p_stk,
                              int p_type,
                              int p_prp,
                              int p_cnt1,
                              int p_cnt2,
                              String p_user);

}
