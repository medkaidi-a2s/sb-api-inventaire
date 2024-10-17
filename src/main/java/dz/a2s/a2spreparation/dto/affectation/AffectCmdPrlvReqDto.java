package dz.a2s.a2spreparation.dto.affectation;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AffectCmdPrlvReqDto {

    @NotNull(message = "Identifiant du site obligatoire")
    private int p_cmp;

    @NotNull(message = "Identifiant de la commande obligatoire")
    private int p_slt_id;

    @NotNull(message = "Type de la commande obligatoire")
    private String p_slt_type;

    @NotNull(message = "Année de la commande obligatoire")
    private int p_slt_annee;

    @NotNull(message = "Identifiant du préparateur obligatoire")
    private int p_prp;

    @NotNull(message = "Identifiant du premier contrôleur obligatoire")
    private int p_cnt1;

    private int p_cnt2;
    private String p_user;
    private String reference;

}
