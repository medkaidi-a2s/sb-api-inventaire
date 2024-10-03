package dz.a2s.a2spreparation.entities.views;

import dz.a2s.a2spreparation.entities.keys.VenteId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Immutable
@Table(name = "PRP_CDE_PREP_CONT")
public class PrpCdePrepCont {

    @EmbeddedId
    private VenteId id;

    @Column(name = "CONTROLEUR_ID")
    private Integer preparateurId;

    @Column(name = "VERIFICATEUR_ID")
    private Integer verificateurId;

    @Column(name = "VERIFICATEUR_ID2")
    private Integer verificateurId2;

}
