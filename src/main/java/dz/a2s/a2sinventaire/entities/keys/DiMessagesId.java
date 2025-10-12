package dz.a2s.a2sinventaire.entities.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class DiMessagesId {

    @Column(name = "MSG_ID")
    private Integer msgId;

    @Column(name = "MSG_SYS_ID")
    private Integer msgSysId;

}
