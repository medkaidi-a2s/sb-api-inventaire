package dz.a2s.a2sinventaire.entities.keys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class VntBonBacsId implements Serializable {

    private Integer cmpId;
    private Integer venteId;
    private String type;
    private String stkCode;
    private Integer bacId;
    private Integer typeId;

}
