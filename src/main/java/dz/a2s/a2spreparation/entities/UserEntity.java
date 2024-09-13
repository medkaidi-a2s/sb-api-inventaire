package dz.a2s.a2spreparation.entities;

import dz.a2s.a2spreparation.entities.keys.UserEntityId;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "STP_USERS")
public class UserEntity {

    @Id
    @EmbeddedId
    private UserEntityId id = new UserEntityId();

    @Column(name="USR_PASSWORD")
    private String password;

    @Column(name = "USR_NOM_LOC")
    private String nom;

    public String getUsername() {
        return this.id.getUsername();
    }

    public int getCompanyId() {
        return this.id.getUsrCmpId();
    }

}
