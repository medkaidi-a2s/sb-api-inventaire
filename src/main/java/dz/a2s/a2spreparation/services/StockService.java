package dz.a2s.a2spreparation.services;

import dz.a2s.a2spreparation.dto.response.PaginatedDataDto;
import dz.a2s.a2spreparation.dto.stock.response.StockDto;

import java.util.List;

public interface StockService {

    PaginatedDataDto<StockDto> getStocks(Integer page, Integer size, String search);

}
