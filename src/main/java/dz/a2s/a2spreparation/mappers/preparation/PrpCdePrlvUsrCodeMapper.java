package dz.a2s.a2spreparation.mappers.preparation;

import dz.a2s.a2spreparation.dto.affectation.CmdPrlvIdDto;
import dz.a2s.a2spreparation.dto.preparation.PrpCmdPrlvUsrCodeDto;
import dz.a2s.a2spreparation.entities.views.PrpCdePrlvUsrCode;

public class PrpCdePrlvUsrCodeMapper {

    public static PrpCmdPrlvUsrCodeDto toPrpCmdPrlvUsrCodeDto(PrpCdePrlvUsrCode commande) {
        CmdPrlvIdDto cmdPrlvIdDto = new CmdPrlvIdDto(
            commande.getId().getSltCmpId(),
            commande.getId().getSltId(),
            commande.getId().getSltType(),
            commande.getId().getSltAnnee()
        );


        PrpCmdPrlvUsrCodeDto prpCmdPrlvUsrCodeDto = new PrpCmdPrlvUsrCodeDto(
                cmdPrlvIdDto,
                commande.getReference(),
                commande.getSltDate(),
                commande.getZone(),
                commande.getClient(),
                commande.getRegion(),
                commande.getNbrLigne(),
                commande.getPreparateurId(),
                commande.getPreparateurCode(),
                commande.getPreparateur(),
                commande.getVerificateurId1(),
                commande.getVerificateur1Code(),
                commande.getVerificateur(),
                commande.getVerificateurId2(),
                commande.getVerificateur2Code(),
                commande.getVerificateur2(),
                commande.getStatut(),
                commande.getCreerUser(),
                commande.getId().getSltCmpId() + "-" + commande.getId().getSltId() + "-" + commande.getId().getSltType() + "-" + commande.getId().getSltAnnee()
        );

        return prpCmdPrlvUsrCodeDto;
    }

}
