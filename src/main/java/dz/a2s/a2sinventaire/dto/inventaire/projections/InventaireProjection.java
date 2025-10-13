package dz.a2s.a2sinventaire.dto.inventaire.projections;

import java.util.Date;

public interface InventaireProjection {

    Integer getCmpId();
    Integer getId();
    String getDepot();
    Date getInvDate();
    String getRemarque();

}
