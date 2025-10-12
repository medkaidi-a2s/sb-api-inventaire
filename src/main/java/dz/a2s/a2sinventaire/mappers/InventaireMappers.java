package dz.a2s.a2sinventaire.mappers;

import dz.a2s.a2sinventaire.dto.common.ListProjection;
import dz.a2s.a2sinventaire.dto.common.ListResponse;
import dz.a2s.a2sinventaire.dto.inventaire.projections.ComptageAccessProjection;
import dz.a2s.a2sinventaire.dto.inventaire.projections.EcartLineProjection;
import dz.a2s.a2sinventaire.dto.inventaire.projections.InventaireLineProjection;
import dz.a2s.a2sinventaire.dto.inventaire.projections.InventaireProjection;
import dz.a2s.a2sinventaire.dto.inventaire.response.ComptageAccessResponse;
import dz.a2s.a2sinventaire.dto.inventaire.response.EcartLineResponse;
import dz.a2s.a2sinventaire.dto.inventaire.response.InventaireLineResponse;
import dz.a2s.a2sinventaire.entities.Inventaire;

public class InventaireMappers {

    public static Inventaire fromInventaireProjection(InventaireProjection projection) {
        return new Inventaire(
                projection.getCmpId(),
                projection.getId(),
                projection.getInvDate(),
                projection.getRemarque()
        );
    }

    public static ListResponse fromListProjection(ListProjection projection) {
        return new ListResponse(
                projection.getCode(),
                projection.getNom()
        );
    }

    public static ComptageAccessResponse fromComptageAccessProjection(ComptageAccessProjection projection) {
        return new ComptageAccessResponse(
                projection.getComptage1(),
                projection.getComptage2(),
                projection.getComptage3()
        );
    }

    public static InventaireLineResponse fromInventaireLineProjection(InventaireLineProjection projection) {
        return new InventaireLineResponse(
                projection.getSite(),
                projection.getInventaire(),
                projection.getDepot(),
                projection.getNumProduit(),
                projection.getNlotInterne(),
                projection.getNumLigne(),
                projection.getCode(),
                projection.getNomProduit(),
                projection.getNlot(),
                projection.getDatePeremption(),
                projection.getPpa(),
                projection.getShp(),
                projection.getForme(),
                projection.getLabo(),
                projection.getQteStock(),
                projection.getZoneProduit(),
                projection.getMotifSaisie(),
                projection.getQteSaisie(),
                projection.getSite() + "-" + projection.getDepot() + "-" + projection.getCode() + "-" + projection.getNlot() + "-" + projection.getNlotInterne() + "-" + projection.getNumLigne() + "-" + projection.getNumProduit() + "-" + (projection.getZoneProduit() != null ? projection.getZoneProduit() : null)
        );
    }

    public static EcartLineResponse fromEcartLineProjection(EcartLineProjection projection) {
        return new EcartLineResponse(
                projection.getSite(),
                projection.getInvId(),
                projection.getDepot(),
                projection.getMedId(),
                projection.getNlotInterne(),
                projection.getLigne(),
                projection.getDateInventaire(),
                projection.getMedZone(),
                projection.getZone(),
                projection.getStkCode(),
                projection.getCommercialName(),
                projection.getCode(),
                projection.getForme(),
                projection.getNlot(),
                projection.getDatePeremption(),
                projection.getPpa(),
                projection.getShp(),
                projection.getColis(),
                projection.getStock(),
                projection.getFournisseur(),
                projection.getLibelleZone(),
                projection.getComptage3(),
                projection.getComptage1(),
                projection.getComptage2(),
                projection.getSite() + "-" + projection.getDepot() + "-" + projection.getStkCode() + "-" + projection.getMedId() + "-" + projection.getNlotInterne() + "-" + projection.getLigne() + "-" + projection.getZone()
        );
    }
}
