package dz.a2s.a2sinventaire.dto.auth;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotNull(message = "Le nom d'utilisateur est obligatoire")
    @NotEmpty(message = "Le nom d'utilisateur est obligatoire")
    private String username;

    @NotNull(message = "Le mot de passe est obligatoire")
    @NotEmpty(message = "Le mot de passe est obligatoire")
    private String password;

}
