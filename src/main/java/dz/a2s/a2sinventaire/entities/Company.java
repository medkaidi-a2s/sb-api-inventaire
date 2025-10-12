package dz.a2s.a2sinventaire.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "STP_COMPAGNIES")
public class Company {

    @Id
    @Column(name = "CMP_ID")
    private Integer id;

    @Column(name="CMP_NOM_ETR")
    private String label;

    @Column(name="CMP_VIL_ID")
    private String location;

}
