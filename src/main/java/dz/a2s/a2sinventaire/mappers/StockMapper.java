package dz.a2s.a2sinventaire.mappers;

import dz.a2s.a2sinventaire.dto.preparation.response.ProductLotDto;
import dz.a2s.a2sinventaire.dto.stock.projections.ProductLotProjection;
import dz.a2s.a2sinventaire.dto.stock.projections.StockMasterProjection;
import dz.a2s.a2sinventaire.dto.stock.response.StockDto;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class StockMapper {

    public StockDto fromStockMasterProjection(StockMasterProjection projection) {
        return new StockDto(
                projection.getCmpId(),
                projection.getId(),
                projection.getMedId(),
                projection.getStkCode(),
                projection.getDesignation(),
                projection.getFournisseur(),
                projection.getLot(),
                projection.getDatePeremption(),
                projection.getPpa(),
                projection.getQuantite(),
                projection.getPrixVente(),
                projection.getPrixGros(),
                projection.getShp(),
                projection.getUgVente(),
                projection.getEtat(),
                projection.getDateArrivage(),
                projection.getColisage(),
                projection.getId() + "-" + projection.getLot() + "-" + projection.getMedId() + "-" + projection.getStkCode()
        );
    }

    public ProductLotDto fromProductLotProjection(ProductLotProjection projection) {
        return new ProductLotDto(
                projection.getCmpId(),
                projection.getId(),
                projection.getMedId(),
                projection.getStkCode(),
                projection.getNlot(),
                projection.getDatePeremption(),
                projection.getQte(),
                projection.getPrixPh(),
                projection.getPrixPpa(),
                projection.getPrixShp(),
                projection.getUgVente()
        );
    }

}
