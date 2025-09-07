package dz.a2s.a2spreparation.dto.commande.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateColisageRequest {

    private Integer cmpId;
    private Integer id;
    private String type;
    private String stkCode;
    private Integer colisV;
    private Integer colisD;
    private Integer frigo;
    private Integer psycho;
    private Integer chers;
    private Integer sachet;
    private Integer bacs;
    private Integer palettes;

}
