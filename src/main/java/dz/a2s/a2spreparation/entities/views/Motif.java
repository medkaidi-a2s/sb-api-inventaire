package dz.a2s.a2spreparation.entities.views;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Immutable
@Table(name = "BI_MOTIF_LIV")
public class Motif {

    @Id
    @Column(name = "CODE")
    private Integer id;

    @Column(name = "LIBELLE")
    private String libelle;

    @Column(name = "CMP_ID")
    private Integer cmpId;

}
