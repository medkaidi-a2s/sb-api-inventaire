package dz.a2s.a2sinventaire.entities.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class StpTiersId {

    @Column(name = "TER_ID")
    private Integer terId;

    @Column(name = "TER_CMP_ID")
    private Integer terCmpId;

    @Column(name = "TER_TYPE")
    private String terType;

}
