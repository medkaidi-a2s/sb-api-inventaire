package dz.a2s.a2spreparation.dto.preparation.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddLotRequest {

    @NotNull(message = "L'identifiant du site obligatoire")
    private Integer cmpId;

    @NotNull(message = "L'identifiant de la commande obligatoire")
    private Integer id;

    @NotNull(message = "Type de la commande obligatoire")
    private String type;

    @NotNull(message = "Code stock obligatoire")
    private String stkCode;

    @NotNull(message = "Le numéro de lot est obligatoire")
    private Integer lotId;

    @NotNull(message = "L'identifiant du produit est obligatoire")
    private Integer medId;

    @NotNull(message = "La quantité du produit est obligatoire")
    private Integer quantity;

}
