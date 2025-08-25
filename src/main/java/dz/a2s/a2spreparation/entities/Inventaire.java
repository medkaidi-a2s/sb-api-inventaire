package dz.a2s.a2spreparation.entities;

import dz.a2s.a2spreparation.entities.keys.InventaireId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "STP_INVENTAIRES")
@IdClass(InventaireId.class)
public class Inventaire {

    @Id
    @Column(name = "CMP_ID")
    private Integer cmpId;

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "DATE")
    private Date date;

    @Column(name = "REMARQUE")
    private String remarque;

}
