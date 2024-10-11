package br.com.xgommiapi.exception;

public class GommiUserAuthenticationFailedException extends ApplicationException {
    public GommiUserAuthenticationFailedException(String message) {
        super(message);
    }
}