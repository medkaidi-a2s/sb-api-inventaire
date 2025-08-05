package dz.a2s.a2spreparation.dto.stock.projections;

import java.math.BigDecimal;
import java.util.Date;

public interface ProductLotProjection {

    Integer getCmpId();
    Integer getId();
    Integer getMedId();
    String getStkCode();
    String getNlot();
    Date getDatePeremption();
    Integer getQte();
    BigDecimal getPrixPh();
    BigDecimal getPrixPpa();
    BigDecimal getPrixShp();
    Integer getUgVente();

}
