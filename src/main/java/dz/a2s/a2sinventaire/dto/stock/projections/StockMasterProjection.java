package dz.a2s.a2sinventaire.dto.stock.projections;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

public interface StockMasterProjection {

    Integer getTotalCount();
    Integer getCmpId();
    Integer getId();
    Integer getMedId();
    String getStkCode();
    String getDesignation();
    String getFournisseur();
    String getLot();
    Date getDatePeremption();
    BigDecimal getPpa();
    Integer getQuantite();
    BigDecimal getPrixVente();
    BigDecimal getPrixGros();
    BigDecimal getShp();
    Integer getUgVente();
    Integer getEtat();
    LocalDateTime getDateArrivage();
    Integer getColisage();
    Integer getRowNum();

}
