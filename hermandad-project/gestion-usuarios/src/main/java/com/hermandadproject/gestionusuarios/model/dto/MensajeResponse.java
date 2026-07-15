package com.hermandadproject.gestionusuarios.model.dto;

/**
 * Respuesta generica para operaciones que no deben exponer datos sensibles.
 *
 * @param mensaje texto publico de resultado.
 */
public record MensajeResponse(String mensaje) {
}
