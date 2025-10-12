package dz.a2s.a2sinventaire.exceptions;

import java.io.Serial;

public class DatabaseErrorException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1;
    public DatabaseErrorException(String message) {super(message);}
}
