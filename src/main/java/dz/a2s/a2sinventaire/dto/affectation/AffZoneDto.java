package dz.a2s.a2sinventaire.dto.affectation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AffZoneDto {

    private CmdZoneIdDto id;
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
    private String verificateur;
    private String verificateur2;
    private String prepare;
    private String frigPsycho;
    private String statut;
    private String zone;

}
