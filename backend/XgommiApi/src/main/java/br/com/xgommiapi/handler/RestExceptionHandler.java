package br.com.xgommiapi.handler;

import br.com.xgommiapi.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(GommiUserAuthenticationFailedException.class)
    private ResponseEntity<RestExceptionMessage> GommiUserAuthenticationFailedException(GommiUserAuthenticationFailedException ex) {
        RestExceptionMessage exceptionResponse = new RestExceptionMessage(HttpStatus.UNAUTHORIZED, ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionResponse);
    }

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

    @ExceptionHandler(PostNotFoundException.class)
    private ResponseEntity<RestExceptionMessage> PostNotFoundException(PostNotFoundException ex) {
        RestExceptionMessage exceptionResponse = new RestExceptionMessage(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    @ExceptionHandler(FollowerRelationAlreadyExistsException.class)
    private ResponseEntity<RestExceptionMessage> PostNotFoundException(FollowerRelationAlreadyExistsException ex) {
        RestExceptionMessage exceptionResponse = new RestExceptionMessage(HttpStatus.CONFLICT, ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionResponse);
    }

    @ExceptionHandler(SelfFollowException.class)
    private ResponseEntity<RestExceptionMessage> PostNotFoundException(SelfFollowException ex) {
        RestExceptionMessage exceptionResponse = new RestExceptionMessage(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }
}
