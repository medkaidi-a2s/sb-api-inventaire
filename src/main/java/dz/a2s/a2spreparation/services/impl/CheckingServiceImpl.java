package dz.a2s.a2spreparation.services.impl;

import dz.a2s.a2spreparation.dto.CommandeResponseDto;
import dz.a2s.a2spreparation.entities.views.Commande;
import dz.a2s.a2spreparation.mappers.CommandeMapper;
import dz.a2s.a2spreparation.repositories.views.CommandeRepository;
import dz.a2s.a2spreparation.repositories.views.VenteDetailsRepository;
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
    private final VenteDetailsRepository venteDetailsRepository;

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

    @Override
    public Integer startControleCde(int p_vnt_cmp_id, int p_vnt_id, String p_vnt_type, String p_vnt_stk_code) throws Exception {
        log.info("Entering the method startControleCde from the CheckingService");

        Integer response = this.commandeRepository.startControleCde(
                p_vnt_cmp_id,
                p_vnt_id,
                p_vnt_type,
                p_vnt_stk_code
        );

        log.info("Valeur de retour de la procédure stockée pour commencer le contrôle est de : {}", response);

        if(response != 0)
            throw new Exception("Erreur lors de la mise à jour de la commande");

        return response;
    }

    @Override
    public Integer setControlledQuantity(Integer cmpId, Integer id, String type, String stkCode, Integer no, Integer qte, String motif) throws Exception {
        log.info("Entering the setControlledQuantity method from the CheckingService");

        Integer response = this.venteDetailsRepository.setControlledQuantity(
                cmpId,
                id,
                type,
                stkCode,
                no,
                qte,
                motif
        );

        log.info("Réponse de la requête de mise à jour de la quantité contrôlée {}", response);

        if(response == 0)
            throw new Exception("Une erreur est survenu lors de la mise à jour de la quantité contrôlée pour la commande spécifiée");

        return response;
    }
}
