package com.example.Demo_E_Commerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class Handeler {
    @ExceptionHandler
    ResponseEntity<?> handleRunTime(RuntimeException ex) {
        return ResponseEntity.badRequest()
                .body(Map.of("Error", ex.getMessage()));
    }
    @ExceptionHandler
    ResponseEntity<?>handleException(Exception ex){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("Error" , ex.getMessage()));
    }
}
