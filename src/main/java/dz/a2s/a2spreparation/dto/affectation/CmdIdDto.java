package dz.a2s.a2spreparation.dto.affectation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CmdIdDto {

    private Integer cmpId;
    private Integer id;
    private String type;
    private String stkCode;

}
