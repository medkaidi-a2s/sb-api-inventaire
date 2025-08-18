package dz.a2s.a2spreparation.services;

import dz.a2s.a2spreparation.dto.auth.AuthorizationDto;

import java.util.List;

public interface AuthorizationService {

    AuthorizationDto getAffectationAuthorization();
    AuthorizationDto getPreparationAuthorization();
    AuthorizationDto getControlAuthorization();
    AuthorizationDto getStatisticsAuthorization();
    List<AuthorizationDto> getAuthorizations();

    void testProcedure();
}
