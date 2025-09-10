package dz.a2s.a2spreparation.entities;

import dz.a2s.a2spreparation.entities.keys.BacId;
import dz.a2s.a2spreparation.entities.keys.VntBonBacsId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "VNT_BON_BACS")
@IdClass(VntBonBacsId.class)
public class VntBonBacs {

    @Id
    @Column(name = "bbc_cmp_id")
    private Integer cmpId;
    @Id
    @Column(name = "bbc_vnt_id")
    private Integer venteId;
    @Id
    @Column(name = "bbc_type")
    private String type;
    @Id
    @Column(name = "bbc_stk_code")
    private String stkCode;
    @Id
    @Column(name = "bbc_lbc_id")
    private Integer bacId;
    @Id
    @Column(name = "bbc_lbt_id")
    private Integer typeId;

}