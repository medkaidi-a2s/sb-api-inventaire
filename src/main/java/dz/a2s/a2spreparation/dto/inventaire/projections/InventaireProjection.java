package dz.a2s.a2spreparation.dto.inventaire.projections;

import java.util.Date;

public interface InventaireProjection {

    Integer getCmpId();
    Integer getId();
    Date getInvDate();
    String getRemarque();

}
