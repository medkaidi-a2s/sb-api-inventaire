package dz.a2s.a2spreparation.mappers;

import dz.a2s.a2spreparation.dto.affectation.AffCmdDto;
import dz.a2s.a2spreparation.dto.affectation.CmdIdDto;
import dz.a2s.a2spreparation.dto.affectation.PrpCmdDto;
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
                commande.getStatut()
        );

        return dto;
    }

}
