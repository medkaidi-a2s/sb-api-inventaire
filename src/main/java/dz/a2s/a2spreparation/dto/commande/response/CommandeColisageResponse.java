package dz.a2s.a2spreparation.dto.commande.response;

import java.math.BigDecimal;
import java.util.Date;

public record CommandeColisageResponse(
        Integer cmpId,
        Integer id,
        Integer type,
        String stkCode,
        Date date,
        String reference,
        BigDecimal totalTtc,
        String client,
        String region,
        Integer totalColis,
        Integer bacs,
        Integer nbrEtiquettes,
        Boolean prepFlag,
        String key
) {
}
