package dz.a2s.a2spreparation.mappers;

import dz.a2s.a2spreparation.dto.CommandeResponseDto;
import dz.a2s.a2spreparation.dto.affectation.CmdIdDto;
import dz.a2s.a2spreparation.dto.commande.projections.CommandeColisageProjection;
import dz.a2s.a2spreparation.dto.commande.response.CommandeColisageResponse;
import dz.a2s.a2spreparation.dto.preparation.CommandeReceiptData;
import dz.a2s.a2spreparation.dto.preparation.CommandeReceiptProjection;
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
                commande.getPriorite(),
                commande.getReference() + " " + commande.getClient() + " " + commande.getRegion() + " " + commande.getFrigPsycho(),
                commande.getId().getVntCmpId() + "-" + commande.getId().getVntId() + "-" + commande.getId().getVntType() + "-" + commande.getId().getVntStkCode()
        );

        return dto;
    }

    public static CommandeReceiptData toReceiptData(CommandeReceiptProjection projection) {
        return new CommandeReceiptData(
                projection.getVntReference(),
                projection.getVntDate(),
                projection.getTerNom(),
                projection.getTerAdresse(),
                projection.getTerRegionLib(),
                projection.getXtable(),
                projection.getZone() != null ? projection.getZone() : null,
                projection.getNbr() != null ? projection.getNbr() : 0
        );
    }

    public static CommandeColisageResponse toCommandeColisageResponse(CommandeColisageProjection projection) {
        return new CommandeColisageResponse(
                projection.getVntCmpId(),
                projection.getVntId(),
                projection.getVntType(),
                projection.getVntStkCode(),
                projection.getVntDate(),
                projection.getVntReference(),
                projection.getVntTotalTtc(),
                projection.getLibelleTier(),
                projection.getRegion(),
                projection.getVntTotalColis(),
                projection.getVntBacs(),
                projection.getNbrEtiquete(),
                projection.getVntPrepFlag() == 1,
                projection.getVntCmpId() + "-" + projection.getVntId() + "-" + projection.getVntType() + "-" + projection.getVntStkCode() + "-" + projection.getVntReference()
        );
    }

}
