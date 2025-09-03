package dz.a2s.a2spreparation.dto.commande.projections;

import java.math.BigDecimal;
import java.util.Date;

public interface CommandeColisageProjection {

    Integer getTotalRecords();
    Integer getVntCmpId();
    Integer getVntId();
    Integer getVntType();
    String getVntStkCode();
    Date getVntDate();
    String getVntReference();
    BigDecimal getVntTotalTtc();
    String getLibelleTier();
    String getRegion();
    Integer getVntTotalColis();
    Integer getVntBacs();
    Integer getNbrEtiquete();
    Integer getVntPrepFlag();

}
