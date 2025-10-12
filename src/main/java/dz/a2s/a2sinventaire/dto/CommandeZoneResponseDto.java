package dz.a2s.a2sinventaire.dto;

import dz.a2s.a2sinventaire.dto.affectation.CmdZoneIdDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommandeZoneResponseDto {

    private CmdZoneIdDto id;
    private String reference;
    private Date date;
    private String client;
    private String region;
    private Integer nbrLigne;
    private Integer nbrLigneValid;
    private BigDecimal totalTtc;
    private String creerPar;
    private Date creerDate;
    private String portefeuille;
    private Integer preparateurId;
    private String preparateur;
    private Integer verificateurId;
    private String verificateur;
    private Integer verificateurId2;
    private String verificateur2;
    private String prepare;
    private String frigPsycho;
    private String statut;
    private String zone;
    private String priorite;
    private String search;
    private String key;

}
