package dz.a2s.a2spreparation.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(RessourceNotFoundException.class)
    public ResponseEntity<ErrorObject> handleNotFoundException(RessourceNotFoundException ex) {
        log.info("Entering notFoundExceptionHandler from the GlobalExceptionsHandler with message {}", ex.getMessage());

        ErrorObject errorObject = ErrorObject.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .timestamp(new Date())
                .message("La ressource demandée est introuvable")
                .build();

        log.info("Returning the following error {}", errorObject.getMessage());
        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorObject> handleBadCredentialsException(BadCredentialsException ex) {
        log.info("Entering handleBadCredentialsException from the GloablHandleExceptions with message {}", ex.getMessage());

        ErrorObject errorObject = new ErrorObject();

        errorObject.setMessage("Nom d'utilisateur ou mot de passe incorrecte");
        errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setTimestamp(new Date());
//        errorObject.setError(ex);

        log.info("Returning the following error {}", errorObject.getMessage());
        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorObject> handleValidationErrors(MethodArgumentNotValidException ex) {
        log.info("Entering handleValidationErrors method from GlobalErrorHandler with message {}", ex.getMessage());

        List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(fieldError -> {
            return fieldError.getField() + " : " + fieldError.getDefaultMessage();
        }).toList();

        log.info("Détails de l'erreur de validation {}", errors);

        ErrorObject errorObject = ErrorObject
                .builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(new Date())
                .message("Erreur de validation des paramètres")
//                .error(getErrorsMap(errors))
                .build();

        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorObject> handleException(Exception ex) {
        log.info("Entering handleException from the GlobalHandleException with message {}", ex.getMessage());

        ErrorObject errorObject = ErrorObject.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message("Une erreur interne s'est produite, veuillez contacter votre administrateur.")
                .timestamp(new Date())
                .build();

        log.info("Returning the following error {}", errorObject.getMessage());

        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorObject> handleRuntimeException(RuntimeException ex) {
        log.info("Entering handleRuntimeException from the GlobalHandleException with message {}", ex.getMessage());

        ErrorObject errorObject = ErrorObject.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Une erreur interne s'est produite en cours d'exécution de l'application, veuillez contacter votre administrateur.")
                .timestamp(new Date())
                .build();

        log.info("Returning the following error {}", ex.getMessage());

        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>(
                Map.of("errors", errors)
        );
        return errorResponse;
    }

}
