package dz.a2s.a2spreparation.entities;

import dz.a2s.a2spreparation.entities.keys.BacId;
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
@Table(name = "LGS_BACS")
@IdClass(BacId.class)
public class Bac {

    @Id
    @Column(name = "LBC_CMP_ID")
    private Integer cmpId;

    @Id
    @Column(name = "LBC_ID")
    private Integer id;

    @Id
    @Column(name = "LBC_LBT_ID")
    private Integer typeId;

    @Column(name = "LBT_LIBELLE")
    private String type;

    @Column(name = "LBC_LBS_ID")
    private Integer statsId;

    @Column(name = "LBC_CODE")
    private String code;

    @Transient
    private String key;

}
