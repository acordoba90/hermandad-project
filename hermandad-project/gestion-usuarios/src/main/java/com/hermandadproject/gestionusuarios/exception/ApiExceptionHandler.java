package com.hermandadproject.gestionusuarios.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler({UserNotFoundException.class, UserProfileNotFoundException.class})
    public ResponseEntity<Map<String, String>> handleNotFound(RuntimeException ex, HttpServletRequest request) {
        logFunctionalError(ex, request, HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler({UserAlreadyExistsException.class, UserProfileAlreadyExistsException.class})
    public ResponseEntity<Map<String, String>> handleAlreadyExists(RuntimeException ex, HttpServletRequest request) {
        logFunctionalError(ex, request, HttpStatus.CONFLICT);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(InvalidUserCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleInvalidUserCredentials(RuntimeException ex, HttpServletRequest request) {
        logFunctionalError(ex, request, HttpStatus.UNAUTHORIZED);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<Map<String, String>> handleBadRequest(RuntimeException ex, HttpServletRequest request) {
        logFunctionalError(ex, request, HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleUnexpected(Exception ex, HttpServletRequest request) {
        LOGGER.error(
                "Error inesperado procesando la peticion. path={}, metodo={}",
                request.getRequestURI(),
                request.getMethod(),
                ex
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Error interno del servidor"));
    }

    private void logFunctionalError(RuntimeException ex, HttpServletRequest request, HttpStatus status) {
        LOGGER.warn(
                "Error funcional procesando la peticion. tipo={}, mensaje={}, path={}, metodo={}, status={}",
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                request.getRequestURI(),
                request.getMethod(),
                status.value()
        );
    }
}
