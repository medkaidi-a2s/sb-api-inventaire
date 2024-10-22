package dz.a2s.a2spreparation.entities.views;

import dz.a2s.a2spreparation.entities.keys.VenteZoneId;
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
@Table(name = "PRP_LISTE_CDE_ZONES")
public class CommandeZone {

    @Id
    @EmbeddedId
    private VenteZoneId id;

    @Column(name = "REFERENCE")
    private String reference;

    @Column(name = "VNT_DATE")
    private Date date;

    @Column(name = "CLIENT")
    private String client;

    @Column(name = "REGION")
    private String region;

    @Column(name = "NBR_LIGNE")
    private Integer nbrLigne;

    @Column(name = "TOTAL_TTC")
    private BigDecimal totalTtc;

    @Column(name = "CREER_PAR")
    private String creerPar;

    @Column(name = "CREER_DATE")
    private Date creerDate;

    @Column(name = "PORTEFEUILLE")
    private String portefeuille;

    @Column(name = "VBZ_PREPAR_ID")
    private Integer preparateurId;

    @Column(name = "PREPARATEUR")
    private String preparateur;

    @Column(name = "VBZ_VERIF_ID")
    private Integer verificateurId;

    @Column(name = "VERIFICATEUR")
    private String verificateur;

    @Column(name = "VBZ_VERIF_ID2")
    private Integer verificateurId2;

    @Column(name = "VERIFICATEUR2")
    private String verificateur2;

    @Column(name = "PREPARE")
    private String prepare;

    @Column(name = "FRIG_PSYCHO")
    private String frigPsycho;

    @Column(name = "STATUT")
    private String statut;

    @Column(name = "ZONE")
    private String zone;

}
