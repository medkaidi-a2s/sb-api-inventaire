package dz.a2s.a2spreparation.services;

import dz.a2s.a2spreparation.dto.CommandeResponseDto;
import dz.a2s.a2spreparation.dto.CommandeZoneResponseDto;
import dz.a2s.a2spreparation.dto.affectation.CmdColisageDto;
import dz.a2s.a2spreparation.dto.affectation.CmdIdDto;
import dz.a2s.a2spreparation.dto.affectation.CmdZoneColisageDto;
import dz.a2s.a2spreparation.dto.affectation.CmdZoneIdDto;
import dz.a2s.a2spreparation.dto.commande.request.UpdateColisageRequest;
import dz.a2s.a2spreparation.dto.commande.response.ColisageDto;
import dz.a2s.a2spreparation.dto.commande.response.CommandeColisageResponse;
import dz.a2s.a2spreparation.dto.commande.response.ListeEtiquettesResponse;
import dz.a2s.a2spreparation.dto.response.PaginatedDataDto;
import dz.a2s.a2spreparation.entities.Bac;
import dz.a2s.a2spreparation.entities.Colis;

import java.util.List;

public interface CommandeService {

    PaginatedDataDto<CommandeColisageResponse> getCommandesColisage(String dateDebut, String dateFin, Integer statutPrepare, String rotation, Integer page, String search);
    List<CommandeResponseDto> getAllCommandes(String search, String date);
    List<CommandeZoneResponseDto> getAllCommandesZone(String search, String date);
    List<CommandeResponseDto> getControlledCommandes(String date);
    List<CommandeZoneResponseDto> getControlledCommandesZone(String date);
    Integer saisirColisageCommande(CmdColisageDto cmdColisageDto);
    Integer saisirColisageZone(CmdZoneColisageDto cmdZoneColisageDto);
    ColisageDto getColisageZone(CmdZoneIdDto id);
    ColisageDto getColisageCommande(CmdIdDto id);
    List<Bac> getListeBacs();
    CommandeColisageResponse updateColisageGlobal(UpdateColisageRequest request);
    ListeEtiquettesResponse getEtiquettesColis(CmdIdDto id);

}
