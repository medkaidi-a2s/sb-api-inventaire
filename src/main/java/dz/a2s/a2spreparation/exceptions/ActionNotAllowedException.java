package dz.a2s.a2spreparation.exceptions;

import java.io.Serial;

public class ActionNotAllowedException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1;
    public ActionNotAllowedException(String message) {super(message);}
}
