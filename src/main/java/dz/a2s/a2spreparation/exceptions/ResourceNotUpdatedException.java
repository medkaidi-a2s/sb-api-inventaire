package dz.a2s.a2spreparation.exceptions;

import java.io.Serial;

public class ResourceNotUpdatedException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1;
    public ResourceNotUpdatedException(String message) {super(message);}
}
