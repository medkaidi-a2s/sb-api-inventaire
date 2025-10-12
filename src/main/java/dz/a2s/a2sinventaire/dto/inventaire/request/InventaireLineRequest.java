package dz.a2s.a2sinventaire.dto.inventaire.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class InventaireLineRequest {

    private Integer invId;
    private Integer comptage;
    private String emplacement;
    private Integer stockZero;
    private Integer isEcart;
    private String search;
    private Integer page;

}
