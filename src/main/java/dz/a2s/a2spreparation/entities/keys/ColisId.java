package dz.a2s.a2spreparation.entities.keys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class ColisId implements Serializable {

    private Integer cmpId;
    private Integer id;
    private String type;
    private String stkCode;
    private String code;

}
