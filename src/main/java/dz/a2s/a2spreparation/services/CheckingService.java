package dz.a2s.a2spreparation.services;

import dz.a2s.a2spreparation.dto.CommandeResponseDto;

import java.util.List;

public interface CheckingService {

    List<CommandeResponseDto> getAllPreparedCommandes(String date);

}
