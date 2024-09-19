package dz.a2s.a2spreparation.services.impl;

import dz.a2s.a2spreparation.dto.AuthorizationDto;
import dz.a2s.a2spreparation.repositories.StpUserRolesRepository;
import dz.a2s.a2spreparation.services.AuthorizationService;
import dz.a2s.a2spreparation.services.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    private final CustomUserDetailsService customUserDetailsService;
    private final StpUserRolesRepository stpUserRolesRepository;

    @Value("${params.authorizations.affectation}")
    private int affectationFormCode;

    @Override
    public AuthorizationDto getAffectationAuthorization() {
        log.info("Entering the getAffectationAuthorization method from the AuthorizationService for formCode {}", affectationFormCode);

        String username = this.customUserDetailsService.getCurrentUserCode();
        Integer companyId = this.customUserDetailsService.getCurrentCompanyId();
        Integer value = this.stpUserRolesRepository.getAuthorization(username, companyId, affectationFormCode);

        AuthorizationDto authorizationDto = new AuthorizationDto(affectationFormCode, value);

        return authorizationDto;
    }
}
