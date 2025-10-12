package dz.a2s.a2sinventaire.entities;

import dz.a2s.a2sinventaire.entities.keys.ParamsId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "STP_PARAMS")
public class Params {

    @EmbeddedId
    @Id
    private ParamsId id;

    @Column(name = "PRM_METHODE_PREPARE")
    private int method;

}
