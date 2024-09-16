package dz.a2s.a2spreparation.services;

import dz.a2s.a2spreparation.entities.UserEntity;
import dz.a2s.a2spreparation.repositories.UserEntityRepository;
import dz.a2s.a2spreparation.security.AppUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
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

        String[] split = username.split(":");
        int companyId = parseInt(split[0]);
        log.info("The companyId is {}", companyId);
        String userCode = split[1];
        log.info("The userCode is {}", userCode);

        UserEntity userEntity = this.userEntityRepository.findByUsernameAndCompanyId(userCode, companyId).orElseThrow();
        return new AppUserDetails(userEntity.getUsername(), userEntity.getPassword(), userEntity.getNom(), Collections.emptyList(), userEntity.getCompanyId());
    }

    public Integer GetCurrentCompanyId() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String[] split = username.split(":");
        return Integer.parseInt(split[0]);
    }

    public Integer GetCurrentUserCode() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String[] split = username.split(":");
        return Integer.parseInt(split[1]);
    }
}
