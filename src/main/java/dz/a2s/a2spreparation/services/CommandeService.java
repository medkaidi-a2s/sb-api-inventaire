package dz.a2s.a2spreparation.services;

import dz.a2s.a2spreparation.dto.CommandeResponseDto;
import dz.a2s.a2spreparation.dto.CommandeZoneResponseDto;
import dz.a2s.a2spreparation.dto.affectation.CmdColisageDto;
import dz.a2s.a2spreparation.dto.affectation.CmdIdDto;
import dz.a2s.a2spreparation.dto.affectation.CmdZoneColisageDto;
import dz.a2s.a2spreparation.dto.affectation.CmdZoneIdDto;
import dz.a2s.a2spreparation.dto.commande.response.ColisageDto;

import java.util.List;

public interface CommandeService {

    List<CommandeResponseDto> getControlledCommandes(String date);
    List<CommandeZoneResponseDto> getControlledCommandesZone(String date);
    Integer saisirColisageCommande(CmdColisageDto cmdColisageDto);
    Integer saisirColisageZone(CmdZoneColisageDto cmdZoneColisageDto);
    ColisageDto getColisageZone(CmdZoneIdDto id);
    ColisageDto getColisageCommande(CmdIdDto id);

}
