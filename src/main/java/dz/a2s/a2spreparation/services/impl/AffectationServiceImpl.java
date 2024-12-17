package dz.a2s.a2spreparation.services.impl;

import dz.a2s.a2spreparation.dto.CommandeResponseDto;
import dz.a2s.a2spreparation.dto.CommandeZoneResponseDto;
import dz.a2s.a2spreparation.dto.affectation.*;
import dz.a2s.a2spreparation.entities.views.*;
import dz.a2s.a2spreparation.exceptions.RessourceNotFoundException;
import dz.a2s.a2spreparation.mappers.CommandeMapper;
import dz.a2s.a2spreparation.mappers.CommandeZoneMapper;
import dz.a2s.a2spreparation.mappers.affectation.CmdPrlvMapper;
import dz.a2s.a2spreparation.repositories.DiMessagesRepository;
import dz.a2s.a2spreparation.repositories.views.*;
import dz.a2s.a2spreparation.services.AffectationService;
import dz.a2s.a2spreparation.services.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AffectationServiceImpl implements AffectationService {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final PrpPrepareControleRepository prpPrepareControleRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final DiMessagesRepository diMessagesRepository;
    private final PrpCdePrlvRepository prpCdePrlvRepository;
    private final PrpCdePrepContRepository prpCdePrepContRepository;
    private final PrpCdePrlvPrepContRepository prpCdePrlvPrepContRepository;
    private final CommandeRepository commandeRepository;
    private final CommandeZoneRepository commandeZoneRepository;

    @Override
    public List<CommandeZoneResponseDto> getListCmdZones(String date) throws RessourceNotFoundException {
        log.info("Point d'entrée de la méthode getListCmdZones du AffectationService avec date {}", date);

        Integer companyId = this.customUserDetailsService.getCurrentCompanyId();
        log.info("Récupération de la companyId : {}", companyId);

        String preparationZone = this.customUserDetailsService.getPreparationZone();
        log.info("Récupération de l'id de la zone de l'utilisateur authentifié : {}", preparationZone);

        Integer preparateurId = this.customUserDetailsService.getUtilisateurId();
        log.info("Fetching the current user id to filter the orders {}", preparateurId);

        log.info("Récupération de la liste des commandes par zone à partir du repo");
        List<CommandeZone> listeCommandes = this.commandeZoneRepository.getListCmdZones(companyId, preparationZone, preparateurId, date);
        log.info("Commandes récupérées du repo avec size : {}", listeCommandes.size());


        log.info("Mappage des entités vers les DTOs");
        List<CommandeZoneResponseDto> commandes = listeCommandes.stream().map(CommandeZoneMapper::toCommandeZoneResponseDto).toList();
        log.info("Mappage terminé avec size : {}", commandes.size());

        return commandes;
    }

    @Override
    public List<CommandeResponseDto> getListCmd(String date) {
        log.info("Entering getListCommande method from the AffectationService with date {}", date);

        if (!date.isEmpty())
            LocalDate.parse(date, DATE_FORMATTER);

        Integer companyId = this.customUserDetailsService.getCurrentCompanyId();
        log.info("Récupération de la companyId {}", companyId);
        List<Commande> listeCommandes = this.commandeRepository.getListCommande(companyId, date);
        log.info("Liste des commandes récupérés à partir du repo = {}", listeCommandes.size());

        log.info("Mappage des entités vers les DTOs");
        List<CommandeResponseDto> commandes = listeCommandes.stream().map(CommandeMapper::toCommandeResponseDto).toList();
        log.info("Mappage de la liste des entités terminé avec size {}", commandes.size());

        return commandes;
    }

    @Override
    public List<CommandeResponseDto> getListCmdAssigned(String date) {
        log.info("Point d'entrée à la méthode getListCommandeAssigned du AffectationService avec date {}", date);

        if (!date.isEmpty())
            LocalDate.parse(date, DATE_FORMATTER);

        Integer companyId = this.customUserDetailsService.getCurrentCompanyId();
        log.info("Récupération de la companyId {}", companyId);

        List<Commande> listeCommandes = this.commandeRepository.getListCommandeAssigned(companyId, date);
        log.info("Liste des commandes déjà affectées récupérées à partir du repo avec size : {}", listeCommandes.size());

        log.info("Mappage des entités vers les DTOs");
        List<CommandeResponseDto> commandes = listeCommandes.stream().map(CommandeMapper::toCommandeResponseDto).toList();
        log.info("Mappage de la liste des entité terminé avec size : {}", commandes.size());

        return commandes;
    }

    @Override
    public List<PrpCmdPrlvDto> getListCmdPrlv(String date) {
        log.info("Entering the getListeCommandesPrlv from the AffectationService with date {}", date);

        if (!date.isEmpty())
            LocalDate.parse(date, DATE_FORMATTER);

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

        if (!date.isEmpty())
            LocalDate.parse(date, DATE_FORMATTER);

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

        if (preparateurs.isEmpty())
            throw new RessourceNotFoundException(("Liste des préparateurs vide"));

        return preparateurs;
    }

    @Override
    public List<PrpPrepareControle> getAllControleurs() {
        log.info("Entering getAllPreparateurs method from the AffectationService");

        log.info("Fetching data from the repository");
        List<PrpPrepareControle> controleurs = this.prpPrepareControleRepository.getAllControleurs();
        log.info("Data fetched from the repository with length {}", controleurs.size());

        if (controleurs.isEmpty())
            throw new RessourceNotFoundException(("Liste des contrôleurs vide"));

        return controleurs;
    }

    @Override
    public AffectCmdResultDto affectCmdPrp(int p_cmp, int p_vnt, String p_stk, String p_type, int p_prp, int p_cnt1, int p_cnt2, String p_user, String reference) {
        AffectCmdResultDto result;

        if(p_cnt2 == 0)
            p_cnt2 = p_cnt1;

        int response = this.commandeRepository.affectCommandePrp(
                p_cmp,
                p_vnt,
                p_stk,
                p_type,
                p_prp,
                p_cnt1,
                p_cnt2,
                p_user
        );

        log.info("La réponse de la procédure stockée pour l'affectation des commandes {}", response);

        if (response == 0)
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

        if (p_cnt2 == 0)
            p_cnt2 = p_cnt1;

        String username = this.customUserDetailsService.getCurrentUserCode();
        log.info("Entering the getCommandes method from the PreparationService with username {}", username);

        Integer response = this.commandeRepository.editAffectCommandePrp(
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

        if (response != 0)
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

        if(p_cnt2 == 0)
            p_cnt2 = p_cnt1;

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

        if (response == 0)
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

        if (p_cnt2 == 0)
            p_cnt2 = p_cnt1;

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

        if (response != 0)
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

        if (prpCdePrepCont == null)
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

        if (prpCdePrlvPrepCont == null)
            throw new RessourceNotFoundException("Commande par prélévement introuvable");

        return prpCdePrlvPrepCont;
    }
}
