package dz.a2s.a2spreparation.services;

import dz.a2s.a2spreparation.entities.views.PrpCdeZone;
import dz.a2s.a2spreparation.entities.views.PrpCommande;
import dz.a2s.a2spreparation.entities.views.PrpPrepareControle;

import java.util.List;

public interface AffectationService {

    List<PrpCdeZone> getListCmdZones();

    List<PrpCommande> getListCommande();

    List<PrpPrepareControle> getAllPreparateurs();

    List<PrpPrepareControle> getAllControleurs();

}
