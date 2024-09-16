package dz.a2s.a2spreparation.entities.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
@Embeddable
public class ParamsId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "PRM_CMP_ID")
    private Integer prmCmpId;

    @Column(name = "PRM_TYPE")
    private String prmType;

}
