package dz.a2s.a2spreparation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthorizationDto {

    private int code;
    private int value;

}
