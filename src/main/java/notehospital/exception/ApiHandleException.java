package notehospital.exception;

import notehospital.dto.response.Response;
import notehospital.exception.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class ApiHandleException {


    @ExceptionHandler(Duplicate.class)
    public ResponseEntity<?> duplicate(Duplicate exception){
        return new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<?> duplicateEntity(SQLIntegrityConstraintViolationException exception){
        return new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFound.class)
    public ResponseEntity<?> notFoundEntity(EntityNotFound exception){
        return new ResponseEntity<String>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MailError.class)
    public ResponseEntity<?> mailError(MailError exception){
        return new ResponseEntity<String>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidPhone.class)
    public ResponseEntity<?> invalidPhone(InvalidPhone invalidPhone){
        return ResponseEntity.ok(new Response<String>(400,invalidPhone.getMessage(),null));
    }

    @ExceptionHandler(BadRequest.class)
    public ResponseEntity<?> invalidPhone(BadRequest badRequest){
        return new ResponseEntity<String>(badRequest.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<String> handleValidationException(BindException ex) {
        StringBuilder errorMessage = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            errorMessage.append(error.getDefaultMessage()).append("\n");
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.toString());
    }
}
