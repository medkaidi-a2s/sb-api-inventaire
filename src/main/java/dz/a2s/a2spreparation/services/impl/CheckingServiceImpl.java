package dz.a2s.a2spreparation.services.impl;

import dz.a2s.a2spreparation.dto.CommandeResponseDto;
import dz.a2s.a2spreparation.dto.CommandeZoneResponseDto;
import dz.a2s.a2spreparation.dto.affectation.CmdIdDto;
import dz.a2s.a2spreparation.dto.affectation.CmdZoneIdDto;
import dz.a2s.a2spreparation.dto.preparation.LigneQteZoneDto;
import dz.a2s.a2spreparation.entities.views.Commande;
import dz.a2s.a2spreparation.entities.views.CommandeZone;
import dz.a2s.a2spreparation.mappers.CommandeMapper;
import dz.a2s.a2spreparation.mappers.CommandeZoneMapper;
import dz.a2s.a2spreparation.repositories.views.CommandeRepository;
import dz.a2s.a2spreparation.repositories.views.CommandeZoneRepository;
import dz.a2s.a2spreparation.repositories.views.VenteDetailsRepository;
import dz.a2s.a2spreparation.repositories.views.VenteZoneDetailsRepository;
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
    private final CommandeZoneRepository commandeZoneRepository;
    private final VenteDetailsRepository venteDetailsRepository;
    private final VenteZoneDetailsRepository venteZoneDetailsRepository;

    @Override
    public List<CommandeResponseDto> getAllPreparedCommandes(String date) {
        log.info("Entering the getAllPreparedCommandes method from the CheckingService with date {}", date);

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
    public List<CommandeZoneResponseDto> getAllPreparedCommandesZone(String date) {
        log.info("Entering the getAllPreparedCommandesZone method from the CheckingService with date {}", date);

        Integer companyId = this.customUserDetailsService.getCurrentCompanyId();
        log.info("Company ID fetched from the service {}", companyId);

        Integer utilisateurId = this.customUserDetailsService.getUtilisateurId();
        log.info("Utilisateur ID fetched from the service {}", utilisateurId);

        List<CommandeZone> commandes = this.commandeZoneRepository.getPreparedCommandesZone(companyId, utilisateurId, date);
        log.info("Fetched the commande to check from the repo with length {}", commandes.size());

        List<CommandeZoneResponseDto> response = commandes.stream().map(CommandeZoneMapper::toCommandeZoneResponseDto).toList();

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
    public Integer setControlledQuantity(Integer cmpId, Integer id, String type, String stkCode, Integer no, Integer qte, Integer motif) throws Exception {
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

    @Override
    public Integer setCommandeControlled(CmdIdDto id) throws Exception {
        log.info("Entering the setCommandeControlled method from the CheckingService with {}", id);

        String username = this.customUserDetailsService.getCurrentUserCode();
        log.info("Getting the logged in user from the customUserDetailsService {}", username);

        Integer response = this.commandeRepository.setCommandeControlled(
                id.getCmpId(),
                id.getId(),
                id.getStkCode(),
                id.getType(),
                username
        );

        log.info("Réponse de la procédure stockée pour marquer la commande comme contrôlée : {}", response);

        if(response != 0)
            throw new Exception("Erreur lors de la mise à jour de la commande");

        return response;
    }

    @Override
    public Integer startControleZone(int v_vbz_cmp_id, int v_vbz_vnt_id, String v_vbz_vnt_type, String v_vbz_stk_code, int v_vbz_zone) throws Exception {
        log.info("Entering the method startControleZone from the CheckingService");

        Integer verificateurId = this.customUserDetailsService.getUtilisateurId();
        log.info("Fetched the verificateur id from the repo {}", verificateurId);

        Integer isControlStarted = this.commandeZoneRepository.isControlStartedByOther(
                v_vbz_cmp_id,
                v_vbz_vnt_id,
                v_vbz_vnt_type,
                v_vbz_stk_code,
                v_vbz_zone,
                verificateurId
        );

        log.info("Valeur de retour de la requête isControlStartedByOther : {}", isControlStarted);

        if(isControlStarted != 0)
            throw new Exception("Le contrôle a été déjà commencé par un autre contrôleur");

        Integer response = this.commandeZoneRepository.startControleZone(
                v_vbz_cmp_id,
                v_vbz_vnt_id,
                v_vbz_vnt_type,
                v_vbz_stk_code,
                v_vbz_zone,
                verificateurId
        );

        log.info("Valeur de retour de la procédure pour commencer le contrôle de la commande par zone est de : {}", response);

        if(response != 0)
            throw new Exception("Erreur lors du début du contrôle de la commande par zone");

        return response;
    }

    @Override
    public Integer setControlledQuantityZone(LigneQteZoneDto ligne) throws Exception {
        log.info("Entering the setControlledQuantityZone method from the CheckingService with ligne {}", ligne);

        Integer response = this.venteZoneDetailsRepository.setControlledQuantityZone(
                ligne.getCmpId(),
                ligne.getId(),
                ligne.getType(),
                ligne.getType(),
//                ligne.getZone(),
                ligne.getNo(),
                ligne.getQte(),
                ligne.getMotif()
        );

        log.info("Réponse de la requête de mise à jour de la quantité contrôlée pour les commandes par zone est de : {}", response);

        if(response == 0)
            throw new Exception("Une erreur est survenu lors de la mise à jour de la quantité contrôlée pour la commande par zone");

        return response;
    }

    @Override
    public Integer setCommandeZoneControlled(CmdZoneIdDto id) throws Exception {
        log.info("Entering the setCommandeZoneControlled method from the CheckingService with {}", id);

        String username = this.customUserDetailsService.getCurrentUserCode();
        log.info("Getting the logged in user from the customUserDetailsService {}", username);

        Integer response = this.commandeZoneRepository.setCommandeZoneControlled(
                id.getCmpId(),
                id.getId(),
                id.getType(),
                id.getStkCode(),
                id.getZone(),
                username
        );
        log.info("Réponse de la procédure stockée pour marquer la commande zone comme contrôlée {}", response);
        if(response != 0)
            throw new Exception("Erreur lors de la mise à jour de la commande par zone");

        return response;
    }
}
