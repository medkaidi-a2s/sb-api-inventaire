package dz.a2s.a2spreparation.entities.views;

import dz.a2s.a2spreparation.entities.keys.StkListesId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Immutable
@Table(name = "prp_liste_plvs_affecte_usrcode")
public class PrpCdePrlvUsrCode {

    @Id
    @EmbeddedId
    private StkListesId id;

    @Column(name = "REFERENCE")
    private String reference;

    @Column(name = "SLT_DATE")
    private Date sltDate;

    @Column(name = "ZONE")
    private String zone;

    @Column(name = "CLIENT")
    private String client;

    @Column(name = "REGION")
    private String region;

    @Column(name = "NBR_LIGNE")
    private Integer nbrLigne;

    @Column(name = "PREPARATEUR_ID")
    private Integer preparateurId;

    @Column(name = "PREPARATEUR_CODE")
    private String preparateurCode;

    @Column(name = "CONTROLEUR")
    private String preparateur;

    @Column(name = "VERIFICATEUR_ID1")
    private Integer verificateurId1;

    @Column(name = "VERIFICATEUR1_CODE")
    private String verificateur1Code;

    @Column(name = "VERIF1")
    private String verificateur;

    @Column(name = "VERIFICATEUR_ID2")
    private Integer verificateurId2;

    @Column(name = "VERIFICATEUR2_CODE")
    private String verificateur2Code;

    @Column(name = "VERIF2")
    private String verificateur2;

    @Column(name = "STATUT")
    private String statut;

    @Column(name = "CREER_USER")
    private String creerUser;

}
