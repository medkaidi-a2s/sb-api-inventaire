package dz.a2s.a2spreparation.api;

import dz.a2s.a2spreparation.dto.CommandeZoneResponseDto;
import dz.a2s.a2spreparation.dto.response.PaginatedResponse;
import dz.a2s.a2spreparation.dto.response.SuccessResponseDto;
import dz.a2s.a2spreparation.dto.stock.response.StockDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Tag(name = "Gestion des stocks", description = "APIs pour les opérations relatives aux stocks")
public interface StockApi {

    @Operation(summary = "Récupération du stock de tous les produits", description = "Récupération des stocks de tout les produits sous forme paginée")
    @ApiResponse(responseCode = "200", description = "Stocks récupérés avec succès")
    @GetMapping("")
    public ResponseEntity<PaginatedResponse<StockDto>> getAllStocks(Integer page, Integer size, Optional<String> search);

}
