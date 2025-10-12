package dz.a2s.a2sinventaire.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChangePasswordDto {

    @NotNull(message = "Le mot de passe actuel est obligatoire")
    private String currentPassword;

    @NotNull(message = "Le nouveau mot de passe est obligatoire")
    private String newPassword;

}
