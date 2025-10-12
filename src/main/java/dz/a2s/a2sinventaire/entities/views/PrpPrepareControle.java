package dz.a2s.a2sinventaire.entities.views;

import dz.a2s.a2sinventaire.entities.keys.StpTiersId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Immutable
@Table(name = "PRP_LISTE_PREPARE_CONTROLE")
public class PrpPrepareControle {

    @Id
    @EmbeddedId
    private StpTiersId id;

    @Column(name = "TER_NOM")
    private String terNom;

}
