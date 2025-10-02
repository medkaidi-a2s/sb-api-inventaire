package dz.a2s.a2spreparation.dto.inventaire.response;

import java.math.BigDecimal;
import java.util.Date;

public record EcartLineResponse(
        Integer site,
        Integer invId,
        String stkCode,
        Integer medId,
        Integer detailId,
        Integer ligne,
        Date dateInventaire,
        String medZone,
        Integer zone,
        String prdStkCode,
        String commercialName,
        String nlot,
        Date datePeremption,
        BigDecimal ppa,
        BigDecimal shp,
        BigDecimal colis,
        Integer stock,
        String fournisseur,
        String libelleZone,
        Integer comptage3,
        Integer comptage1,
        Integer comptage2
) {
}
