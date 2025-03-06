package org.locations.optiroute.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException manve){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(manve.getMessage());
    }
    @ExceptionHandler(IOException.class)
    public ResponseEntity handleIOException(IOException ioe){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
    }
    @ExceptionHandler(FileLoadingException.class)
    public ResponseEntity handleFileLoadingException(FileLoadingException fle){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(fle.getMessage());
    }
}
