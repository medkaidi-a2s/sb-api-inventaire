package dz.a2s.a2spreparation.services.impl;

import dz.a2s.a2spreparation.dto.CommandeResponseDto;
import dz.a2s.a2spreparation.dto.affectation.CmdIdDto;
import dz.a2s.a2spreparation.dto.affectation.CmdZoneIdDto;
import dz.a2s.a2spreparation.dto.auth.AuthorizationTypes;
import dz.a2s.a2spreparation.dto.preparation.*;
import dz.a2s.a2spreparation.dto.preparation.request.AddLotRequest;
import dz.a2s.a2spreparation.dto.preparation.request.ReplaceLotRequest;
import dz.a2s.a2spreparation.dto.preparation.response.ProductLotDto;
import dz.a2s.a2spreparation.entities.enums.AuthorizationCodes;
import dz.a2s.a2spreparation.entities.enums.TIER_TYPES;
import dz.a2s.a2spreparation.entities.keys.StkListesId;
import dz.a2s.a2spreparation.entities.keys.VenteId;
import dz.a2s.a2spreparation.entities.views.*;
import dz.a2s.a2spreparation.exceptions.ActionNotAllowedException;
import dz.a2s.a2spreparation.exceptions.AppErrorCodes;
import dz.a2s.a2spreparation.exceptions.DatabaseErrorException;
import dz.a2s.a2spreparation.exceptions.RessourceNotFoundException;
import dz.a2s.a2spreparation.mappers.CommandeMapper;
import dz.a2s.a2spreparation.mappers.StockMapper;
import dz.a2s.a2spreparation.mappers.preparation.PrpCdePrlvUsrCodeMapper;
import dz.a2s.a2spreparation.mappers.preparation.VenteDetailsMapper;
import dz.a2s.a2spreparation.mappers.preparation.VentePrlvDetailsMapper;
import dz.a2s.a2spreparation.repositories.views.*;
import dz.a2s.a2spreparation.services.AuthorizationService;
import dz.a2s.a2spreparation.services.CustomUserDetailsService;
import dz.a2s.a2spreparation.services.PreparationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PreparationServiceImpl implements PreparationService {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final CustomUserDetailsService customUserDetailsService;
    private final PrpCdePrlvUsrCodeRepository prpCdePrlvUsrCodeRepository;
    private final VentePrlvDetailsRepository ventePrlvDetailsRepository;
    private final VenteDetailsRepository venteDetailsRepository;
    private final VenteZoneDetailsRepository venteZoneDetailsRepository;
    private final CommandeRepository commandeRepository;
    private final CommandeZoneRepository commandeZoneRepository;
    private final MotifRepository motifRepository;
    private final StockRepository stockRepository;
    private final StockMapper stockMapper;
    private final AuthorizationService authorizationService;

    @Override
    public List<CommandeResponseDto> getCommandes(String date) {
        log.info("| Entry | PreparationService.getCommandes | Args | date : {}", date);

        if (!date.isEmpty())
            LocalDate.parse(date, DATE_FORMATTER);

        var preparateurId = this.customUserDetailsService.getUtilisateurId(TIER_TYPES.PREPARATEUR.getType());
        log.info("ID du préparateur recuperé : {}", preparateurId);

        var companyId = this.customUserDetailsService.getCurrentCompanyId();

        log.info("Fetching the liste of orders by preparateur from the repo");
        List<Commande> commandes = this.commandeRepository.getCommandesParPreparateur(preparateurId, companyId, date);
        List<CommandeResponseDto> response = commandes.stream().map(CommandeMapper::toCommandeResponseDto).toList();

        log.info("Returning fetched data to controller with length {}", response.size());

        return response;
    }

    @Override
    public List<PrpCmdPrlvUsrCodeDto> getCommandesPrlv(String date) {
        log.info("Entering the getCommandesPrlv method from the PreparationService with date {}", date);

        if (!date.isEmpty())
            LocalDate.parse(date, DATE_FORMATTER);

        String username = this.customUserDetailsService.getCurrentUserCode();
        log.info("Entering the getCommandesPrlv method from the PreparationService with username {}", username);

        log.info("Fetching the liste of orders par prélevement by preparateur from the repo");
        List<PrpCdePrlvUsrCode> commandes = this.prpCdePrlvUsrCodeRepository.getCmdPrlvParPreparateur(username, date);
        List<PrpCmdPrlvUsrCodeDto> response = commandes.stream().map(PrpCdePrlvUsrCodeMapper::toPrpCmdPrlvUsrCodeDto).toList();

        log.info("Returning fetched data to controller with length {}", response.size());

        return response;
    }

    @Transactional
    @Override
    public Integer startPreparePrlv(int cmdId, int id, String type, int annee) throws Exception {
        log.info("Entering the method startPreparePrlv from the PreparationService");

        Integer response = this.prpCdePrlvUsrCodeRepository.startPreparePrlv(
                cmdId,
                id,
                type,
                annee
        );

        log.info("Valeur de retour de la stored procedure for starting preparation with prlv is {}", response);

        if (response != 0)
            throw new RuntimeException("La préparation de la commande (par prélèvement) n'a pas pu être démarrée");

        return response;
    }

    @Transactional
    @Override
    public Integer startPrepareCde(int cmpId, int id, String type, String stkCode) throws Exception {
        log.info("Entering the method startPrepareCde from the PreparationService");

        Integer response = this.commandeRepository.startPrepareCde(
                cmpId,
                id,
                type,
                stkCode
        );

        log.info("Exécution de la procédure stockée pour débuter la préparation de commande : valeur de retour = {}", response);

        if (response != 0) {
            if (response == 1)
                throw new ActionNotAllowedException(AppErrorCodes.CDE_EN_PREPARATION.getMessage());
            throw new RuntimeException("La préparation de la commande n'a pas pu être commencée - [stored procedure return value : " + response + "]");
        }

        return response;
    }

    @Transactional
    @Override
    public Integer startPrepareZone(int cmpId, int id, String type, String stkCode, int zone) throws Exception {
        log.info("Point d'entrée à la méthode startPrepareZone du PreparationService");

        Integer preparateurId = this.customUserDetailsService.getUtilisateurId(TIER_TYPES.PREPARATEUR.getType());
        log.info("Récupération de l'id du préparateur : {}", preparateurId);

        Integer response = this.commandeZoneRepository.startPrepareZone(
                cmpId,
                id,
                type,
                stkCode,
                zone,
                preparateurId
        );

        log.info("Exécution de la procédure stockée pour débuter la préparation de commande (par zone) : valeur de retour = {}", response);

        if (response != 0) {
            if (response == 1)
                throw new ActionNotAllowedException(AppErrorCodes.CDE_EN_PREPARATION.getMessage());
            throw new RuntimeException("La préparation de la commande n'a pas pu être commencée - [stored procedure return value : " + response + "]");
        }

        return response;
    }

    @Override
    public PrpCmdPrlvUsrCodeDto getOneCmdPrlv(Integer id, String type, Integer annee) {
        log.info("Entering the getOneCmdPrlv method from the PreparationService");

        Integer companyId = this.customUserDetailsService.getCurrentCompanyId();
        log.info("Company Id retrieved {}", companyId);

        PrpCdePrlvUsrCode commande = this.prpCdePrlvUsrCodeRepository.getOneCmdPrlv(
                companyId,
                id,
                type,
                annee
        );

        if (commande == null)
            throw new RessourceNotFoundException("Commande demandée introuvable");

        PrpCmdPrlvUsrCodeDto response = PrpCdePrlvUsrCodeMapper.toPrpCmdPrlvUsrCodeDto(commande);

        return response;
    }

    @Override
    public List<LignePrlvDto> getDetailsVentePrlv(StkListesId id) {
        log.info("Entering the getDetailsVentePrlv method from the PreparationService with {}", id);

        List<VentePrlvDetails> details = this.ventePrlvDetailsRepository.getDetailsByVente(
                id.getSltCmpId(),
                id.getSltId(),
                id.getSltType(),
                id.getSltAnnee()
        );

        List<LignePrlvDto> response = details.stream().map(VentePrlvDetailsMapper::toLignePrlvDto).toList();

        return response;
    }

    @Override
    public List<LigneDto> getDetailsVente(VenteId id) {
        log.info("Entering the getDetailsVente method from the PreparationService");

        List<VenteDetails> details = this.venteDetailsRepository.getDetailsByVente(
                id.getVntCmpId(),
                id.getVntId(),
                id.getVntType(),
                id.getVntStkCode()
        );

        log.info("Retruning results from the PreparationService with length {}", details.size());
        List<LigneDto> response = details.stream().map(VenteDetailsMapper::toLigneDto).toList();

        return response;
    }

    @Override
    public List<LigneZoneDto> getDetailsVenteZone(CmdZoneIdDto id) {
        log.info("Entering the getDetailsVenteZone method from the PreparationService");

        List<VenteZoneDetails> details = this.venteZoneDetailsRepository.getDetailsByVenteZone(
                id.getCmpId(),
                id.getId(),
                id.getType(),
                id.getStkCode(),
                id.getZone()
        );

        log.info("Retruning results from the PreparationService with length {}", details.size());
        List<LigneZoneDto> response = details.stream().map(VenteDetailsMapper::toLigneZoneDto).toList();

        return response;
    }

    @Transactional
    @Override
    public Integer setPreparedQuantity(Integer cmpId, Integer id, String type, String stkCode, Integer no, Integer qte, Integer motif) throws Exception {
        log.info("Entering the setPreparedQuantity method from the PreparationService");

        Integer response = this.venteDetailsRepository.setPreparedQuantity(
                cmpId,
                id,
                type,
                stkCode,
                no,
                qte,
                motif
        );

        log.info("Réponse de la requête de mise à jour de la quantité préparée {}", response);

        if (response == 0)
            throw new ActionNotAllowedException(AppErrorCodes.PRODUIT_PREPARE.getMessage());

        return response;
    }

    @Transactional
    @Override
    public Integer setPreparedQuantityZone(LigneQteZoneDto ligne) throws Exception {
        log.info("Entering the setPreparedQuantity method from the PreparationService with ligne {}", ligne);

        Integer response = this.venteZoneDetailsRepository.setPreparedQuantityZone(
                ligne.getCmpId(),
                ligne.getId(),
                ligne.getType(),
                ligne.getType(),
                ligne.getNo(),
                ligne.getQte(),
                ligne.getMotif()
        );

        log.info("Réponse de la requête de mise à jour de la quantité préparée {}", response);

        if (response == 0)
            throw new ActionNotAllowedException(AppErrorCodes.PRODUIT_PREPARE.getMessage());

        return response;
    }

    @Override
    public List<Motif> getAllMotif() {
        log.info("Entering the geAllMotif method from the PreparationService");

        List<Motif> motifs = this.motifRepository.getAll();

        log.info("Fetched the motifs from the repo {}", motifs);

        return motifs;
    }

    @Transactional
    @Override
    public Integer setCommandePrepared(CmdIdDto id) throws Exception {
        log.info("Entering the setCommandePrepared method from the PreparationService with {}", id);

        String username = this.customUserDetailsService.getCurrentUserCode();
        log.info("Getting the logged in user from the customUserDetailsService {}", username);

        Integer response = this.commandeRepository.setCommandePrepared(
                id.getCmpId(),
                id.getId(),
                id.getStkCode(),
                id.getType(),
                username
        );

        log.info("Réponse de la procédure stockée pour marquer la commande comme préparée {}", response);

        if (response != 0) {
            if (response == 105)
                throw new ActionNotAllowedException(AppErrorCodes.CDE_PREPARE.getMessage());
            else if (response == 106)
                throw new ActionNotAllowedException(AppErrorCodes.PRESENCE_PRODUIT_INVALIDE.getMessage());
            throw new RuntimeException("La préparation de la commande n'a pas pu être terminée - [stored procedure return value : " + response + "]");
        }

        return response;
    }

    @Transactional
    @Override
    public Integer setCommandeZonePrepared(CmdZoneIdDto id) throws Exception {
        log.info("Entering the setCommandeZonePrepared method from the PreparationService with {}", id);

        log.info("Récupération du user code de l'utilisateur authentifié");
        String username = this.customUserDetailsService.getCurrentUserCode();

        Integer response = this.commandeZoneRepository.setCommandeZonePrepared(
                id.getCmpId(),
                id.getId(),
                id.getType(),
                id.getStkCode(),
                id.getZone(),
                username
        );
        log.info("Réponse de la procédure stockée pour marquer la commande zone comme préparée {}", response);

        if (response != 0) {
            if (response == 105)
                throw new ActionNotAllowedException(AppErrorCodes.CDE_PREPARE.getMessage());
            else if (response == 106)
                throw new ActionNotAllowedException(AppErrorCodes.PRESENCE_PRODUIT_INVALIDE.getMessage());
            throw new RuntimeException("La préparation de la commande n'a pas pu être terminée (par zone) - [stored procedure return value : " + response + "]");
        }

        return response;
    }

    @Override
    public CommandeReceiptData getReceiptParCommande(CmdIdDto id) {
        log.info("| Entry | PreparationService.getReceiptParCommande | Args | id={}", id);

        var receiptProjection = this.commandeRepository.getReceiptData(id.getCmpId(), id.getId(), id.getType(), id.getStkCode());
        log.info("Fetched the receipt data from the repo | receiptData={}", receiptProjection);

        return CommandeMapper.toReceiptData(receiptProjection);
    }

    @Override
    public CommandeReceiptData getReceiptParZone(CmdZoneIdDto id) {
        log.info("| Entry | PreparationService.getReceiptParZone | Args | id={}", id);

        var receiptProjection = this.commandeZoneRepository.getReceiptData(id.getCmpId(), id.getId(), id.getType(), id.getStkCode(), id.getZone());
        log.info("Fetched the receipt data from the repo | receptData={}", receiptProjection);

        return CommandeMapper.toReceiptData(receiptProjection);
    }

    @Transactional
    @Override
    public Integer deleteLigneCommande(LigneVenteDto ligne) {
        log.info("| Entry | PreparationService.deleteLigneCommande | Args | ligne={}", ligne);

        if (!this.authorizationService.hasAuthorization(AuthorizationCodes.COMMANDES_EDIT.getCode(), AuthorizationTypes.READ) &&
                !this.authorizationService.hasAuthorization(AuthorizationCodes.COMMANDES_LIST.getCode(), AuthorizationTypes.READ)) {
            log.error("Action not allowed - code : {} or {}", AuthorizationCodes.COMMANDES_EDIT.getCode(), AuthorizationCodes.COMMANDES_LIST.getCode());
            throw new ActionNotAllowedException("Action refusée : droit non accordé (code : " + AuthorizationCodes.COMMANDES_EDIT.getCode() + " ou " + AuthorizationCodes.COMMANDES_LIST.getCode() + " - Lecture)");
        }

        Integer code = this.authorizationService.hasAuthorization(AuthorizationCodes.COMMANDES_EDIT.getCode(), AuthorizationTypes.READ) ?
                AuthorizationCodes.COMMANDES_EDIT.getCode() : AuthorizationCodes.COMMANDES_LIST.getCode();

        if (!this.authorizationService.hasAuthorization(code, AuthorizationTypes.DELETE)) {

            log.error("Action not allowed - code : {} - DELETE", code);
            throw new ActionNotAllowedException("Action refusée : droit non accordé (code : " + code + " - Suppression)");
        }

        log.info("Récupération du user code de l'utilisateur authentifié");
        String username = this.customUserDetailsService.getCurrentUserCode();

        var response = 0;

        try {
            response = this.venteDetailsRepository.deleteLigneCommande(
                    ligne.getCmpId(),
                    ligne.getId(),
                    ligne.getStkCode(),
                    ligne.getType(),
                    ligne.getNo(),
                    username
            );
        } catch (DataAccessException exception) {
            log.info("Une erreur s'est produite lors de la suppression de la ligne de commande | Original message: {}", exception.getMessage());
            throw new DatabaseErrorException("Une erreur s'est produite lors de la suppression de la ligne de commande");
        }
        log.info("Réponse de la procédure stockée pour supprimer une ligne de commande {}", response);

        if (response != 0)
            throw new ActionNotAllowedException("La suppression de la ligne de commande n'a pas pu avoir lieu");

        return response;
    }

    @Transactional
    @Override
    public Integer editQuantityCommande(LigneQteDto ligne) {
        log.info("| Entry | PreparationService.editQuantityCommande | Args | ligne={}", ligne);

        if (!this.authorizationService.hasAuthorization(AuthorizationCodes.COMMANDES_EDIT.getCode(), AuthorizationTypes.READ) &&
                !this.authorizationService.hasAuthorization(AuthorizationCodes.COMMANDES_LIST.getCode(), AuthorizationTypes.READ)) {
            log.error("Action not allowed - code : {} or {}", AuthorizationCodes.COMMANDES_EDIT.getCode(), AuthorizationCodes.COMMANDES_LIST.getCode());
            throw new ActionNotAllowedException("Action refusée : droit non accordé (code : " + AuthorizationCodes.COMMANDES_EDIT.getCode() + " ou " + AuthorizationCodes.COMMANDES_LIST.getCode() + " - Lecture)");
        }

        Integer code = this.authorizationService.hasAuthorization(AuthorizationCodes.COMMANDES_EDIT.getCode(), AuthorizationTypes.READ) ?
                AuthorizationCodes.COMMANDES_EDIT.getCode() : AuthorizationCodes.COMMANDES_LIST.getCode();

        if (!this.authorizationService.hasAuthorization(code, AuthorizationTypes.UPDATE)) {

            log.error("Action not allowed - code : {} - UPDATE", code);
            throw new ActionNotAllowedException("Action refusée : droit non accordé (code : " + code + " - Modification)");
        }

        log.info("Récupération du user code de l'utilisateur authentifié");
        String username = this.customUserDetailsService.getCurrentUserCode();

        var response = 0;

        try {
            response = this.venteDetailsRepository.editQuantityCommande(
                    ligne.getCmpId(),
                    ligne.getId(),
                    ligne.getStkCode(),
                    ligne.getType(),
                    ligne.getNo(),
                    ligne.getQte(),
                    username
            );
        } catch (DataAccessException exception) {
            log.info("Une erreur s'est produite lors de la modification de la quantité de la ligne de commande | Original message: {}", exception.getMessage());
            throw new DatabaseErrorException("Une erreur s'est produite lors de la modification de la quantité de la ligne de commande");
        }
        log.info("Réponse de la procédure stockée pour modifier la quantité d'une ligne de commande {}", response);

        if (response != 0)
            throw new ActionNotAllowedException("La modification de la quantité de la ligne de commande n'a pas pu avoir lieu");

        return response;
    }

    @Override
    public List<ProductLotDto> getAvailableLots(Integer cmpId, Integer medId, Integer oldLotId, Integer qte) {
        log.info("| Entry | PreparationService.getAvailableLots | Args | cmpId={}, medId={}, oldLotId={}", cmpId, medId, oldLotId);

        var projections = this.stockRepository.getAvailableLots(cmpId, medId, oldLotId, qte);
        log.info("Fetched the available lots from the repo | projections.size={}", projections.size());

        var lots = projections.stream().map(this.stockMapper::fromProductLotProjection).toList();
        log.info("Mapped the projections to DTOs | lots.size={}", lots.size());

        return lots;
    }

    @Transactional
    @Override
    public Integer replaceProductLot(ReplaceLotRequest request) {
        log.info("| Entry | PreparationService.replaceProductLot | Args | request={}", request);

        if (!this.authorizationService.hasAuthorization(AuthorizationCodes.COMMANDES_EDIT.getCode(), AuthorizationTypes.READ) &&
                !this.authorizationService.hasAuthorization(AuthorizationCodes.COMMANDES_LIST.getCode(), AuthorizationTypes.READ)) {
            log.error("Action not allowed - code : {} or {}", AuthorizationCodes.COMMANDES_EDIT.getCode(), AuthorizationCodes.COMMANDES_LIST.getCode());
            throw new ActionNotAllowedException("Action refusée : droit non accordé (code : " + AuthorizationCodes.COMMANDES_EDIT.getCode() + " ou " + AuthorizationCodes.COMMANDES_LIST.getCode() + " - Lecture)");
        }

        Integer code = this.authorizationService.hasAuthorization(AuthorizationCodes.COMMANDES_EDIT.getCode(), AuthorizationTypes.READ) ?
                AuthorizationCodes.COMMANDES_EDIT.getCode() : AuthorizationCodes.COMMANDES_LIST.getCode();

        if (!this.authorizationService.hasAuthorization(code, AuthorizationTypes.RUN)) {

            log.error("Action not allowed - code : {} - RUN", code);
            throw new ActionNotAllowedException("Action refusée : droit non accordé (code : " + code + " - Exécution)");
        }

        log.info("Récupération du user code de l'utilisateur authentifié");
        String username = this.customUserDetailsService.getCurrentUserCode();

        var response = 0;

        try {
            response = this.venteDetailsRepository.replaceProductLot(
                    request.getCmpId(),
                    request.getId(),
                    request.getStkCode(),
                    request.getType(),
                    request.getNo(),
                    request.getNewLotId(),
                    username
            );
        } catch (DataAccessException exception) {
            log.info("Une erreur s'est produite lors du remplacement du lot de la ligne de commande | Original message: {}", exception.getMessage());
            throw new DatabaseErrorException("Une erreur s'est produite lors du remplacement du lot de la ligne de commande");
        }
        log.info("Réponse de la procédure stockée pour remplacer le lot d'une ligne de commande {}", response);

        if (response != 0)
            throw new ActionNotAllowedException("Le remplacement du lot de la ligne de commande n'a pas pu avoir lieu");

        return response;
    }

    @Transactional
    @Override
    public Integer addProductLot(AddLotRequest request) {
        log.info("| Entry | PreparationService.addProductLot | Args | request={}", request);

        if (!this.authorizationService.hasAuthorization(AuthorizationCodes.COMMANDES_EDIT.getCode(), AuthorizationTypes.READ) &&
                !this.authorizationService.hasAuthorization(AuthorizationCodes.COMMANDES_LIST.getCode(), AuthorizationTypes.READ)) {
            log.error("Action not allowed - code : {} or {}", AuthorizationCodes.COMMANDES_EDIT.getCode(), AuthorizationCodes.COMMANDES_LIST.getCode());
            throw new ActionNotAllowedException("Action refusée : droit non accordé (code : " + AuthorizationCodes.COMMANDES_EDIT.getCode() + " ou " + AuthorizationCodes.COMMANDES_LIST.getCode() + " - Lecture)");
        }

        Integer code = this.authorizationService.hasAuthorization(AuthorizationCodes.COMMANDES_EDIT.getCode(), AuthorizationTypes.READ) ?
                AuthorizationCodes.COMMANDES_EDIT.getCode() : AuthorizationCodes.COMMANDES_LIST.getCode();

        if (!this.authorizationService.hasAuthorization(code, AuthorizationTypes.INSERT)) {
            log.error("Action not allowed - code : {} - INSERT", code);
            throw new ActionNotAllowedException("Action refusée : droit non accordé (code : " + code + " - Ajout)");
        }

        log.info("Récupération du user code de l'utilisateur authentifié");
        String username = this.customUserDetailsService.getCurrentUserCode();

        var response = 0;

        try {
            response = this.venteDetailsRepository.addProductLot(
                    request.getCmpId(),
                    request.getId(),
                    request.getStkCode(),
                    request.getType(),
                    request.getMedId(),
                    request.getLotId(),
                    request.getQuantity(),
                    username
            );
        } catch (DataAccessException exception) {
            log.info("Une erreur s'est produite lors de l'ajout du lot à la commande | Original message: {}", exception.getMessage());
            throw new DatabaseErrorException("Une erreur s'est produite lors de l'ajout du lot à la commande");
        }
        log.info("Réponse de la procédure stockée pour ajouter un lot à une commande {}", response);

        if (response != 0)
            throw new ActionNotAllowedException("L'ajout du lot à la commande n'a pas pu avoir lieu");

        return response;
    }
}
