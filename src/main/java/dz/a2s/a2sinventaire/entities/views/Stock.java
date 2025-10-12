package dz.a2s.a2sinventaire.entities.views;

import dz.a2s.a2sinventaire.entities.keys.StockId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@IdClass(StockId.class)
@Immutable
@Table(name = "PRP_STOCKS")
public class Stock {

    @Column(name = "PRD_CMP_ID")
    @Id
    private Integer cmpId;

    @Column(name = "PRD_ID")
    @Id
    private Integer id;

    @Column(name = "PRD_MED_ID")
    @Id
    private Integer medId;

    @Column(name = "PRD_STK_CODE")
    @Id
    private String stkCode;

    @Column(name = "MED_COMMERCIAL_NAME")
    private String designation;

    @Column(name = "PRD_ATTRIBUT2")
    private String fournisseur;

    @Column(name = "PRD_NLOT")
    private String lot;

    @Column(name = "PRD_DATE_PEREMPTION")
    private Date datePeremption;

    @Column(name = "PRD_PRIX_PPA")
    private BigDecimal ppa;

    @Column(name = "PRD_QTE")
    private Integer quantite;

    @Column(name = "PRD_PRIX_PH")
    private BigDecimal prixVente;

    @Column(name = "PRD_PRIX_GR")
    private BigDecimal prixGros;

    @Column(name = "PRD_PRIX_SHP")
    private BigDecimal shp;

    @Column(name = "PRD_UG_VNETE")
    private Integer ugVente;

    @Column(name = "PRD_ETAT_FLAG")
    private Integer etat;

    @Column(name = "PRD_CREER_DATE")
    private LocalDateTime dateArrivage;

    @Column(name = "PRD_COLIS")
    private Integer colisage;
    
}
