package dz.a2s.a2spreparation.services;

import dz.a2s.a2spreparation.entities.UserEntity;
import dz.a2s.a2spreparation.exceptions.ActionNotAllowedException;
import dz.a2s.a2spreparation.repositories.UserEntityRepository;
import dz.a2s.a2spreparation.security.AppUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

import static java.lang.Integer.parseInt;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserEntityRepository userEntityRepository;

    @Override
    public AppUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Entering loadUserByUsername method of UserDetails with {}", username);

        String[] split = username.split(":");
        int companyId = parseInt(split[0]);
        log.info("The companyId is {}", companyId);
        String userCode = split[1];
        log.info("The userCode is {}", userCode);

        UserEntity userEntity = this.userEntityRepository.findByUsernameAndCompanyId(userCode, companyId).orElseThrow(() -> new UsernameNotFoundException("Nom d'utilisateur ou mot de passe incorrecte"));
//        var users = this.userEntityRepository.findByUsernameAndCompanyId(userCode, companyId);
//        if(users.isEmpty())
//            throw new UsernameNotFoundException("Nom d'utilisateur ou mot de passe incorrecte");
//        UserEntity userEntity = users.get(0);
        return new AppUserDetails(userEntity.getUsername(), userEntity.getPassword(), userEntity.getNom(), Collections.emptyList(), userEntity.getCompanyId());
    }

    public Integer getCurrentCompanyId() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String[] split = username.split(":");
        return Integer.parseInt(split[0]);
    }

    public String getCurrentUserCode() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String[] split = username.split(":");
        return split[1];
    }

//    public String getPreparationZone() {
//        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String[] split = username.split(":");
//        Integer companyId = Integer.parseInt(split[0]);
//        String userCode = split[1];
//
//        String preparationZone = this.userEntityRepository.getPreparationZone(userCode, companyId);
//
//        return preparationZone;
//    }

    public Integer getUtilisateurId(Integer tierType) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String[] split = username.split(":");
        Integer companyId = Integer.parseInt(split[0]);
        String userCode = split[1];

        var ids = this.userEntityRepository.getUtilisateurId(userCode, companyId, tierType);

        if(ids.isEmpty())
            throw new ActionNotAllowedException("Accès refusé : Action non autorisée.");

        Integer id = this.userEntityRepository.getUtilisateurId(userCode, companyId, tierType).get(0);

        return id;
    }

    public Integer changePassword(String currentPassword, String newPassowrd) throws Exception {
        int response = 0;
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String[] split = username.split(":");
        Integer companyId = Integer.parseInt(split[0]);
        String userCode = split[1];

        String passwordFromDb = this.userEntityRepository.getUserPassword(userCode, companyId);
        if(currentPassword.equals(passwordFromDb))
            response = this.userEntityRepository.changePassword(userCode, companyId, newPassowrd);
        else
            throw new Exception("Le mot de passe actuel est incorrecte");

        if(response == 0)
            throw new Exception("Le mot de passe de l'utilisateur n'a pas pu être changé");

        return response;
    }
}
