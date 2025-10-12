package dz.a2s.a2sinventaire.services.impl;

import dz.a2s.a2sinventaire.config.ParamsProperties;
import dz.a2s.a2sinventaire.dto.auth.AuthorizationDto;
import dz.a2s.a2sinventaire.dto.auth.AuthorizationTypes;
import dz.a2s.a2sinventaire.dto.auth.ParamMeta;
import dz.a2s.a2sinventaire.repositories.ParamsRepository;
import dz.a2s.a2sinventaire.repositories.ProcedureCaller;
import dz.a2s.a2sinventaire.repositories.StpUserRolesRepository;
import dz.a2s.a2sinventaire.services.AuthorizationService;
import dz.a2s.a2sinventaire.services.CustomUserDetailsService;
import jakarta.persistence.ParameterMode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    private final CustomUserDetailsService customUserDetailsService;
    private final StpUserRolesRepository stpUserRolesRepository;
    private final ParamsRepository paramsRepository;
    private final ParamsProperties paramsProperties;
    private final ProcedureCaller procedureCaller;

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

    @Override
    public List<AuthorizationDto> getAuthorizations() {
        log.info("| Entry | CompanyService.getAuthorizations");

        var cmpId = this.customUserDetailsService.getCurrentCompanyId();
        var username = this.customUserDetailsService.getCurrentUserCode();
        var codes = this.paramsProperties.getAuthorizations().values().stream().toList();
        log.info("Fetching authorizations for cmpId={} and username={} - authorizations list : {}", cmpId, username, codes);

        var projections = this.paramsRepository.getAuthorizations(cmpId, codes, username);
        log.info("Fetched authorizations for cmpId={} and username={} - authorizations size : {}", cmpId, username, projections.size());

        var authorizations = projections.stream().map(item -> new AuthorizationDto(item.getCode(), item.getValeur())).toList();
        log.info("Mapped authorizations into DTOs : {}", authorizations);

        return authorizations;
    }

    @Override
    public Boolean hasAuthorization(Integer code, AuthorizationTypes type) {
        log.info("| Entry | AuthorizationService.hasAuthorization | Args | code={}, type={}", code, type);

        var result = this.callAuthorizationProcedure(code);

        return switch (type) {
            case READ -> result.containsKey("XLIRE") && (Integer) result.get("XLIRE") == 1;
            case INSERT -> result.containsKey("XINSERT") && (Integer) result.get("XINSERT") == 1;
            case UPDATE -> result.containsKey("XUPDATE") && (Integer) result.get("XUPDATE") == 1;
            case DELETE -> result.containsKey("XDELETE") && (Integer) result.get("XDELETE") == 1;
            case PRINT -> result.containsKey("XIMPRIME") && (Integer) result.get("XIMPRIME") == 1;
            case RUN -> result.containsKey("XEXECUTE") && (Integer) result.get("XEXECUTE") == 1;
            case EXPORT -> result.containsKey("XEXPORT") && (Integer) result.get("XEXPORT") == 1;
            default -> false;
        };
    }

    public Map<String, Object> callAuthorizationProcedure(Integer code) {
        log.info("| Entry | AuthorizationService.callAuthorizationProcedure | Args | code={}", code);

        var cmpId = this.customUserDetailsService.getCurrentCompanyId();
        var username = this.customUserDetailsService.getCurrentUserCode();

        // OUT params
        List<ParamMeta> params = new ArrayList<>();
        params.add(new ParamMeta("P_CMP", Integer.class, ParameterMode.IN, cmpId));
        params.add(new ParamMeta("XFRM", Integer.class, ParameterMode.IN, code));
        params.add(new ParamMeta("XLIRE", Integer.class, ParameterMode.OUT, null));
        params.add(new ParamMeta("XINSERT", Integer.class, ParameterMode.OUT, null));
        params.add(new ParamMeta("XUPDATE", Integer.class, ParameterMode.OUT, null));
        params.add(new ParamMeta("XDELETE", Integer.class, ParameterMode.OUT, null));
        params.add(new ParamMeta("XIMPRIME", Integer.class, ParameterMode.OUT, null));
        params.add(new ParamMeta("XEXECUTE", Integer.class, ParameterMode.OUT, null));
        params.add(new ParamMeta("XEXPORT", Integer.class, ParameterMode.OUT, null));
        params.add(new ParamMeta("xuser", String.class, ParameterMode.IN, username));
        params.add(new ParamMeta("XMSG", String.class, ParameterMode.OUT, null));

        Map<String, Object> result = procedureCaller.callProcedure("TROLES1", params);

        log.info("Fetched the stored procedure result {}", result);

        return result;
    }

}
