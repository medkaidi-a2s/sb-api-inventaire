package dz.a2s.a2spreparation.dto.affectation;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PrpCdePrlvPrepContDto {

    @NotNull(message = "Company ne doit pas être null")
    private Integer p_cmp;

    @NotNull(message = "Id ne doit pas être null")
    private Integer p_slt_id;

    @NotNull(message = "Le type ne doit pas être null")
    private String p_slt_type;

    @NotNull(message = "Année ne doit pas être null")
    private Integer p_slt_annee;

    @NotNull(message = "Id préparateur ne doit pas être null")
    private Integer p_prp;

    @NotNull(message = "Id contrôleur ne doit pas être null")
    private Integer p_cnt1;

    private Integer p_cnt2;

}
