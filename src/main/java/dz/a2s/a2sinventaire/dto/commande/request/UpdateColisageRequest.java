package dz.a2s.a2sinventaire.dto.commande.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private List<bacId> bacsIds;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class bacId {
        private Integer id;
        private Integer typeId;
    }

}
