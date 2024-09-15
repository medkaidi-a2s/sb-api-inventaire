package dz.a2s.a2spreparation.exceptions;

import java.io.Serial;

public class RessourceNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1;
    public RessourceNotFoundException(String message) {super(message);}

}
