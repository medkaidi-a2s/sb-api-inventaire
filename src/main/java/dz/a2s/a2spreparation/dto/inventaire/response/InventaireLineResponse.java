package dz.a2s.a2spreparation.dto.inventaire.response;

import java.math.BigDecimal;
import java.util.Date;

public record InventaireLineResponse(
        Integer cmpId,
        Integer invId,
        String depot,
        Integer numProduit,
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
        String zoneProduit,
        String motifSaisie,
        Integer qteSaisie,
        String key
) {
}
