package com.code.camping.controller;

import com.code.camping.utils.dto.webResponse.Res;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<?> handleHttpServerErrorException(HttpServerErrorException e) {
        String message =e.getMessage();
        HttpStatus status = HttpStatus.BAD_REQUEST;

        if(e.getMessage().contains("not found")) {
            status = HttpStatus.NOT_FOUND;
        }
        return Res.renderJson(null, message, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return Res.renderJson(errors, "Validation Errors", HttpStatus.BAD_REQUEST);
    }

}
