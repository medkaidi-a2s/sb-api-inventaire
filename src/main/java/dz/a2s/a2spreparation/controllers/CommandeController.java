package dz.a2s.a2spreparation.controllers;

import dz.a2s.a2spreparation.api.CommandeApi;
import dz.a2s.a2spreparation.dto.CommandeResponseDto;
import dz.a2s.a2spreparation.dto.CommandeZoneResponseDto;
import dz.a2s.a2spreparation.dto.affectation.CmdColisageDto;
import dz.a2s.a2spreparation.dto.affectation.CmdIdDto;
import dz.a2s.a2spreparation.dto.affectation.CmdZoneColisageDto;
import dz.a2s.a2spreparation.dto.affectation.CmdZoneIdDto;
import dz.a2s.a2spreparation.dto.commande.request.CommandeColisageRequest;
import dz.a2s.a2spreparation.dto.commande.request.UpdateColisageRequest;
import dz.a2s.a2spreparation.dto.commande.response.ColisageDto;
import dz.a2s.a2spreparation.dto.commande.response.CommandeColisageResponse;
import dz.a2s.a2spreparation.dto.commande.response.ListeEtiquettesResponse;
import dz.a2s.a2spreparation.dto.response.PaginatedResponse;
import dz.a2s.a2spreparation.dto.response.SuccessResponseDto;
import dz.a2s.a2spreparation.entities.Bac;
import dz.a2s.a2spreparation.entities.Colis;
import dz.a2s.a2spreparation.services.CheckingService;
import dz.a2s.a2spreparation.services.CommandeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/commandes")
public class CommandeController implements CommandeApi {

    private final CommandeService commandeService;

    @Override
    public ResponseEntity<PaginatedResponse<CommandeColisageResponse>> getCommandesColisage(@RequestBody CommandeColisageRequest request) {
        log.info("| Entry | CommandeController.getCommandesColisage | Args | request : {}", request);

        var commandes = this.commandeService.getCommandesColisage(
                request.getDateDebut(),
                request.getDateFin(),
                request.getStatutPrepare() ? 1 : 0,
                request.getPage(),
                request.getSearch()
        );
        log.info("Fetched the orders from the service | commandes.size={}", commandes.data().size());

        var response = new PaginatedResponse<>(
                200,
                "Liste des commandes récupérée avec succès - page = " + request.getPage() + " - size = " + commandes.pageSize(),
                commandes.data(),
                commandes.totalRecords(),
                commandes.totalPages(),
                commandes.currentPage(),
                commandes.pageSize()
        );
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<SuccessResponseDto<List<CommandeZoneResponseDto>>> getCommandesZone(@RequestParam Optional<String> date) {
        log.info("| Entry | CommandeController.getCommandesZone | Args | date : {}", date);

        var commandes = this.commandeService.getControlledCommandesZone(date.orElse(""));
        log.info("Fetched the controlled commandes from the service | commandes.size={}", date);

        SuccessResponseDto<List<CommandeZoneResponseDto>> successResponseDto = new SuccessResponseDto<>(
                200,
                "Liste des commandes par zone déjà contrôlées",
                commandes
        );

        return ResponseEntity.ok(successResponseDto);
    }

    @Override
    public ResponseEntity<SuccessResponseDto<List<CommandeResponseDto>>> getControlledCommandes(Optional<String> date) {
        log.info("| Entry | CommandeController.getCommandes | Args | date : {}", date);

        List<CommandeResponseDto> commandes = this.commandeService.getControlledCommandes(date.orElse(""));
        log.info("Fetched the controlled commandes from the service | commandes.size={}", date);

        SuccessResponseDto<List<CommandeResponseDto>> successResponseDto = new SuccessResponseDto<>(
                200,
                "Liste des commandes déjà contrôlées",
                commandes
        );

        return ResponseEntity.ok(successResponseDto);
    }

    @Override
    public ResponseEntity<SuccessResponseDto<List<CommandeResponseDto>>> getAllCommandes(@RequestParam("date") Optional<String> date, @RequestParam("search") Optional<String> search) {
        log.info("| Entry | CommandeController.getAllCommandes | Args | date : {}, search : {}", date, search);

        var commandes = this.commandeService.getAllCommandes(search.orElse("").trim(), date.orElse(""));
        log.info("Fetched the orders from the service | commandes.size={}", commandes.size());

        SuccessResponseDto<List<CommandeResponseDto>> successResponseDto = new SuccessResponseDto<>(
                200,
                "Liste des commandes",
                commandes
        );

        return ResponseEntity.ok(successResponseDto);
    }

    @Override
    public ResponseEntity<SuccessResponseDto<List<CommandeZoneResponseDto>>> getAllCommandesZone(@RequestParam("date") Optional<String> date, @RequestParam("search") Optional<String> search) {
        log.info("| Entry | CommandeController.getAllCommandesZone | Args | date : {}, search : {}", date, search);

        var commandes = this.commandeService.getAllCommandesZone(search.orElse("").trim(), date.orElse(""));
        log.info("Fetched the orders from the service | commandes.size={}", commandes.size());

        SuccessResponseDto<List<CommandeZoneResponseDto>> successResponseDto = new SuccessResponseDto<>(
                200,
                "Liste des commandes par zone",
                commandes
        );

        return ResponseEntity.ok(successResponseDto);
    }

    @Override
    public ResponseEntity<SuccessResponseDto<Integer>> saisirColisageCommande(@RequestBody CmdColisageDto cmdColisageDto) {
        log.info("| Entry | CommandeController.saisirColisageCommande | Args | cmdColisageDto={}", cmdColisageDto);

        var response = this.commandeService.saisirColisageCommande(cmdColisageDto);
        log.info("Réponse de la méthode de saisi du colisage | response={}", response);

        SuccessResponseDto<Integer> successResponseDto = new SuccessResponseDto<>(
                200,
                "Liste des commandes déjà contrôlées",
                response
        );

        return ResponseEntity.ok(successResponseDto);
    }

    @Override
    public ResponseEntity<SuccessResponseDto<ColisageDto>> getColisageCommande(@RequestBody CmdIdDto id) {
        log.info("| Entry | CommandeController.getColisageCommande | Args | id={}", id);

        var colisage = this.commandeService.getColisageCommande(id);
        log.info("Fetched the colisage from the service | colisage={}", colisage);

        var response = new SuccessResponseDto<>(
                200,
                "Colisage récupéré avec succès",
                colisage);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<SuccessResponseDto<Integer>> saisirColisageZone(@RequestBody CmdZoneColisageDto cmdZoneColisageDto) {
        log.info("| Entry | CommandeController.saisirColisageZone | Args | cmdZoneColisageDto={}", cmdZoneColisageDto);

        var response = this.commandeService.saisirColisageZone(cmdZoneColisageDto);
        log.info("Réponse de la méthode de saisi du colisage | response={}", response);

        SuccessResponseDto<Integer> successResponseDto = new SuccessResponseDto<>(
                200,
                "Liste des commandes déjà contrôlées",
                response
        );

        return ResponseEntity.ok(successResponseDto);
    }

    @Override
    public ResponseEntity<SuccessResponseDto<ColisageDto>> getColisageZone(@RequestBody CmdZoneIdDto id) {
        log.info("| Entry | CommandeController.getColisageZone | Args | id={}", id);

        var colisage = this.commandeService.getColisageZone(id);
        log.info("Fetched the colisage from the service | colisage={}", colisage);

        var response = new SuccessResponseDto<>(
                200,
                "Colisage récupéré avec succès",
                colisage
        );

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<SuccessResponseDto<List<Bac>>> getListeBacs() {
        log.info("| Entry | CommandeController.getListeBacs");

        var bacs = this.commandeService.getListeBacs();
        log.info("Fetched the bacs from the service | bacs.size={}", bacs.size());

        var response = new SuccessResponseDto<>(
                200,
                "Liste des bacs récupérée avec succès",
                bacs
        );

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<SuccessResponseDto<CommandeColisageResponse>> updateColisageGlobal(@RequestBody UpdateColisageRequest request) {
        log.info("| Entry | CommandeController.updateColisageGlobal | Args | request={}", request);

        var updatedRows = this.commandeService.updateColisageGlobal(request);
        log.info("Updated rows in the service | updatedRows={}", updatedRows);

        var response = new SuccessResponseDto<>(
                200,
                "Colisage mis à jour avec succès",
                updatedRows
        );

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<SuccessResponseDto<ListeEtiquettesResponse>> getEtiquettesColisage(@RequestBody CmdIdDto id) {
        log.info("| Entry | CommandeController.getEtiquettesColisage | Args | id={}", id);

        var etiquettesResponse = this.commandeService.getEtiquettesColis(id);
        log.info("Fetched the etiquettes from the service | etiquettes.size={}", etiquettesResponse.getData().size());

        var response = new SuccessResponseDto<>(
                200,
                "Etiquettes récupérées avec succès",
                etiquettesResponse
        );

        return ResponseEntity.ok(response);
    }
}
