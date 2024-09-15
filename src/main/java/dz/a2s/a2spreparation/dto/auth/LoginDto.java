package dz.a2s.a2spreparation.dto.auth;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    @NotNull(message = "Le nom d'utilisateur est obligatoire")
    @NotEmpty(message = "Le nom d'utilisateur est obligatoire")
    private String username;

    @NotNull(message = "Le mot de passe est obligatoire")
    @NotEmpty(message = "Le mot de passe est obligatoire")
    private String password;

    @NotNull(message = "La compagnie est obligatoire")
    @NotEmpty(message = "La compagnie est obligatoire")
    private Integer companyId;

}
