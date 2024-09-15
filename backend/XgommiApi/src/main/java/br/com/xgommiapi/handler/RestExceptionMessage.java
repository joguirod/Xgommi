package br.com.xgommiapi.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
public class RestExceptionMessage {
    private HttpStatus status;
    private String message;
}
