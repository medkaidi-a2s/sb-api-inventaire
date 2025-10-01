package dz.a2s.a2spreparation.dto.commande.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
public class CommandeColisageRequest {

    private String dateDebut;
    private String dateFin;
    private Boolean statutPrepare;
    private String rotation;
    private Integer page;
    private String search;

}
