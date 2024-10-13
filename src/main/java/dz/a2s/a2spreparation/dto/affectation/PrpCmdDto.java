package dz.a2s.a2spreparation.dto.affectation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PrpCmdDto {

    private CmdIdDto id;
    private String reference;
    private Date date;
    private String client;
    private String region;
    private Integer nbrLigne;
    private BigDecimal totalTtc;
    private String creerPar;
    private Date creerDate;
    private String portefeuille;
    private String frigPsycho;
    private String statut;

}
