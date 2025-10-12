package dz.a2s.a2sinventaire.entities.views;

import dz.a2s.a2sinventaire.entities.keys.VenteId;
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
@Table(name = "PRP_LISTE_CDE_AFFECTES2")
public class PrpCdeUsrCode {

    @Id
    @EmbeddedId
    private VenteId id;

    @Column(name = "REFERENCE")
    private String reference;

    @Column(name = "VNT_DATE")
    private Date vntDate;

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

    @Column(name = "PREPARATEUR")
    private String preparateur;

    @Column(name = "PREPARATEUR_CODE_USER")
    private String preparateurCodeUser;

    @Column(name = "VERIFICATEUR")
    private String verificateur;

    @Column(name = "VERIFICATEUR_CODE_USER")
    private String verificateurCodeUser;

    @Column(name = "VERIFICATEUR2")
    private String verificateur2;

    @Column(name = "VERIFICATEUR2_CODE_USER")
    private String verificateur2CodeUser;

    @Column(name = "PREPARE")
    private String prepare;

    @Column(name = "FRIG_PSYCHO")
    private String frigPsycho;

    @Column(name = "STATUT")
    private String statut;

}
