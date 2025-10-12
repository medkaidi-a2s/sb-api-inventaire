package dz.a2s.a2sinventaire.dto.affectation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AffectCmdResultDto {

    private Integer messageId;
    private String venteRef;
    private String message;

}
