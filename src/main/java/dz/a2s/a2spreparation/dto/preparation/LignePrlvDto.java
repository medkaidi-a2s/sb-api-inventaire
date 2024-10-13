package dz.a2s.a2spreparation.dto.preparation;

import dz.a2s.a2spreparation.entities.keys.VentePrlvDetailsId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LignePrlvDto {

    private VentePrlvDetailsId id;
    private Integer vntId;
    private String vntType;
    private String stkCode;
    private Integer vndNo;
    private Integer medId;
    private Integer prdId;
    private String commercialName;
    private String remarque;
    private String vntReference;
    private String prdNLot;
    private Date datePeremption;
    private BigDecimal ppa;
    private Integer qteCommande;
    private Integer qtePrepare;
    private String emplacement;

}
