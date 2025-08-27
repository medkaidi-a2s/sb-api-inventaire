package dz.a2s.a2spreparation.dto.preparation;

import dz.a2s.a2spreparation.dto.affectation.CmdPrlvIdDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PrpCmdPrlvUsrCodeDto {

    private CmdPrlvIdDto id;
    private String reference;
    private Date date;
    private String zone;
    private String client;
    private String region;
    private Integer nbrLignes;
    private Integer preparateurId;
    private String preparateurCode;
    private String preparateur;
    private Integer verificateurId1;
    private String verificateur1Code;
    private String verificateur1;
    private Integer verificateurId2;
    private String verificateur2Code;
    private String verificateur2;
    private String statut;
    private String creerPar;
    private String key;

}
