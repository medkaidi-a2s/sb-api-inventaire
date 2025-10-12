package dz.a2s.a2sinventaire.dto.auth;

import jakarta.persistence.ParameterMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ParamMeta {

    String name;
    Class<?> type;
    ParameterMode mode;
    Object value;

}
