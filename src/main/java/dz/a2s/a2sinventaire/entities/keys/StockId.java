package dz.a2s.a2sinventaire.entities.keys;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class StockId implements Serializable {

    private Integer cmpId;
    private Integer id;
    private Integer medId;
    private String stkCode;

}
