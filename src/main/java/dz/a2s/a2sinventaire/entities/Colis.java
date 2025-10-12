package dz.a2s.a2sinventaire.entities;

import dz.a2s.a2sinventaire.entities.keys.ColisId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "VNT_COLIS")
@IdClass(ColisId.class)
public class Colis {

    @Id
    @Column(name = "VCO_CMP_ID")
    private Integer cmpId;

    @Id
    @Column(name = "VCO_VNT_ID")
    private Integer id;

    @Id
    @Column(name = "VCO_VNT_TYPE")
    private String type;

    @Id
    @Column(name = "VCO_STK_CODE")
    private String stkCode;

    @Id
    @Column(name = "VCO_CODE")
    private String code;

    @Column(name = "VNT_REFERENCE")
    private String reference;

    @Column(name = "TER_NOM")
    private String client;

    @Column(name = "TER_ADRESSE")
    private String adresse;

    @Column(name = "TER_REGION_LIB")
    private String region;

    @Column(name = "ZONE")
    private String zone;

    @Transient
    private String key;

}
