package dz.a2s.a2spreparation.mappers;

import dz.a2s.a2spreparation.dto.CommandeResponseDto;
import dz.a2s.a2spreparation.dto.CommandeZoneResponseDto;
import dz.a2s.a2spreparation.dto.affectation.CmdIdDto;
import dz.a2s.a2spreparation.dto.affectation.CmdZoneIdDto;
import dz.a2s.a2spreparation.entities.views.Commande;
import dz.a2s.a2spreparation.entities.views.CommandeZone;

public class CommandeZoneMapper {

    public static CommandeZoneResponseDto toCommandeZoneResponseDto(CommandeZone commande) {

        CommandeZoneResponseDto dto = new CommandeZoneResponseDto(
                new CmdZoneIdDto(
                        commande.getId().getVntCmpId(),
                        commande.getId().getVntId(),
                        commande.getId().getVntType(),
                        commande.getId().getVntStkCode(),
                        commande.getId().getVbzZone()
                ),
                commande.getReference(),
                commande.getDate(),
                commande.getClient(),
                commande.getRegion(),
                commande.getNbrLigne(),
                commande.getNbrLigneValid(),
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
                commande.getZone(),
                commande.getReference() + " " + commande.getClient() + " " + commande.getRegion() + " " + commande.getFrigPsycho()
        );

        return dto;
    }

}
