package dz.a2s.a2spreparation.entities.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class VentePrlvDetailsId {

    @Column(name = "SLD_CMP_ID")
    private Integer cmpId;

    @Column(name = "SLD_SLT_ID")
    private Integer id;

    @Column(name = "SLD_SLT_TYPE")
    private String type;

    @Column(name = "SLD_SLT_ANNEE")
    private Integer annee;

    @Column(name = "SLD_NO")
    private Integer no;

}
