package dz.a2s.a2sinventaire.entities.views;

import dz.a2s.a2sinventaire.entities.keys.VenteId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Immutable
@Table(name = "PRP_LISTE_CDE_CONTROLES")
public class Commande {

    @Id
    @EmbeddedId
    private VenteId id;

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

    @Column(name = "NBR_LIGNE_VALID")
    private Integer nbrLigneValid;

    @Column(name = "TOTAL_TTC")
    private BigDecimal totalTtc;

    @Column(name = "CREER_PAR")
    private String creerPar;

    @Column(name = "CREER_DATE")
    private Date creerDate;

    @Column(name = "PORTEFEUILLE")
    private String portefeuille;

    @Column(name = "PREPARATEUR_ID")
    private Integer preparateurId;

    @Column(name = "PREPARATEUR")
    private String preparateur;

    @Column(name = "VERIFICATEUR_ID")
    private Integer verificateurId;

    @Column(name = "VERIFICATEUR")
    private String verificateur;

    @Column(name = "VERIFICATEUR_ID2")
    private Integer verificateurId2;

    @Column(name = "VERIFICATEUR2")
    private String verificateur2;

    @Column(name = "PREPARE")
    private String prepare;

    @Column(name = "FRIG_PSYCHO")
    private String frigPsycho;

    @Column(name = "STATUT")
    private String statut;

    @Column(name = "PRIORITE_REGION")
    private String priorite;

}
