package dz.a2s.a2spreparation.entities.views;

import dz.a2s.a2spreparation.entities.keys.StkListesId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@Table(name = "PRP_CDE_PRLV_PREP_CONT")
public class PrpCdePrlvPrepCont {

    @EmbeddedId
    private StkListesId id;

    @Column(name = "CONTROLEUR_ID")
    private Integer preparateurId;

    @Column(name = "VERIFICATEUR_ID")
    private Integer verificateurId;

    @Column(name = "VERIFICATEUR_ID2")
    private Integer verificateurId2;

    @Column(name = "CREER_DATE")
    private Date creerDate;

    @Column(name = "CREER_PAR")
    private String creerPar;

}
