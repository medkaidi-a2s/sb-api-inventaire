package dz.a2s.a2spreparation.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ErrorObject {

    private int statusCode;
    private String message;
    private Object error;
    private Date timestamp;

}
