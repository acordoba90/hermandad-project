package com.hermandadproject.gestioninventario.exception;

public class InvalidInventoryQuantityException extends RuntimeException {
    public InvalidInventoryQuantityException(String message) {
        super(message);
    }
}
