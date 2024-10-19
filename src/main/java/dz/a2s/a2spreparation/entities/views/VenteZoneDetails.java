package dz.a2s.a2spreparation.entities.views;

import dz.a2s.a2spreparation.entities.keys.VenteDetailsId;
import dz.a2s.a2spreparation.entities.keys.VenteZoneDetailsId;
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
@Table(name = "PRP_ZONE_DETAILS")
public class VenteZoneDetails {

    @Id
    @EmbeddedId
    private VenteZoneDetailsId id;

    @Column(name = "VND_ANNEE")
    private Integer vndAnnee;

    @Column(name = "VND_MED_ID")
    private Integer vndMedId;

    @Column(name = "VND_NLOT")
    private String vndNLot;

    @Column(name = "VND_DATE_PEREMPTION")
    private Date datePeremption;

    @Column(name = "VND_QTE")
    private Integer qte;

    @Column(name = "VND_QTE_UG")
    private Integer qteUg;

    @Column(name = "VND_MONT_TTC")
    private BigDecimal montantTtc;

    @Column(name = "VND_REMARQUE")
    private String vndRemarque;

    @Column(name = "VND_CREER_PAR")
    private String creerPar;

    @Column(name = "VND_PREPARE_FLAG")
    private Boolean prepareFlag;

    @Column(name = "VND_QTE_PREPARE")
    private Integer qtePrepare;

    @Column(name = "VND_PREPARE_MOTIF")
    private String prepareMotif;

    @Column(name = "VND_CONTROLE_FLAG1")
    private Boolean controlFlag;

    @Column(name = "VND_CONTROLE_QTE1")
    private Integer qteControle;

    @Column(name = "VND_CONTROLE_MOTIF1")
    private String controlMotif;

    @Column(name = "VND_CONTROLE_FLAG2")
    private Boolean controlFlag2;

    @Column(name = "VND_CONTROLE_QTE2")
    private Integer qteControle2;

    @Column(name = "VND_CONTROLE_MOTIF2")
    private String controlMotif2;

    @Column(name = "MED_COMMERCIAL_NAME")
    private String medCommercialName;

    @Column(name = "VND_PRIX_PPA")
    private BigDecimal ppa;

}
