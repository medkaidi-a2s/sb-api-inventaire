package dz.a2s.a2spreparation.entities.views;

import dz.a2s.a2spreparation.entities.keys.VentePrlvDetailsId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Immutable
@Table(name = "PRP_PRELEV_DETAILS")
public class VentePrlvDetails {

    @Id
    @EmbeddedId
    private VentePrlvDetailsId id;

    @Column(name = "SLD_VNT_ID")
    private Integer vntId;

    @Column(name = "SLD_VNT_TYPE")
    private String vntType;

    @Column(name = "SLD_STK_CODE")
    private String stkCode;

    @Column(name = "SLD_VND_NO")
    private Integer vndNo;

    @Column(name = "SLD_MED_ID")
    private Integer medId;

    @Column(name = "PRD_ID")
    private Integer prdId;

    @Column(name = "MED_COMMERCIAL_NAME")
    private String commercialName;

    @Column(name = "SLD_REMARQUE")
    private String remarque;

    @Column(name = "VNT_REFERENCE")
    private String vntReference;

    @Column(name = "PRD_NLOT")
    private String prdNLot;

    @Column(name = "PRD_DATE_PEREMPTION")
    private Date datePeremption;

    @Column(name = "PRD_PRIX_PPA")
    private BigDecimal ppa;

    @Column(name = "SLD_QTE_PLV")
    private Integer qtePrlv;

    @Column(name = "QTE_CDE")
    private Integer qteCde;

    @Column(name = "EMPLACEMENT")
    private String emplacement;

}
