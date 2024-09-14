package br.com.xgommiapi.infra;

import br.com.xgommiapi.exception.GommiUserNotFoundException;
import br.com.xgommiapi.exception.GommiUserNotUniqueException;
import br.com.xgommiapi.exception.GommiUserNullAtributeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(GommiUserNotFoundException.class)
    private ResponseEntity<RestExceptionMessage> GommiUserNotFoundException(GommiUserNotFoundException ex) {
        RestExceptionMessage exceptionResponse = new RestExceptionMessage(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    @ExceptionHandler(GommiUserNotUniqueException.class)
    private ResponseEntity<RestExceptionMessage> GommiUserNotUniqueException(GommiUserNotUniqueException ex) {
        RestExceptionMessage exceptionResponse = new RestExceptionMessage(HttpStatus.CONFLICT, ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionResponse);
    }

    @ExceptionHandler(GommiUserNullAtributeException.class)
    private ResponseEntity<RestExceptionMessage> GommiUserNullAtributeException(GommiUserNullAtributeException ex) {
        RestExceptionMessage exceptionResponse = new RestExceptionMessage(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }
}
