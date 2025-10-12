package dz.a2s.a2sinventaire.mappers.statistique;

import dz.a2s.a2sinventaire.dto.statistique.StatsPreparateurDto;
import dz.a2s.a2sinventaire.entities.views.StatsPreparateur;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class StatistiqueMapper {

    public static List<StatsPreparateurDto> toStatsPreparateurDtoList (List<StatsPreparateur> statistiques) {
        List<StatsPreparateurDto> list = new ArrayList<>();

        statistiques.forEach(item -> {
            AtomicReference<Boolean> found = new AtomicReference<>(false);
            if(list.isEmpty()) {
                StatsPreparateurDto statsPreparateurDto = new StatsPreparateurDto();
                statsPreparateurDto.setPreparateur(item.getPreparateur());
                statsPreparateurDto.setData(new ArrayList<StatsPreparateurDto.StatutData>());
            }

            list.forEach(element -> {
                if(element.getPreparateur().equals(item.getPreparateur())) {
                    found.set(true);
                    List<StatsPreparateurDto.StatutData> data = element.getData();
                    data.add(new StatsPreparateurDto.StatutData(
                            item.getStatutPrepare(),
                            item.getStatut(),
                            item.getNbrCommande(),
                            item.getNbrLigne(),
                            item.getTotalTtc()
                    ));
                }
            });

            if(!found.get()) {
                StatsPreparateurDto statsPreparateurDto = new StatsPreparateurDto();
                statsPreparateurDto.setPreparateur(item.getPreparateur());
                List<StatsPreparateurDto.StatutData> data = new ArrayList<>();
                data.add(new StatsPreparateurDto.StatutData(
                        item.getStatutPrepare(),
                        item.getStatut(),
                        item.getNbrCommande(),
                        item.getNbrLigne(),
                        item.getTotalTtc()
                ));
                statsPreparateurDto.setData(data);
                list.add(statsPreparateurDto);
            }

        });

        return list;
    }

}
