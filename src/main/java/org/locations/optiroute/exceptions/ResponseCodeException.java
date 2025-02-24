package org.locations.optiroute.exceptions;

import org.springframework.http.HttpStatus;

public class ResponseCodeException extends ResponseException {
    public ResponseCodeException(String message, HttpStatus status) {
        super(message,status);
    }
}
