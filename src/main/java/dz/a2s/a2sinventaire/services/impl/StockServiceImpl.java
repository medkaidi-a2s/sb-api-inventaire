package dz.a2s.a2sinventaire.services.impl;

import dz.a2s.a2sinventaire.dto.response.PaginatedDataDto;
import dz.a2s.a2sinventaire.dto.stock.response.StockDto;
import dz.a2s.a2sinventaire.mappers.StockMapper;
import dz.a2s.a2sinventaire.repositories.views.StockRepository;
import dz.a2s.a2sinventaire.services.CustomUserDetailsService;
import dz.a2s.a2sinventaire.services.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final StockMapper stockMapper;

    @Override
    public PaginatedDataDto<StockDto> getStocks(Integer page, Integer size, String search) {
        log.info("| Entry | StockService.getStocks | Args | page={}, size={}, search={}", page, size, search);

        Integer cmpId = this.customUserDetailsService.getCurrentCompanyId();
        log.info("Company Id retrieved {}", cmpId);

        int start = (page * size) - size + 1;
        int end = page * size;

        var projections = this.stockRepository.getAllStocks(start, end, cmpId, search);
        log.info("Fetched the stock master projections from the repo | projections.size={}", projections.size());

        var totalRecords = projections.isEmpty() ? 0 : projections.stream().findFirst().get().getTotalCount();
        log.info("Fetched the total records from the projections | totalRecords={}", totalRecords);

        var stocks = projections.stream().map(this.stockMapper::fromStockMasterProjection).toList();
        log.info("Mapped the projections to stocks | stocks.size={}", stocks.size());

        return new PaginatedDataDto<>(stocks, totalRecords, (totalRecords + size - 1) / size, page, size);
    }
}
