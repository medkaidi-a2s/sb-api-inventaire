package dz.a2s.a2spreparation.services.impl;

import dz.a2s.a2spreparation.dto.CommandeResponseDto;
import dz.a2s.a2spreparation.dto.CommandeZoneResponseDto;
import dz.a2s.a2spreparation.dto.affectation.CmdIdDto;
import dz.a2s.a2spreparation.dto.affectation.CmdZoneIdDto;
import dz.a2s.a2spreparation.dto.controle.response.BonCommandeZoneDto;
import dz.a2s.a2spreparation.dto.preparation.LigneQteZoneDto;
import dz.a2s.a2spreparation.entities.enums.TIER_TYPES;
import dz.a2s.a2spreparation.entities.views.Commande;
import dz.a2s.a2spreparation.entities.views.CommandeZone;
import dz.a2s.a2spreparation.exceptions.ActionNotAllowedException;
import dz.a2s.a2spreparation.exceptions.AppErrorCodes;
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
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    private final CommandeZoneMapper commandeZoneMapper;

    @Override
    public List<CommandeResponseDto> getAllPreparedCommandes(String date) {
        log.info("Entering the getAllPreparedCommandes method from the CheckingService with date {}", date);

        Integer companyId = this.customUserDetailsService.getCurrentCompanyId();
        log.info("Company ID fetched from the service {}", companyId);

        Integer utilisateurId = this.customUserDetailsService.getUtilisateurId(TIER_TYPES.CONTROLEUR.getType());
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

        Integer utilisateurId = this.customUserDetailsService.getUtilisateurId(TIER_TYPES.CONTROLEUR.getType());
        log.info("Utilisateur ID fetched from the service {}", utilisateurId);

        List<CommandeZone> commandes = this.commandeZoneRepository.getPreparedCommandesZone(companyId, utilisateurId, date);
        log.info("Fetched the commande to check from the repo with length {}", commandes.size());

        List<CommandeZoneResponseDto> response = commandes.stream().map(CommandeZoneMapper::toCommandeZoneResponseDto).toList();

        return response;
    }

    @Override
    public List<BonCommandeZoneDto> getPreparedBonCommandesZone(String date) {
        log.info("| Entry | CheckingService.getPreparedBonCommandesZone | Args | date : {}", date);

        Integer companyId = this.customUserDetailsService.getCurrentCompanyId();
        log.info("Company ID fetched from the service {}", companyId);

        Integer utilisateurId = this.customUserDetailsService.getUtilisateurId(TIER_TYPES.CONTROLEUR.getType());
        log.info("Utilisateur ID fetched from the service {}", utilisateurId);

        var commandes = this.commandeZoneRepository.getPreparedBonCommandeZone(companyId, utilisateurId, date);
        log.info("Fetched the list of bon de commande par zone from the repo | commandes.size={}", commandes.size());

        List<BonCommandeZoneDto> response = new ArrayList<>();

        if(!commandes.isEmpty())
            response = commandes.stream().map(commandeZoneMapper::toBonCommandeZone).toList();

        return response;
    }

    @Transactional
    @Override
    public Integer startControleCde(int cmdId, int id, String type, String stkCode) throws Exception {
        log.info("Entering the method startControleCde from the CheckingService");

        Integer response = this.commandeRepository.startControleCde(
                cmdId,
                id,
                type,
                stkCode
        );

        log.info("Valeur de retour de la procédure stockée pour commencer le contrôle est de : {}", response);

        if (response != 0) {
            if (response == 1)
                throw new ActionNotAllowedException(AppErrorCodes.CDE_EN_CONTROLE.getMessage());
            throw new RuntimeException("Le contrôle de la commande n'a pas pu être commencé - [stored procedure return value : " + response + "]");
        }

        return response;
    }

    @Transactional
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

        if (response == 0)
            throw new ActionNotAllowedException(AppErrorCodes.PRODUIT_CONTROLE.getMessage());

        return response;
    }

    @Transactional
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

        if (response != 0) {
            if (response == 105)
                throw new ActionNotAllowedException(AppErrorCodes.CDE_CONTROLE.getMessage());
            else if (response == 106)
                throw new ActionNotAllowedException(AppErrorCodes.PRESENCE_PRODUIT_INVALIDE.getMessage());
            throw new RuntimeException("Le contrôle de la commande n'a pas pu être terminée - [stored procedure return value : " + response + "]");
        }

        return response;
    }

    @Transactional
    @Override
    public Integer startControleZone(int cmdId, int id, String type, String stkCode, int zone) throws Exception {
        log.info("Entering the method startControleZone from the CheckingService");

        Integer verificateurId = this.customUserDetailsService.getUtilisateurId(TIER_TYPES.CONTROLEUR.getType());
        log.info("Fetched the verificateur id from the repo {}", verificateurId);

        Integer response = this.commandeZoneRepository.startControleZone(
                cmdId,
                id,
                type,
                stkCode,
                zone,
                verificateurId
        );

        log.info("Valeur de retour de la procédure pour commencer le contrôle de la commande par zone est de : {}", response);

        if (response != 0) {
            if (response == 1)
                throw new ActionNotAllowedException(AppErrorCodes.CDE_EN_CONTROLE.getMessage());
            throw new RuntimeException("Le contrôle de la commande n'a pas pu être commencé (par zone) - [stored procedure return value : " + response + "]");
        }

        return response;
    }

    @Transactional
    @Override
    public Integer startControleCommandeZone(Integer cmpId, Integer id, String type, String stkCode) {
        log.info("| Entry | CheckingService.startControleCommandeZone | Args | v_vbz_cmp_id={}, v_vbz_vnt_id={}, v_vbz_vnt_type={}, v_vbz_stk_code={}", cmpId, id, type, stkCode);

        Integer verificateurId = this.customUserDetailsService.getUtilisateurId(TIER_TYPES.CONTROLEUR.getType());
        var username = this.customUserDetailsService.getCurrentUserCode();
        log.info("Fetched the username and verificateur id from the repo username={}, verificateurId={}", username, verificateurId);

        Integer response = 0;

        try {
            response = this.commandeZoneRepository.startControleCommandeZone(
                    cmpId,
                    id,
                    type,
                    stkCode,
                    verificateurId,
                    username
            );
        } catch(DataAccessException ex) {
            log.info("Une erreur s'est produite lors du lancement du contrôle de la commande par zone | original message = {}", ex.getMessage());
            throw new RuntimeException("Le contrôle de la commande globale n'a pas pu être commencé (par zone");
        }

        log.info("Valeur de retour de la procédure pour commencer le contrôle de la commande par zone est de : {}", response);

        if (response != 0) {
            if (response == 1)
                throw new ActionNotAllowedException(AppErrorCodes.CDE_EN_CONTROLE.getMessage());
            throw new RuntimeException("Le contrôle de la commande globale n'a pas pu être commencé (par zone) - [stored procedure return value : " + response + "]");
        }

        return response;
    }

    @Transactional
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

        if (response == 0)
            throw new ActionNotAllowedException(AppErrorCodes.PRODUIT_CONTROLE.getMessage());

        return response;
    }

    @Transactional
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

        if (response != 0) {
            if (response == 105)
                throw new ActionNotAllowedException(AppErrorCodes.CDE_CONTROLE.getMessage());
            else if (response == 106)
                throw new ActionNotAllowedException(AppErrorCodes.PRESENCE_PRODUIT_INVALIDE.getMessage());
            throw new RuntimeException("Le contrôle de la commande n'a pas pu être terminée - [stored procedure return value : " + response + "]");
        }

        return response;
    }

    @Transactional
    @Override
    public Integer setCommandeZoneGlobalControlled(CmdIdDto id) {
        log.info("| Entry | CheckingService.setCommandeZoneGlobalControlled | Args | id={}", id);

        String username = this.customUserDetailsService.getCurrentUserCode();
        log.info("Fetched the logged in user from the customUserDetailsService {}", username);

        Integer response = 0;

        try {
            response = this.commandeZoneRepository.setCommandeZoneGlobalControlled(
                    id.getCmpId(),
                    id.getId(),
                    id.getType(),
                    id.getStkCode(),
                    username
            );
            log.info("Réponse de la procédure stockée pour marquer la commande zone comme contrôlée {}", response);
        } catch (DataAccessException ex) {
            log.info("Une erreur s'est produite lors du contrôle de la commande par zone | original message = {}", ex.getMessage());
            throw new RuntimeException("Une erreur liée à la base de données s'est produite");
        }

        if (response != 0) {
            if (response == 105)
                throw new ActionNotAllowedException(AppErrorCodes.BON_CDE_CONTROLE.getMessage());
            else if (response == 106)
                throw new ActionNotAllowedException(AppErrorCodes.PRESENCE_PRODUIT_INVALIDE.getMessage());
            throw new RuntimeException("Le contrôle de la commande n'a pas pu être terminée - [stored procedure return value : " + response + "]");
        }

        return response;
    }
}
