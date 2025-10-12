package dz.a2s.a2sinventaire.dto.preparation;

import dz.a2s.a2sinventaire.dto.affectation.CmdIdDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PrpCdeUsrCodeDto {
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
    private String preparateur;
    private String preparateurCode;
    private String verificateur;
    private String verificateurCode;
    private String verificateur2;
    private String verificateur2Code;
    private String prepare;
    private String frigPsycho;
    private String statut;

}
