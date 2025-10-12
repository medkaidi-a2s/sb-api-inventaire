package dz.a2s.a2sinventaire.dto.preparation.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AvailableLotsRequest {

    private Integer cmpId;
    private Integer medId;
    private Integer oldLotId;
    private Integer qte;

}
