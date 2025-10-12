package dz.a2s.a2sinventaire.dto.preparation;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LigneVenteDto {

    @NotNull(message = "Company est obligatoire")
    private Integer cmpId;

    @NotNull(message = "Identifiant de la vente est obligatoire")
    private Integer id;

    @NotNull(message = "Type de la commande est obligatoire")
    private String type;

    @NotNull(message = "Code stock est obligatoire")
    private String stkCode;

    @NotNull(message = "Numéro de la ligne est obligatoire")
    private Integer no;

}
