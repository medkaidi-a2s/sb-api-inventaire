package dz.a2s.a2spreparation.services.impl;

import dz.a2s.a2spreparation.dto.affectation.*;
import dz.a2s.a2spreparation.entities.views.*;
import dz.a2s.a2spreparation.exceptions.RessourceNotFoundException;
import dz.a2s.a2spreparation.mappers.affectation.CmdPrlvMapper;
import dz.a2s.a2spreparation.mappers.affectation.CommandeMapper;
import dz.a2s.a2spreparation.repositories.DiMessagesRepository;
import dz.a2s.a2spreparation.repositories.views.*;
import dz.a2s.a2spreparation.services.AffectationService;
import dz.a2s.a2spreparation.services.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AffectationServiceImpl implements AffectationService {

    private final PrpListeCdeZonesRepository prpListeCdeZonesRepository;
    private final PrpCommandeRepository prpCommandeRepository;
    private final PrpPrepareControleRepository prpPrepareControleRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final DiMessagesRepository diMessagesRepository;
    private final PrpCdePrlvRepository prpCdePrlvRepository;
    private final PrpCdePrepContRepository prpCdePrepContRepository;
    private final PrpCdePrlvPrepContRepository prpCdePrlvPrepContRepository;

    @Override
    public List<AffZoneDto> getListCmdZones() throws RessourceNotFoundException {
        log.info("Entering getListCmdZones method from the AffectationService");

        Integer companyId = this.customUserDetailsService.getCurrentCompanyId();
        log.info("Fetching orders for the company {}", companyId);

        String preparationZone = this.customUserDetailsService.getPreparationZone();
        log.info("Fetching the preparation zone for the logged user {}", preparationZone);

        Integer preparateurId = this.customUserDetailsService.getUtilisateurId();
        log.info("Fetching the current user id to filter the orders {}", preparateurId);

        log.info("Fetching data from the repo");
        List<PrpCdeZone> listeCommandes = this.prpListeCdeZonesRepository.getListCmdZones(companyId, preparationZone, preparateurId);
        log.info("Data fetched from the repo length = {}", listeCommandes.size());

        log.info("Mapping entity classes to DTOs");
        List<AffZoneDto> commandes = listeCommandes.stream().map(CommandeMapper::toAffZoneCmdDto).toList();
        log.info("Entity classes mapped to DTOs with length {}", commandes.size());

        return commandes;
    }

    @Override
    public List<PrpCmdDto> getListCmd(String date) {
        log.info("Entering getListCommande method from the AffectationService");

        Integer companyId = this.customUserDetailsService.getCurrentCompanyId();
        log.info("Fetching orders for the company {}", companyId);
        List<PrpCommande> listeCommandes = this.prpCommandeRepository.getListCommande(companyId, date);
        log.info("Data fetched from the repo length = {}", listeCommandes.size());

        log.info("Mapping entity classes to DTOs");
        List<PrpCmdDto> commandes = listeCommandes.stream().map(CommandeMapper::toPrpCmdDto).toList();
        log.info("Entity classes mapped to DTOs with length {}", commandes.size());

        return commandes;
    }

    @Override
    public List<AffCmdDto> getListCmdAssigned(String date) {
        log.info("Entering getListCommandeAssigned method from the AffectationService");

        Integer companyId = this.customUserDetailsService.getCurrentCompanyId();
        log.info("Fetching assigned orders for the company {}", companyId);

        List<PrpCommande> listeCommandes = this.prpCommandeRepository.getListCommandeAssigned(companyId, date);
        log.info("Data fetched from the repo length = {}", listeCommandes.size());

        log.info("Mapping entity classes to DTOs");
        List<AffCmdDto> commandes = listeCommandes.stream().map(CommandeMapper::toAffCmdDto).toList();
        log.info("Entity classes mapped to DTOs with length {}", commandes.size());

        return commandes;
    }

    @Override
    public List<PrpCmdPrlvDto> getListCmdPrlv(String date) {
        log.info("Entering the getListeCommandesPrlv from the AffectationService with date {}", date);

        log.info("Fetching liste des commandes from the repo");
        List<PrpCdePrlv> listeCommandes = this.prpCdePrlvRepository.getListeCommandesPrlv(date);
        log.info("Data fetched from the repo with length {}", listeCommandes.size());

        log.info("Mapping class entities to DTOs");
        List<PrpCmdPrlvDto> commandes = listeCommandes.stream().map(CmdPrlvMapper::toPrpCommand).toList();
        log.info("Entity classes mapped to DTOs with length {}", commandes.size());

        return commandes;
    }

    @Override
    public List<AffCmdPrlvDto> getListCmdPrlvAssigned(String date) {
        log.info("Entering the getListeCommandesPrlvAssigned from the AffectationService with date {}", date);

        log.info("Fetching liste des commandes par prélévement déjà affectées from the repo");
        List<PrpCdePrlv> listeCommandes = this.prpCdePrlvRepository.getListCmdPrlvAssigned(date);
        log.info("Data fetched from the repo with length {}", listeCommandes.size());

        log.info("Mapping class entities to DTOs");
        List<AffCmdPrlvDto> commandes = listeCommandes.stream().map(CmdPrlvMapper::toAffCommand).toList();
        log.info("Entity classes mapped to DTOs with length {}", commandes.size());

        return commandes;
    }

    @Override
    public List<PrpPrepareControle> getAllPreparateurs() {
        log.info("Entering getAllPreparateurs method from the AffectationService");

        log.info("Fetching data from the repository");
        List<PrpPrepareControle> preparateurs = this.prpPrepareControleRepository.getAllPreparateurs();
        log.info("Data fetched from the repository with length {}", preparateurs.size());

        if(preparateurs.isEmpty())
            throw new RessourceNotFoundException(("Liste des préparateurs vide"));

        return preparateurs;
    }

    @Override
    public List<PrpPrepareControle> getAllControleurs() {
        log.info("Entering getAllPreparateurs method from the AffectationService");

        log.info("Fetching data from the repository");
        List<PrpPrepareControle> controleurs = this.prpPrepareControleRepository.getAllControleurs();
        log.info("Data fetched from the repository with length {}", controleurs.size());

        if(controleurs.isEmpty())
            throw new RessourceNotFoundException(("Liste des contrôleurs vide"));

        return controleurs;
    }

    @Override
    public AffectCmdResultDto affectCmdPrp(int p_cmp, int p_vnt, String p_stk, String p_type, int p_prp, int p_cnt1, int p_cnt2, String p_user, String reference) {
        AffectCmdResultDto result;
        int response = this.prpCommandeRepository.affectCommandePrp(
                p_cmp,
            p_vnt,
            p_stk,
            p_type,
            p_prp,
            p_cnt1,
            p_cnt2,
            p_user
        );

        log.info("La réponse de la procédure stockée affectation des commandes {}", response);

        if(response == 0)
            result = AffectCmdResultDto.builder().messageId(0).message("Affectation réussie").venteRef(reference).build();
        else {
            String message = this.diMessagesRepository.getMsgDescLocById(response);
            result = AffectCmdResultDto.builder().messageId(response).message(message == null ? "Une erreur inconnue s'est produite" : message).venteRef(reference).build();
        }

        return result;
    }

    @Override
    public PrpCdePrepCont editAffectCmdPrp(int p_cmp, int p_vnt, String p_stk, String p_type, int p_prp, int p_cnt1, int p_cnt2) throws Exception {
        log.info("Entering editAffectCmdPrp method from the AffectationService");

        String username = this.customUserDetailsService.getCurrentUserCode();
        log.info("Entering the getCommandes method from the PreparationService with username {}", username);

        Integer response = this.prpCommandeRepository.editAffectCommandePrp(
                p_cmp,
                p_vnt,
                p_stk,
                p_type,
                p_prp,
                p_cnt1,
                p_cnt2,
                username
        );

        log.info("Réponse de la procédure stockée de réaffectation des commandes {}", response);

        if(response != 0)
            throw new Exception("La réaffectation de cette commande n'a pas pu s'effectuer");

        PrpCdePrepCont entity = this.prpCdePrepContRepository.getPrepCont(
                p_cmp,
                p_vnt,
                p_type,
                p_stk
        );

        log.info("Edited entity return from the repo {}", entity);

        return entity;
    }

    @Override
    public AffectCmdResultDto affectCmdPrpPrlv(int p_cmp, int p_slt_id, String p_slt_type, int p_slt_annee, int p_prp, int p_cnt1, int p_cnt2, String p_user, String reference) {
        log.info("Entering the affectCmdPrpPrlv from the AffectationService");
        AffectCmdResultDto result;
        int response = this.prpCdePrlvRepository.affectCommandePrpPrlv(
            p_cmp,
            p_slt_id,
            p_slt_type,
            p_slt_annee,
            p_prp,
            p_cnt1,
            p_cnt2,
            p_user
        );

        log.info("La réponse de la procédure stockée affectation des commandes par prélévement {}", response);

        if(response == 0)
            result = AffectCmdResultDto.builder().messageId(0).message("Affectation réussie").venteRef(reference).build();
        else {
            String message = this.diMessagesRepository.getMsgDescLocById(response);
            result = AffectCmdResultDto.builder().messageId(response).message(message == null ? "Une erreur inconnue s'est produite" : message).venteRef(reference).build();
        }
        return result;
    }

    @Override
    public PrpCdePrlvPrepCont editAffectCmdPrpPrlv(int p_cmp, int p_slt_id, String p_slt_type, int p_slt_annee, int p_prp, int p_cnt1, int p_cnt2) throws Exception {
        log.info("Entering the editAffectCmdPrpPrlv method from the AffectationService");

        Integer response = this.prpCdePrlvPrepContRepository.editAffectCommandePrpPrlv(
                p_cmp,
                p_slt_id,
                p_slt_type,
                p_slt_annee,
                p_prp,
                p_cnt1,
                p_cnt2
        );

        log.info("Result of the stored procedure {}", response);

        if(response != 0)
            throw new Exception("La réaffectation de cette commande n'a pas pu s'effectuer");

        PrpCdePrlvPrepCont entity = this.prpCdePrlvPrepContRepository.getPrepCont(
                p_cmp,
                p_slt_id,
                p_slt_type,
                p_slt_annee
        );

        log.info("Edited entity returned from the repo {}", entity);

        return entity;
    }

    @Override
    public PrpCdePrepCont getPrepCont(Integer vntId, String vntType, String vntStkCode) throws RessourceNotFoundException {
        log.info("Entering getPrepCont method from AffectationService");

        Integer companyId = this.customUserDetailsService.getCurrentCompanyId();

        log.info("Getting the entity from the repo");
        PrpCdePrepCont prpCdePrepCont = this.prpCdePrepContRepository.getPrepCont(companyId, vntId, vntType, vntStkCode);

        if(prpCdePrepCont == null)
            throw new RessourceNotFoundException("Commande introuvable");
        return prpCdePrepCont;
    }

    @Override
    public PrpCdePrlvPrepCont getPrepContPrlv(Integer id, String type, Integer annee) throws RessourceNotFoundException {
        log.info("Entering getPrepContPrlv method from AffectationService");

        Integer companyId = this.customUserDetailsService.getCurrentCompanyId();

        log.info("Getting the entity from the repo");
        PrpCdePrlvPrepCont prpCdePrlvPrepCont = this.prpCdePrlvPrepContRepository.getPrepCont(
                companyId,
                id,
                type,
                annee
        );

        if(prpCdePrlvPrepCont == null)
            throw new RessourceNotFoundException("Commande par prélévement introuvable");

        return prpCdePrlvPrepCont;
    }
}
