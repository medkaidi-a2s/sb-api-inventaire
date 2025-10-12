package dz.a2s.a2sinventaire.dto.affectation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString(callSuper = true)
public class CmdZoneColisageDto extends CmdZoneIdDto{
    private Integer colisV;
    private Integer colisD;
    private Integer frigo;
    private Integer psycho;
    private Integer chers;
    private Integer sachet;

}
