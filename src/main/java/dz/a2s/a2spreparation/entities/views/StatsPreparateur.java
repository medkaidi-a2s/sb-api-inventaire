package dz.a2s.a2spreparation.entities.views;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Immutable
@Table(name = "PRP_STATS_CDES")
public class StatsPreparateur {

    @Id
    @Column(name = "ROW_ID")
    private Integer id;

    @Column(name = "VNT_STATUT_PREPARE")
    private Integer statutPrepare;

    @Column(name = "STATUT")
    private String statut;

    @Column(name = "PREPARATEUR")
    private String preparateur;

    @Column(name = "NBR_DE")
    private Integer nbrCommande;

    @Column(name = "NBR_LIGNE")
    private Integer nbrLigne;

    @Column(name = "TOTAL")
    private BigDecimal totalTtc;

}
