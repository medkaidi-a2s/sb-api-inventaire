package dz.a2s.a2sinventaire.dto.preparation;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LigneQteDto {

    @NotNull(message = "Identifiant du site obligatoire")
    private Integer cmpId;

    @NotNull(message = "Identifiant de la commande obligatoire")
    private Integer id;

    @NotNull(message = "Type de la commande obligatoire")
    private String type;

    @NotNull(message = "Code stock obligatoire")
    private String stkCode;

    @NotNull(message = "Numéro de la ligne obligatoire")
    private Integer no;

    @NotNull(message = "Quantité de la préparation obligatoire")
    private Integer qte;

    private Integer newLotId;

    private Integer motif;

}
