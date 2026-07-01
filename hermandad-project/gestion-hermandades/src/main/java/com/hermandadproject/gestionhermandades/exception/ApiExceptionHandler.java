package com.hermandadproject.gestionhermandades.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(HermandadNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(HermandadNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(HermandadAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleAlreadyExists(HermandadAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(TipoHermandadNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleTipoHermandadNotFound(TipoHermandadNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(TipoHermandadCaracteristicasNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleTipoHermandadCaracteristicasNotFound(TipoHermandadCaracteristicasNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(TipoHermandadAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleTipoHermandadAlreadyExists(TipoHermandadAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(CarismaHermandadNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCarismaNotFound(CarismaHermandadNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(CarismaHermandadAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleCarismaAlreadyExists(CarismaHermandadAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(CarismaHermandadAssignmentException.class)
    public ResponseEntity<Map<String, String>> handleCarismaAssignment(CarismaHermandadAssignmentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(EconomiaHermandadValidationException.class)
    public ResponseEntity<Map<String, String>> handleEconomiaValidation(EconomiaHermandadValidationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(MovimientoEconomicoHermandadValidationException.class)
    public ResponseEntity<Map<String, String>> handleMovimientoEconomicoValidation(MovimientoEconomicoHermandadValidationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", ex.getMessage()));
    }
}
