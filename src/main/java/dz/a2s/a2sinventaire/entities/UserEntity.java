package dz.a2s.a2sinventaire.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "STP_USERS")
public class UserEntity {

    @Id
    @Column(name="USR_CODE")
    private String username;

    @Column(name="USR_PASSWORD")
    private String password;

    @Column(name = "USR_NOM_LOC")
    private String nom;

}
