package dz.a2s.a2spreparation.services.impl;

import dz.a2s.a2spreparation.dto.CommandeResponseDto;
import dz.a2s.a2spreparation.entities.views.Commande;
import dz.a2s.a2spreparation.mappers.CommandeMapper;
import dz.a2s.a2spreparation.repositories.views.CommandeRepository;
import dz.a2s.a2spreparation.services.CheckingService;
import dz.a2s.a2spreparation.services.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class CheckingServiceImpl implements CheckingService {
    private final CustomUserDetailsService customUserDetailsService;
    private final CommandeRepository commandeRepository;

    @Override
    public List<CommandeResponseDto> getAllPreparedCommandes(String date) {
        log.info("Entering the getAllComandes method from the CheckingService with date {}", date);

        Integer companyId = this.customUserDetailsService.getCurrentCompanyId();
        log.info("Company ID fetched from the service {}", companyId);

        Integer utilisateurId = this.customUserDetailsService.getUtilisateurId();
        log.info("Utilisateur ID fetched from the service {}", utilisateurId);

        List<Commande> commandes = this.commandeRepository.getPreparedCommandes(companyId, utilisateurId, date);
        log.info("Fetched the commande to check from the repo with length {}", commandes.size());

        List<CommandeResponseDto> response = commandes.stream().map(CommandeMapper::toCommandeResponseDto).toList();

        return response;
    }
}
