package dz.a2s.a2sinventaire.services;

import dz.a2s.a2sinventaire.dto.response.PaginatedDataDto;
import dz.a2s.a2sinventaire.dto.stock.response.StockDto;

public interface StockService {

    PaginatedDataDto<StockDto> getStocks(Integer page, Integer size, String search);

}
