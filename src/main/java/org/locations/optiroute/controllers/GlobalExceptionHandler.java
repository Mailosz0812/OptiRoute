package org.locations.optiroute.controllers;

import org.locations.optiroute.exceptions.AddressNotFoundException;
import org.locations.optiroute.exceptions.ResponseCodeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.IOException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseCodeException.class)
    public ResponseEntity handleResponseCodeException(ResponseCodeException rce){
        return ResponseEntity.status(rce.getStatus()).body(rce.getMessage());
    }
    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity handleAddressNotFoundException(AddressNotFoundException anfe){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(anfe.getMessage()+anfe.getName());
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handleHttpMessageNotReadableException(HttpMessageNotReadableException hmnre){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("JSON parsing Error"+ hmnre.getMessage());
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException matme){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad parameter type"+matme.getMessage());
    }
}
