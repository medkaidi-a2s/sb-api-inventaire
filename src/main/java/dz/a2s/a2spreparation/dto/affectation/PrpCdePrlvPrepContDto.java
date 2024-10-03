package dz.a2s.a2spreparation.dto.affectation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PrpCdePrlvPrepContDto {

    private Integer p_cmp;
    private Integer p_slt_id;
    private String p_slt_type;
    private Integer p_slt_annee;
    private Integer p_prp;
    private Integer p_cnt1;
    private Integer p_cnt2;

}
