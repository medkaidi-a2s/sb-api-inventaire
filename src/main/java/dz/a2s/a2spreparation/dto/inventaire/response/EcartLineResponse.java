package dz.a2s.a2spreparation.dto.inventaire.response;

import java.math.BigDecimal;
import java.util.Date;

public record EcartLineResponse(
        Integer cmpId,
        Integer invId,
        String depot,
        Integer medId,
        Integer nlotInterne,
        Integer ligne,
        Date dateInventaire,
        String libelleZone,
        Integer zone,
        String stkCode,
        String nomProduit,
        String code,
        String forme,
        String nlot,
        Date datePeremption,
        BigDecimal ppa,
        BigDecimal shp,
        BigDecimal colis,
        Integer qteStock,
        String labo,
        String zoneProduit,
        Integer comptage3,
        Integer comptage1,
        Integer comptage2,
        String key
) {
}
