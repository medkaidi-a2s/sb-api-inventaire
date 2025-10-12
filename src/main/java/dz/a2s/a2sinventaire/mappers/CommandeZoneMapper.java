package dz.a2s.a2sinventaire.mappers;

import dz.a2s.a2sinventaire.dto.CommandeZoneResponseDto;
import dz.a2s.a2sinventaire.dto.affectation.CmdIdDto;
import dz.a2s.a2sinventaire.dto.affectation.CmdZoneIdDto;
import dz.a2s.a2sinventaire.dto.controle.projections.MasterControleProjection;
import dz.a2s.a2sinventaire.dto.controle.response.BonCommandeZoneDto;
import dz.a2s.a2sinventaire.entities.views.CommandeZone;
import org.springframework.stereotype.Component;

@Component
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
                commande.getPriorite(),
                commande.getReference() + " " + commande.getClient() + " " + commande.getRegion() + " " + commande.getFrigPsycho() + " " + commande.getZone(),
                commande.getId().getVntCmpId() + "-" + commande.getId().getVntId() + "-" + commande.getId().getVntType() + "-" + commande.getId().getVntStkCode() + "-" + commande.getId().getVbzZone()
        );

        return dto;
    }

    public BonCommandeZoneDto toBonCommandeZone(MasterControleProjection projection) {
        return BonCommandeZoneDto
                .builder()
                .id(new CmdIdDto(projection.getCmpId(), projection.getId(), projection.getType(), projection.getStkCode()))
                .reference(projection.getReference())
                .date(projection.getVntDate())
                .client(projection.getClient())
                .region(projection.getRegion())
                .totalTtc(projection.getTotalTtc())
                .preparateur(projection.getPreparateur())
                .controleur1(projection.getControleur1())
                .controleur2(projection.getControleur2())
                .statut(projection.getStatut())
                .nbrLigne(projection.getNbrLigne())
                .nbrLigneValid(projection.getNbrLigneValid())
                .key(projection.getCmpId() + "-" + projection.getId() + "-" + projection.getType() + "-" + projection.getStkCode())
                .build();
    }

}
