package dz.a2s.a2spreparation.dto.affectation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PrpCmdPrlvDto {

    private CmdPrlvIdDto id;
    private String reference;
    private Date date;
    private String zone;
    private String client;
    private String region;
    private Integer nbrLigne;
    private String statut;
    private String creerPar;
    private String key;

}
