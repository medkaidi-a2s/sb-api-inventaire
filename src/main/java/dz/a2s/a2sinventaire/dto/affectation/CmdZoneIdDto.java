package dz.a2s.a2sinventaire.dto.affectation;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CmdZoneIdDto {

    @NotNull(message = "Identifiant du site obligatoire")
    private Integer cmpId;

    @NotNull(message = "Identifiant de la commande obligatoire")
    private Integer id;

    @NotNull(message = "Type de la commande obligatoire")
    private String type;

    @NotNull(message = "Code stock de la commande obligatoire")
    private String stkCode;

    @NotNull(message = "Zone de la commande est obligatoire")
    private Integer zone;

}
