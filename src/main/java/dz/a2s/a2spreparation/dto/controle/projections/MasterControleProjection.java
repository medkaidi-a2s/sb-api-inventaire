package dz.a2s.a2spreparation.dto.controle.projections;

import java.math.BigDecimal;
import java.util.Date;

public interface MasterControleProjection {

    Integer getCmpId();
    Integer getId();
    String getType();
    String getStkCode();
    String getReference();
    Date getVntDate();
    String getRegion();
    String getClient();
    BigDecimal getTotalTtc();
    String getPreparateur();
    String getControleur1();
    String getControleur2();
    String getStatut();
    Integer getNbrLigne();
    Integer getNbrLigneValid();

}
