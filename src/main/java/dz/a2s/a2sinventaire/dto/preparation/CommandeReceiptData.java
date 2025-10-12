package dz.a2s.a2sinventaire.dto.preparation;

import java.time.LocalDateTime;

public record CommandeReceiptData(
        String reference,
        LocalDateTime date,
        String client,
        String adresse,
        String region,
        String table,
        String zone,
        Integer nbr,
        String user) {
}
