package dz.a2s.a2sinventaire.dto.statistique;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StatsPreparateurDto {

    private String preparateur;

    private List<StatutData> data;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class StatutData {
        private int statutPrepare;
        private String statut;
        private int count;
        private int lines;
        private BigDecimal cost;
    }

}
