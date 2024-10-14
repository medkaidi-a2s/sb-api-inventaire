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
public class VenteZoneId {

    @Column(name = "VNT_CMP_ID")
    private Integer vntCmpId;

    @Column(name="VNT_ID")
    private Integer vntId;

    @Column(name="VNT_TYPE")
    private String vntType;

    @Column(name="VNT_STK_CODE")
    private String vntStkCode;

    @Column(name = "VBZ_ZONE")
    private Integer vbzZone;

}
