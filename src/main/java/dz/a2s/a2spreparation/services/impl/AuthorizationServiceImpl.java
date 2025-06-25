package dz.a2s.a2spreparation.services.impl;

import dz.a2s.a2spreparation.dto.auth.AuthorizationDto;
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

    @Value("${params.authorizations.preparation}")
    private int preparationFormCode;

    @Value("${params.authorizations.control}")
    private int controlFormCode;

    @Value("${params.authorizations.statistics}")
    private int statisticsFormCode;

    @Override
    public AuthorizationDto getAffectationAuthorization() {
        log.info("Entering the getAffectationAuthorization method from the AuthorizationService for formCode {}", affectationFormCode);

        String username = this.customUserDetailsService.getCurrentUserCode();
        Integer companyId = this.customUserDetailsService.getCurrentCompanyId();
        Integer value = this.stpUserRolesRepository.getAuthorization(username, companyId, affectationFormCode);

        log.info("Valeur de retour de la requête relative à l'autorisation d'affectation {}", value);

        AuthorizationDto authorizationDto = new AuthorizationDto(affectationFormCode, value);

        return authorizationDto;
    }

    @Override
    public AuthorizationDto getPreparationAuthorization() {
        log.info("Entering the getPreparationAuthorization method from the AuthorizationService for formCode {}", preparationFormCode);

        String username = this.customUserDetailsService.getCurrentUserCode();
        Integer companyId = this.customUserDetailsService.getCurrentCompanyId();
        Integer value = this.stpUserRolesRepository.getAuthorization(username, companyId, preparationFormCode);

        log.info("Valeur de retour de la requête relative à l'autorisation de préparation {}", value);

        AuthorizationDto authorizationDto = new AuthorizationDto(preparationFormCode, value);

        return authorizationDto;
    }

    @Override
    public AuthorizationDto getControlAuthorization() {
        log.info("Entering the getControlAuthorization method from the AuthorizationService for formCode {}", controlFormCode);

        String username = this.customUserDetailsService.getCurrentUserCode();
        Integer companyId = this.customUserDetailsService.getCurrentCompanyId();
        Integer value = this.stpUserRolesRepository.getAuthorization(username, companyId, controlFormCode);

        log.info("Valeur de retour de la requête relative à l'autorisation du contrôle {}", value);

        AuthorizationDto authorizationDto = new AuthorizationDto(controlFormCode, value);

        return authorizationDto;
    }

    @Override
    public AuthorizationDto getStatisticsAuthorization() {
        log.info("Entering the getStatisticsAuthorization method from the AuthorizationService for formCode {}", statisticsFormCode);

        String username = this.customUserDetailsService.getCurrentUserCode();
        Integer companyId = this.customUserDetailsService.getCurrentCompanyId();
        Integer value = this.stpUserRolesRepository.getAuthorization(username, companyId, statisticsFormCode);

        log.info("Valeur de retour de la requête relative à l'autorisation des statistiques {}", value);

        AuthorizationDto authorizationDto = new AuthorizationDto(statisticsFormCode, value);

        return authorizationDto;
    }
}
