package dz.a2s.a2spreparation.dto.inventaire.projections;

import java.math.BigDecimal;
import java.util.Date;

public interface EcartLineProjection {

    Integer getTotalRecords();
    Integer getSite();
    Integer getInvId();
    String getStkCode();
    Integer getMedId();
    Integer getDetailId();
    Integer getLigne();
    Date getDateInventaire();
    String getMedZone();
    Integer getZone();
    String getPrdStkCode();
    String getCommercialName();
    String getNlot();
    Date getDatePeremption();
    BigDecimal getPpa();
    BigDecimal getShp();
    BigDecimal getColis();
    Integer getStock();
    String getFournisseur();
    String getLibelleZone();
    Integer getComptage3();
    Integer getComptage1();
    Integer getComptage2();

}
