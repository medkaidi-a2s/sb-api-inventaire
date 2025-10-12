package dz.a2s.a2sinventaire.api;

import dz.a2s.a2sinventaire.dto.response.PaginatedResponse;
import dz.a2s.a2sinventaire.dto.stock.response.StockDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Tag(name = "Gestion des stocks", description = "APIs pour les opérations relatives aux stocks")
public interface StockApi {

    @Operation(summary = "Récupération du stock de tous les produits", description = "Récupération des stocks de tout les produits sous forme paginée")
    @ApiResponse(responseCode = "200", description = "Stocks récupérés avec succès")
    @GetMapping("")
    public ResponseEntity<PaginatedResponse<StockDto>> getAllStocks(Integer page, Integer size, Optional<String> search);

}
