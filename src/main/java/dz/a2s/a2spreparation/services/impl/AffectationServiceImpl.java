package dz.a2s.a2spreparation.services.impl;

import dz.a2s.a2spreparation.dto.affectation.AffectCmdResultDto;
import dz.a2s.a2spreparation.entities.views.PrpCdePrlv;
import dz.a2s.a2spreparation.entities.views.PrpCdeZone;
import dz.a2s.a2spreparation.entities.views.PrpCommande;
import dz.a2s.a2spreparation.entities.views.PrpPrepareControle;
import dz.a2s.a2spreparation.exceptions.RessourceNotFoundException;
import dz.a2s.a2spreparation.repositories.DiMessagesRepository;
import dz.a2s.a2spreparation.repositories.views.PrpCdePrlvRepository;
import dz.a2s.a2spreparation.repositories.views.PrpCommandeRepository;
import dz.a2s.a2spreparation.repositories.views.PrpListeCdeZonesRepository;
import dz.a2s.a2spreparation.repositories.views.PrpPrepareControleRepository;
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

    @Override
    public List<PrpCdeZone> getListCmdZones() throws RessourceNotFoundException {
        log.info("Entering getListCmdZones method from the AffectationService");

        Integer companyId = this.customUserDetailsService.getCurrentCompanyId();
        log.info("Fetching orders for the company {}", companyId);

        log.info("Fetching data from the repo");
        List<PrpCdeZone> listeCommandes = this.prpListeCdeZonesRepository.getListCmdZones(companyId);
        log.info("Data fetched from the repo length = {}", listeCommandes.size());

        if(listeCommandes.isEmpty())
            throw new RessourceNotFoundException("Liste des commandes affectées par zone vide");

        return listeCommandes;
    }

    @Override
    public List<PrpCommande> getListCommande(String date) {
        log.info("Entering getListCommande method from the AffectationService");

        Integer companyId = this.customUserDetailsService.getCurrentCompanyId();
        log.info("Fetching orders for the company {}", companyId);

//        String statut = CommandeStatus.getStatus(status);

//        log.info("Fetching data from the repo with the following status '{}'", statut);
        List<PrpCommande> listeCommandes = this.prpCommandeRepository.getListCommande(companyId, date);
        log.info("Data fetched from the repo length = {}", listeCommandes.size());

        return listeCommandes;
    }

    @Override
    public List<PrpCommande> getListCommandeAssigned(String date) {
        log.info("Entering getListCommandeAssigned method from the AffectationService");

        Integer companyId = this.customUserDetailsService.getCurrentCompanyId();
        log.info("Fetching assigned orders for the company {}", companyId);

        List<PrpCommande> listeCommandes = this.prpCommandeRepository.getListCommandeAssigned(companyId, date);
        log.info("Data fetched from the repo length = {}", listeCommandes.size());

        return listeCommandes;
    }

    @Override
    public List<PrpCdePrlv> getListeCommandesPrlv(String date) {
        log.info("Entering the getListeCommandesPrlv from the AffectationService with date {}", date);

        log.info("Fetching liste des commandes from the repo");
        List<PrpCdePrlv> listeCommandes = this.prpCdePrlvRepository.getListeCommandesPrlv(date);
        log.info("Data fetched from the repo with length {}", listeCommandes.size());

        return listeCommandes;
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
    public AffectCmdResultDto affectCommandePrp(int p_cmp, int p_vnt, int p_stk, int p_type, int p_prp, int p_cnt1, int p_cnt2, String p_user, String reference) {
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

        log.info("La réponse de la procédure stockée affectatin des commandes {}", response);

        if(response == 0)
            result = AffectCmdResultDto.builder().messageId(0).message("Affectation réussie").venteRef(reference).build();
        else {
            String message = this.diMessagesRepository.getMsgDescLocById(response);
            result = AffectCmdResultDto.builder().messageId(response).message(message == null ? "Une erreur inconnue s'est produite" : message).venteRef(reference).build();
        }

        return result;
    }

    @Override
    public AffectCmdResultDto affectCommandePrpPrlv(int p_cmp, int p_slt_id, String p_slt_type, int p_slt_annee, int p_prp, int p_cnt1, int p_cnt2, String p_user, String reference) {
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
}
