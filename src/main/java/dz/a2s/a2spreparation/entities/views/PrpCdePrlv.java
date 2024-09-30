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
@Table(name = "PRP_LISTE_PLVS")
public class PrpCdePrlv {

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

    @Column(name = "CONTROLEUR")
    private String controleur;

    @Column(name = "VERIF1")
    private String verif1;

    @Column(name = "VERIF2")
    private String verif2;

    @Column(name = "STATUT")
    private String statut;

    @Column(name = "CREER_USER")
    private String creerUser;

}
