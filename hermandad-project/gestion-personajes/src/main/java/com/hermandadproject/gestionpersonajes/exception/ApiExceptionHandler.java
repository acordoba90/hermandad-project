package com.hermandadproject.gestionpersonajes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({
            ArquetipoPerfilNotFoundException.class,
            ColectivoNotFoundException.class,
            PerfilPersonajeNotFoundException.class,
            PersonajeNotFoundException.class,
            RolPersonajeNotFoundException.class
    })
    public ResponseEntity<Map<String, String>> handleNotFound(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler({
            ArquetipoPerfilInactiveException.class,
            ColectivoInactiveException.class,
            ColectivoAlreadyExistsException.class,
            PerfilPersonajeAlreadyExistsException.class,
            PerfilPersonajePointsInvalidException.class,
            PersonajeAlreadyExistsException.class,
            RolNoPerteneceAlColectivoException.class
    })
    public ResponseEntity<Map<String, String>> handleAlreadyExists(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(PerfilPersonajeAttributeOutOfRangeException.class)
    public ResponseEntity<Map<String, String>> handleBadRequest(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", ex.getMessage()));
    }
}
