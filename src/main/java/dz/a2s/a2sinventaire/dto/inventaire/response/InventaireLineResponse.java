package dz.a2s.a2sinventaire.dto.inventaire.response;

import java.math.BigDecimal;
import java.util.Date;

public record InventaireLineResponse(
        Integer cmpId,
        Integer invId,
        String depot,
        Integer medId,
        Integer nlotInterne,
        Integer numLigne,
        String code,
        String nomProduit,
        String nlot,
        Date datePeremption,
        BigDecimal ppa,
        BigDecimal shp,
        String forme,
        String labo,
        Integer qteStock,
        String zoneProduit,
        String motifSaisie,
        Integer qteSaisie,
        String key
) {
}
