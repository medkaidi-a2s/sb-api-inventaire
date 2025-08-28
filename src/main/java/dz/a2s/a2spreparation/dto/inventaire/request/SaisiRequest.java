package dz.a2s.a2spreparation.dto.inventaire.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SaisiRequest {

    @NotNull
    private Integer invId;

    @NotNull
    private Integer nlotInterne;

    @NotNull
    private Integer medId;

    @NotNull
    private String depot;

    @NotNull
    private Integer comptage;

    @NotNull
    private Integer quantite;

    private String motif;

    @NotNull
    private String emplacement;

    @NotNull
    private Integer noLigne;

}
