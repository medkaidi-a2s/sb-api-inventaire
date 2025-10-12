package dz.a2s.a2sinventaire.dto.preparation;

import java.time.LocalDateTime;

public interface CommandeReceiptProjection {

    String getVntReference();
    LocalDateTime getVntDate();
    String getTerNom();
    String getTerAdresse();
    String getTerRegionLib();
    String getXtable();
    String getZone();
    Integer getNbr();

}
