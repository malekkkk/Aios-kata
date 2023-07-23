package com.example.aioskata.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@AllArgsConstructor
public class AiosKataException extends RuntimeException {
    private final HttpStatus status;

    public AiosKataException(String message) {
        super(message);
        this.status = BAD_REQUEST;
    }

    public AiosKataException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
