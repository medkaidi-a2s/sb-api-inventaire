package dz.a2s.a2sinventaire.controllers;

import dz.a2s.a2sinventaire.api.StockApi;
import dz.a2s.a2sinventaire.dto.response.PaginatedResponse;
import dz.a2s.a2sinventaire.dto.stock.response.StockDto;
import dz.a2s.a2sinventaire.services.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/stocks")
public class StockController implements StockApi {

    private final StockService stockService;

    @Override
    public ResponseEntity<PaginatedResponse<StockDto>> getAllStocks(@RequestParam("page") Integer page, @RequestParam("size") Integer size, @RequestParam("search") Optional<String> search) {
        log.info("| Entry | StockController.getAllStocks | Args | page={}, size={}, search={}", page, size, search);

        var stocks = this.stockService.getStocks(page, size, search.orElse(""));
        log.info("Fetched the stocks from the service | stocks.data.size={}", stocks.data().size());

        var response = new PaginatedResponse<>(
                200,
                "Stocks récupéré avec succès - page = " + page + " - size = " + size,
                stocks.data(),
                stocks.totalRecords(),
                stocks.totalPages(),
                stocks.currentPage(),
                stocks.pageSize()
        );
        return ResponseEntity.ok(response);
    }
}
