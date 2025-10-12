package dz.a2s.a2sinventaire.mappers.preparation;

import dz.a2s.a2sinventaire.dto.affectation.CmdIdDto;
import dz.a2s.a2sinventaire.dto.preparation.PrpCdeUsrCodeDto;
import dz.a2s.a2sinventaire.entities.views.PrpCdeUsrCode;

public class PrpCdeUsrCodeMapper {

    public static PrpCdeUsrCodeDto toPrpCdeUsrCodeDto(PrpCdeUsrCode commande) {

        CmdIdDto id = new CmdIdDto(
                commande.getId().getVntCmpId(),
                commande.getId().getVntId(),
                commande.getId().getVntType(),
                commande.getId().getVntStkCode()
        );

        PrpCdeUsrCodeDto prpCdeUsrCodeDto = new PrpCdeUsrCodeDto(
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
                commande.getPreparateurCodeUser(),
                commande.getVerificateur(),
                commande.getVerificateurCodeUser(),
                commande.getVerificateur2(),
                commande.getVerificateur2CodeUser(),
                commande.getPrepare(),
                commande.getFrigPsycho(),
                commande.getStatut()
        );

        return prpCdeUsrCodeDto;
    }

}
