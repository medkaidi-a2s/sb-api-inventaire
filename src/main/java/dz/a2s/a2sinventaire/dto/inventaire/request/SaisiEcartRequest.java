package dz.a2s.a2sinventaire.dto.inventaire.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SaisiEcartRequest {

    @NotNull
    private Integer invId;

    @NotNull
    private Integer nlotInterne;

    @NotNull
    private Integer medId;

    @NotNull
    private String depot;

    @NotNull
    private Integer quantite;

    @NotNull
    private Integer noLigne;

}
