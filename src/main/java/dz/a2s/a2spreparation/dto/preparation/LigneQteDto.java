package dz.a2s.a2spreparation.dto.preparation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LigneQteDto {

    private Integer cmpId;
    private Integer id;
    private String type;
    private String stkCode;
    private Integer no;
    private Integer qte;

}
