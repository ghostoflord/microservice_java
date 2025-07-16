package com.example.auth_service.exception;

import com.example.auth_service.domain.response.RestResponse;
import com.example.auth_service.exception.IdInvalidException;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<RestResponse<Void>> handleAccessDenied(AccessDeniedException ex) {
        RestResponse<Void> response = new RestResponse<>();
        response.setStatusCode(403);
        response.setError("Access denied: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse<Map<String, String>>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        RestResponse<Map<String, String>> response = new RestResponse<>();
        response.setStatusCode(400);
        response.setMessage("Validation failed");
        response.setData(errors);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<RestResponse<Object>> handleConstraintViolation(ConstraintViolationException ex) {
        RestResponse<Object> response = new RestResponse<>();
        response.setStatusCode(400);
        response.setError("Validation error: " + ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<RestResponse<Object>> handleRuntime(RuntimeException ex) {
        RestResponse<Object> response = new RestResponse<>();
        response.setStatusCode(500);
        response.setError("Internal error: " + ex.getMessage());
        return ResponseEntity.status(500).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResponse<Object>> handleGeneral(Exception ex) {
        RestResponse<Object> response = new RestResponse<>();
        response.setStatusCode(500);
        response.setError("Unexpected error: " + ex.getMessage());
        return ResponseEntity.status(500).body(response);
    }

    @ExceptionHandler(value = {
            UsernameNotFoundException.class,
            BadCredentialsException.class,
            IdInvalidException.class

    })
    public ResponseEntity<RestResponse<Object>> handleIdException(Exception ex) {
        RestResponse<Object> res = new RestResponse<Object>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setMessage(ex.getMessage());
        res.setError("Exception occurs...");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }
}