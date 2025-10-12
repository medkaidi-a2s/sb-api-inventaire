package dz.a2s.a2sinventaire.dto.inventaire.projections;

import java.math.BigDecimal;
import java.util.Date;

public interface InventaireLineProjection {

    Integer getTotalRecords();
    Integer getSite();
    Integer getInventaire();
    String getDepot();
    Integer getNumProduit();
    Integer getNlotInterne();
    Integer getNumLigne();
    String getCode();
    String getNomProduit();
    String getNlot();
    Date getDatePeremption();
    BigDecimal getPpa();
    BigDecimal getShp();
    String getForme();
    String getLabo();
    Integer getQteStock();
    String getZoneProduit();
    String getMotifSaisie();
    Integer getQteSaisie();

}
