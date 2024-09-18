package dz.a2s.a2spreparation.services.impl;

import dz.a2s.a2spreparation.repositories.StpUserRolesRepository;
import dz.a2s.a2spreparation.services.AuthorizationService;
import dz.a2s.a2spreparation.services.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    private final CustomUserDetailsService customUserDetailsService;
    private final StpUserRolesRepository stpUserRolesRepository;

    @Override
    public Integer getAffectationAuthorization() {
        String username = this.customUserDetailsService.getCurrentUserCode();
        Integer companyId = this.customUserDetailsService.getCurrentCompanyId();
        Integer formCode = 480;
        return this.stpUserRolesRepository.getAuthorization(username, companyId, formCode);
    }
}
