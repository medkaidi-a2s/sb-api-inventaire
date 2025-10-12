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
public class StkListesId {

    @Column(name = "SLT_CMP_ID")
    private Integer sltCmpId;

    @Column(name = "SLT_ID")
    private Integer sltId;

    @Column(name = "SLT_TYPE")
    private String sltType;

    @Column(name = "SLT_ANNEE")
    private Integer sltAnnee;

}
