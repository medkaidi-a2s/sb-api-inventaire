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
public class VenteDetailsId {

    @Column(name = "VND_CMP_ID")
    private Integer cmpId;

    @Column(name = "VND_VNT_ID")
    private Integer id;

    @Column(name = "VND_VNT_TYPE")
    private String type;

    @Column(name = "VND_STK_CODE")
    private String stkCode;

    @Column(name = "VND_NO")
    private Integer no;

}
