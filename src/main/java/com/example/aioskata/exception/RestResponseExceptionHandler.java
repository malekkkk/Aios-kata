package com.example.aioskata.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

import static java.util.Collections.emptyMap;
import static java.util.stream.Collectors.toMap;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
@Slf4j
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AiosKataException.class)
    public ResponseEntity<Object> handleCityNotFoundException(AiosKataException ex, WebRequest request) {
        log.error("Error", ex);
        return ResponseEntity
                .status(ex.getStatus())
                .body(buildResponse(ex.getStatus(), ex.getMessage(), emptyMap()));
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request){

        log.error("Error", ex);

        final Map<String, String> errors = ex.getBindingResult().getAllErrors()
                .stream()
                .collect(toMap(
                        error -> ((FieldError) error).getField(),
                        DefaultMessageSourceResolvable::getDefaultMessage
                ));

        return ResponseEntity
                .status(BAD_REQUEST)
                .body(buildResponse(BAD_REQUEST, "Error validation request", errors));
    }
    private Map<String, Object> buildResponse(HttpStatus status, String message, Map<String, String> errors) {
        return Map.of(
                "status", status,
                "message", message,
                "errors", errors
        );
    }

}
