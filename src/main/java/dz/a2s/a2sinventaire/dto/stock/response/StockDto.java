package dz.a2s.a2sinventaire.dto.stock.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

public record StockDto(
        Integer cmpId,
        Integer id,
        Integer medId,
        String stkCode,
        String designation,
        String fournisseur,
        String lot,
        Date datePeremption,
        BigDecimal ppa,
        Integer quantite,
        BigDecimal prixVente,
        BigDecimal prixGros,
        BigDecimal shp,
        Integer ugVente,
        Integer etat,
        LocalDateTime dateArrivage,
        Integer colisage,
        String key
) {
}
