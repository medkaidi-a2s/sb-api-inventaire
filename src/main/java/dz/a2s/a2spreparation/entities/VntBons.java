package dz.a2s.a2spreparation.entities;

import dz.a2s.a2spreparation.entities.keys.VenteId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table
@Entity(name = "VNT_BONS")
public class VntBons {

    @EmbeddedId
    @Id
    private VenteId id;

    @Column(name = "VNT_REFERENCE")
    private String vntReference;

    @Column(name ="VNT_DATE")
    private Date vntDate;

    @Column(name = "VNT_NBR_LIGNE")
    private Integer vntNbrLigne;

    @Column(name = "VNT_TOTAL_TTC")
    private BigDecimal vntTotalTtc;

    @Column(name = "VNT_CREER_PAR")
    private String vntCreerPar;

    @Column(name = "VNT_CREER_DATE")
    private Date vntCreerDate;

}
