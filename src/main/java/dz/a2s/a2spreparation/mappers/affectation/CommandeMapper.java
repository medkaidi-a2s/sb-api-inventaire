package dz.a2s.a2spreparation.mappers.affectation;

import dz.a2s.a2spreparation.dto.affectation.*;
import dz.a2s.a2spreparation.entities.views.PrpCdeZone;
import dz.a2s.a2spreparation.entities.views.PrpCommande;

public class CommandeMapper {

    public static PrpCmdDto toPrpCmdDto(PrpCommande commande) {
        CmdIdDto id = new CmdIdDto(
                commande.getId().getVntCmpId(),
                commande.getId().getVntId(),
                commande.getId().getVntType(),
                commande.getId().getVntStkCode()
        );

        PrpCmdDto dto = new PrpCmdDto(
                id,
                commande.getReference(),
                commande.getVntDate(),
                commande.getClient(),
                commande.getRegion(),
                commande.getNbrLigne(),
                commande.getTotalTtc(),
                commande.getCreerPar(),
                commande.getCreerDate(),
                commande.getPortefeuille(),
                commande.getFrigPsycho(),
                commande.getStatut()
        );

        return dto;
    }

    public static AffCmdDto toAffCmdDto(PrpCommande commande) {
        CmdIdDto id = new CmdIdDto(
                commande.getId().getVntCmpId(),
                commande.getId().getVntId(),
                commande.getId().getVntType(),
                commande.getId().getVntStkCode()
        );

        AffCmdDto dto = new AffCmdDto(
                id,
                commande.getReference(),
                commande.getVntDate(),
                commande.getClient(),
                commande.getRegion(),
                commande.getNbrLigne(),
                commande.getTotalTtc(),
                commande.getCreerPar(),
                commande.getCreerDate(),
                commande.getPortefeuille(),
                commande.getPreparateur(),
                commande.getVerificateur(),
                commande.getVerificateur2(),
                commande.getPrepare(),
                commande.getFrigPsycho(),
                commande.getStatut(),
                null
        );

        return dto;
    }

    public static AffZoneDto toAffZoneCmdDto(PrpCdeZone commande) {
        CmdZoneIdDto id = new CmdZoneIdDto(
                commande.getId().getVntCmpId(),
                commande.getId().getVntId(),
                commande.getId().getVntType(),
                commande.getId().getVntStkCode(),
                commande.getId().getVbzZone()
        );

        AffZoneDto dto = new AffZoneDto(
                id,
                commande.getReference(),
                commande.getVntDate(),
                commande.getClient(),
                commande.getRegion(),
                commande.getNbrLigne(),
                commande.getTotalTtc(),
                commande.getCreerPar(),
                commande.getCreerDate(),
                commande.getPortefeuille(),
                commande.getPreparateur(),
                commande.getVerificateur(),
                commande.getVerificateur2(),
                commande.getPrepare(),
                commande.getFrigPsycho(),
                commande.getStatut(),
                commande.getZone()
        );

        return dto;
    }

}
