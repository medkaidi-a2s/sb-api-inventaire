package dz.a2s.a2spreparation.dto.preparation;

import dz.a2s.a2spreparation.entities.keys.VenteDetailsId;
import dz.a2s.a2spreparation.entities.keys.VentePrlvDetailsId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LigneDto {

    private VenteDetailsId id;
    private Integer vndAnnee;
    private Integer medId;
    private String prdNLot;
    private Date datePeremption;
    private Integer qteCommande;
    private BigDecimal montantTtc;
    private String remarque;
    private Boolean prepareFlag;
    private Integer qtePrepare;
    private Integer prepareMotif;
    private Boolean controlFlag;
    private Integer qteControl;
    private Integer controleMotif;
    private Boolean controlFlag2;
    private Integer qteControl2;
    private Integer controleMotif2;
    private String commercialName;
    private BigDecimal ppa;

}
