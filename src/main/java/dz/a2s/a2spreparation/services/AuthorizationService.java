package dz.a2s.a2spreparation.services;

import dz.a2s.a2spreparation.dto.auth.AuthorizationDto;

public interface AuthorizationService {

    AuthorizationDto getAffectationAuthorization();

    AuthorizationDto getPreparationAuthorization();

    AuthorizationDto getControlAuthorization();

    AuthorizationDto getStatisticsAuthorization();

}
