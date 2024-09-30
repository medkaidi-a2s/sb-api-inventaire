package dz.a2s.a2spreparation.dto.affectation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AffectCmdPrlvReqDto {

    private int p_cmp;
    private int p_slt_id;
    private String p_slt_type;
    private int p_slt_annee;
    private int p_prp;
    private int p_cnt1;
    private int p_cnt2;
    private String p_user;
    private String reference;

}
