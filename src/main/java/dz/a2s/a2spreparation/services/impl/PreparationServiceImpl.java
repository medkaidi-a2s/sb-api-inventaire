package dz.a2s.a2spreparation.services.impl;

import dz.a2s.a2spreparation.dto.affectation.CmdZoneIdDto;
import dz.a2s.a2spreparation.dto.preparation.*;
import dz.a2s.a2spreparation.entities.keys.StkListesId;
import dz.a2s.a2spreparation.entities.keys.VenteId;
import dz.a2s.a2spreparation.entities.views.*;
import dz.a2s.a2spreparation.exceptions.RessourceNotFoundException;
import dz.a2s.a2spreparation.mappers.preparation.PrpCdePrlvUsrCodeMapper;
import dz.a2s.a2spreparation.mappers.preparation.PrpCdeUsrCodeMapper;
import dz.a2s.a2spreparation.mappers.preparation.VenteDetailsMapper;
import dz.a2s.a2spreparation.mappers.preparation.VentePrlvDetailsMapper;
import dz.a2s.a2spreparation.repositories.views.*;
import dz.a2s.a2spreparation.services.CustomUserDetailsService;
import dz.a2s.a2spreparation.services.PreparationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    private final PrpCdeUsrCodeRepository prpCdeUsrCodeRepository;
    private final PrpListeCdeZonesRepository prpListeCdeZonesRepository;
    private final VentePrlvDetailsRepository ventePrlvDetailsRepository;
    private final VenteDetailsRepository venteDetailsRepository;
    private final VenteZoneDetailsRepository venteZoneDetailsRepository;
    private final MotifRepository motifRepository;

    @Override
    public List<PrpCdeUsrCodeDto> getCommandes(String date) {
        log.info("Entering the getCommandes method from the PreparationService with date {}", date);

        if(!date.isEmpty())
            LocalDate.parse(date, DATE_FORMATTER);

        String username = this.customUserDetailsService.getCurrentUserCode();
        log.info("Entering the getCommandes method from the PreparationService with username {}", username);

        log.info("Fetching the liste of orders by preparateur from the repo");
        List<PrpCdeUsrCode> commandes = this.prpCdeUsrCodeRepository.getCmdParPreparateur(username, date);
        List<PrpCdeUsrCodeDto> response = commandes.stream().map(PrpCdeUsrCodeMapper::toPrpCdeUsrCodeDto).toList();

        log.info("Returning fetched data to controller with length {}", response.size());

        return response;
    }

    @Override
    public List<PrpCmdPrlvUsrCodeDto> getCommandesPrlv(String date) {
        log.info("Entering the getCommandesPrlv method from the PreparationService with date {}", date);

        if(!date.isEmpty())
            LocalDate.parse(date, DATE_FORMATTER);

        String username = this.customUserDetailsService.getCurrentUserCode();
        log.info("Entering the getCommandesPrlv method from the PreparationService with username {}", username);

        log.info("Fetching the liste of orders par prélevement by preparateur from the repo");
        List<PrpCdePrlvUsrCode> commandes = this.prpCdePrlvUsrCodeRepository.getCmdPrlvParPreparateur(username, date);
        List<PrpCmdPrlvUsrCodeDto> response = commandes.stream().map(PrpCdePrlvUsrCodeMapper::toPrpCmdPrlvUsrCodeDto).toList();

        log.info("Returning fetched data to controller with length {}", response.size());

        return response;
    }

    @Override
    public Integer startPreparePrlv(int p_cmp, int p_slt_id, String p_slt_type, int p_slt_annee) throws Exception {
        log.info("Entering the method startPreparePrlv from the PreparationService");

        Integer response = this.prpCdePrlvUsrCodeRepository.startPreparePrlv(
                p_cmp,
                p_slt_id,
                p_slt_type,
                p_slt_annee
        );

        log.info("Valeur de retour de la stored procedure for starting preparation with prlv is {}", response);

        if(response != 0)
            throw new Exception("Erreur lors de la mise à jour de la commande");

        return response;
    }

    @Override
    public Integer startPrepareCde(int p_vnt_cmp_id, int p_vnt_id, String p_vnt_type, String p_vnt_stk_code) throws Exception{
        log.info("Entering the method startPrepareCde from the PreparationService");

        Integer response = this.prpCdeUsrCodeRepository.startPrepareCde(
                p_vnt_cmp_id,
                p_vnt_id,
                p_vnt_type,
                p_vnt_stk_code
        );

        log.info("Valeur de retour de la stored procedure for starting preparation with cde is {}", response);

        if(response != 0)
            throw new Exception("Erreur lors de la mise à jour de la commande");

        return response;
    }

    @Override
    public Integer startPrepareZone(int v_vbz_cmp_id, int v_vbz_vnt_id, String v_vbz_vnt_type, String v_vbz_stk_code, int v_vbz_zone) throws Exception{
        log.info("Entering the method startPrepareZone from the PreparationService");

        Integer preparateurId = this.customUserDetailsService.getUtilisateurId();
        log.info("Fetched the preparateur id from the repo {}", preparateurId);

        Integer response = this.prpListeCdeZonesRepository.startPrepareZone(
                v_vbz_cmp_id,
                v_vbz_vnt_id,
                v_vbz_vnt_type,
                v_vbz_stk_code,
                v_vbz_zone,
                preparateurId
        );

        log.info("Valeur de retour de la stored procedure for starting preparation par zone is {}", response);

        if(response != 0)
            throw new Exception("Erreur lors de la mise à jour de la commande par zone");

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

        if(commande == null)
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

    @Override
    public Integer setPreparedQuantity(Integer cmpId, Integer id, String type, String stkCode, Integer no, Integer qte, String motif) throws Exception {
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

        if(response == 0)
            throw new Exception("Une erreur est survenu lors de la mise à jour de la quantité préparé pour la commande spécifiée");

        return response;
    }

    @Override
    public Integer setPreparedQuantityZone(LigneQteZoneDto ligne) throws Exception {
        log.info("Entering the setPreparedQuantity method from the PreparationService with ligne {}", ligne);

        Integer response = this.venteZoneDetailsRepository.setPreparedQuantityZone(
                ligne.getCmpId(),
                ligne.getId(),
                ligne.getType(),
                ligne.getType(),
                ligne.getZone(),
                ligne.getNo(),
                ligne.getQte(),
                ligne.getMotif()
        );

        log.info("Réponse de la requête de mise à jour de la quantité préparée {}", response);

        if(response == 0)
            throw new Exception("Une erreur est survenu lors de la mise à jour de la quantité préparé pour la commande spécifiée");

        return response;
    }

    @Override
    public List<Motif> getAllMotif() {
        log.info("Entering the geAllMotif method from the PreparationService");

        List<Motif> motifs = this.motifRepository.getAll();

        log.info("Fetched the motifs from the repo {}", motifs);

        return motifs;
    }
}
