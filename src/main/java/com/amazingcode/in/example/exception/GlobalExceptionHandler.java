package com.amazingcode.in.example.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.amazingcode.in.example.response.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = Logger.getLogger(GlobalExceptionHandler.class.getName());

    @ExceptionHandler(AlreadyPresentException.class)
    public ResponseEntity<ErrorResponse> handleAlreadyPresentException(AlreadyPresentException ex) {
        LOG.log(Level.WARNING, "AlreadyPresentException occurred: {0}", ex.getExceptionMessage());
        ErrorResponse errorResponse = new ErrorResponse(409, ex.getExceptionMessage(), HttpStatus.CONFLICT);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(NotPresentException.class)
    public ResponseEntity<ErrorResponse> handleNotPresentException(NotPresentException ex) {
        LOG.log(Level.INFO, "NotPresentException occurred: {0}", ex.getExceptionMessage());
        ErrorResponse errorResponse = new ErrorResponse(404, ex.getExceptionMessage(), HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        LOG.log(Level.SEVERE, "An unexpected exception occurred: {0}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(500, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
