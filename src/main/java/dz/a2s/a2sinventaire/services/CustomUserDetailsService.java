package dz.a2s.a2sinventaire.services;

import dz.a2s.a2sinventaire.entities.UserEntity;
import dz.a2s.a2sinventaire.exceptions.ActionNotAllowedException;
import dz.a2s.a2sinventaire.repositories.UserEntityRepository;
import dz.a2s.a2sinventaire.security.AppUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static java.lang.Integer.parseInt;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserEntityRepository userEntityRepository;

    @Override
    public AppUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Entering loadUserByUsername method of UserDetails with {}", username);

        var user = this.userEntityRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Nom d'utilisateur ou mot de passe incorrecte"));
        return new AppUserDetails(user.getUsername(), user.getPassword(), user.getNom(), Collections.emptyList());
    }

    public Integer getCurrentCompanyId() {
//        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String[] split = username.split(":");
//        log.info("Fetched the current company | companyId={}", Integer.parseInt(split[0]));
//        return Integer.parseInt(split[0]);
        throw new ActionNotAllowedException("La notion de site n'existe pas dans ce context.");
    }

    public String getCurrentUserCode() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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

    public Integer changePassword(String currentPassword, String newPassowrd) {
        int response = 0;
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String passwordFromDb = this.userEntityRepository.getUserPassword(username);
        if(currentPassword.equals(passwordFromDb))
            response = this.userEntityRepository.changePassword(username, newPassowrd);
        else
            throw new ActionNotAllowedException("Le mot de passe actuel est incorrecte");

        if(response == 0)
            throw new ActionNotAllowedException("Le mot de passe de l'utilisateur n'a pas pu être modifié");

        return response;
    }
}
