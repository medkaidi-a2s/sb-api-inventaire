package dz.a2s.a2sinventaire.entities;

import dz.a2s.a2sinventaire.entities.keys.StpTiersId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table
@Entity(name = "STP_TIERS")
public class StpTiers {

    @Id
    @EmbeddedId
    private StpTiersId id;

    @Column(name = "TER_NOM")
    private String terNom;

    @Column(name = "TER_COMMERCIAL")
    private String terCommercial;

}
