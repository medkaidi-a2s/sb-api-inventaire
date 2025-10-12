package dz.a2s.a2sinventaire.dto.preparation.response;

import java.math.BigDecimal;
import java.util.Date;

public record ProductLotDto(
        Integer cmpId,
        Integer id,
        Integer medId,
        String stkCode,
        String nlot,
        Date datePeremption,
        Integer qte,
        BigDecimal prixPh,
        BigDecimal prixPpa,
        BigDecimal prixShp,
        Integer ugVente
) {
}
