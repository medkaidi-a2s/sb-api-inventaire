package dz.a2s.a2spreparation.dto.commande.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ListeEtiquettesResponse {

    private String reference;
    private String client;
    private String adresse;
    private String region;
    private List<EtiquetteResponse> data;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class EtiquetteResponse {
        private String code;
        private String key;
    }

}
