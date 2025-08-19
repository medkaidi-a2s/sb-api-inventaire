package dz.a2s.a2spreparation.services;

import dz.a2s.a2spreparation.dto.auth.AuthorizationDto;
import dz.a2s.a2spreparation.dto.auth.AuthorizationTypes;

import java.util.List;

public interface AuthorizationService {

    AuthorizationDto getAffectationAuthorization();
    AuthorizationDto getPreparationAuthorization();
    AuthorizationDto getControlAuthorization();
    AuthorizationDto getStatisticsAuthorization();
    List<AuthorizationDto> getAuthorizations();
    Boolean hasAuthorization(Integer code, AuthorizationTypes type);
}
