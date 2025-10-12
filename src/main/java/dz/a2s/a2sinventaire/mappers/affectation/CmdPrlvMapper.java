package dz.a2s.a2sinventaire.mappers.affectation;

import dz.a2s.a2sinventaire.dto.affectation.AffCmdPrlvDto;
import dz.a2s.a2sinventaire.dto.affectation.CmdPrlvIdDto;
import dz.a2s.a2sinventaire.dto.affectation.PrpCmdPrlvDto;
import dz.a2s.a2sinventaire.entities.views.PrpCdePrlv;

public class CmdPrlvMapper {

    public static PrpCmdPrlvDto toPrpCommand(PrpCdePrlv commande) {
        CmdPrlvIdDto id = new CmdPrlvIdDto(
                commande.getId().getSltCmpId(),
                commande.getId().getSltId(),
                commande.getId().getSltType(),
                commande.getId().getSltAnnee()
        );

        PrpCmdPrlvDto dto = new PrpCmdPrlvDto(
                id,
                commande.getReference(),
                commande.getSltDate(),
                commande.getZone(),
                commande.getClient(),
                commande.getRegion(),
                commande.getNbrLigne(),
                commande.getStatut(),
                commande.getCreerUser(),
                commande.getId().getSltCmpId() + "-" + commande.getId().getSltId() + "-" + commande.getId().getSltType() + "-" + commande.getId().getSltAnnee()
        );

        return dto;
    }

    public static AffCmdPrlvDto toAffCommand(PrpCdePrlv commande) {
        CmdPrlvIdDto id = new CmdPrlvIdDto(
                commande.getId().getSltCmpId(),
                commande.getId().getSltId(),
                commande.getId().getSltType(),
                commande.getId().getSltAnnee()
        );

        AffCmdPrlvDto dto = new AffCmdPrlvDto(
                id,
                commande.getReference(),
                commande.getSltDate(),
                commande.getZone(),
                commande.getClient(),
                commande.getRegion(),
                commande.getNbrLigne(),
                commande.getPreparateur(),
                commande.getVerificateur(),
                commande.getVerificateur2(),
                commande.getStatut(),
                commande.getCreerUser(),
                commande.getId().getSltCmpId() + "-" + commande.getId().getSltId() + "-" + commande.getId().getSltType() + "-" + commande.getId().getSltAnnee()
        );

        return dto;
    }

}
