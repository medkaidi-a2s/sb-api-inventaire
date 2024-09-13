package dz.a2s.a2spreparation.entities.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Embeddable
public class UserEntityId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name="USR_CMP_ID")
    private Integer usrCmpId;

    @Column(name="USR_CODE")
    private String username;

    public UserEntityId(Integer cmpUsrId, String usrCode) {
        super();
        this.usrCmpId = cmpUsrId;
        this.username = usrCode;
    }

}
