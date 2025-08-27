package dz.a2s.a2spreparation.dto.controle.response;

import dz.a2s.a2spreparation.dto.affectation.CmdIdDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
public class BonCommandeZoneDto {

    private CmdIdDto id;
    private String reference;
    private Date date;
    private String client;
    private String region;
    private BigDecimal totalTtc;
    private String preparateur;
    private String controleur1;
    private String controleur2;
    private String statut;
    private Integer nbrLigne;
    private Integer nbrLigneValid;
    private String key;


}
