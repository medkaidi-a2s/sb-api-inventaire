package dz.a2s.a2spreparation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SuccessResponseDto<T> {

    private int status;
    private String message;
    private T data;

}
