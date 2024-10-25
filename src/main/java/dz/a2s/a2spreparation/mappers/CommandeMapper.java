package dz.a2s.a2spreparation.mappers;

import dz.a2s.a2spreparation.dto.CommandeResponseDto;
import dz.a2s.a2spreparation.dto.affectation.CmdIdDto;
import dz.a2s.a2spreparation.entities.views.Commande;

public class CommandeMapper {

    public static CommandeResponseDto toCommandeResponseDto(Commande commande) {

        CommandeResponseDto dto = new CommandeResponseDto(
                new CmdIdDto(
                        commande.getId().getVntCmpId(),
                        commande.getId().getVntId(),
                        commande.getId().getVntType(),
                        commande.getId().getVntStkCode()
                ),
                commande.getReference(),
                commande.getDate(),
                commande.getClient(),
                commande.getRegion(),
                commande.getNbrLigne(),
                commande.getTotalTtc(),
                commande.getCreerPar(),
                commande.getCreerDate(),
                commande.getPortefeuille(),
                commande.getPreparateurId(),
                commande.getPreparateur(),
                commande.getVerificateurId(),
                commande.getVerificateur(),
                commande.getVerificateurId2(),
                commande.getVerificateur2(),
                commande.getPrepare(),
                commande.getFrigPsycho(),
                commande.getStatut(),
                commande.getReference() + " " + commande.getClient() + " " + commande.getRegion() + " " + commande.getFrigPsycho()
        );

        return dto;
    }

}
