package com.hermandadproject.gestionusuarios.logging;

/**
 * Representa al usuario que inicia una operacion. Cuando la operacion es
 * publica o no existe autenticacion, el actor se identifica como ANONYMOUS.
 */
public record ActorContext(String actorUsuarioId, String actorNombreUsuario) {

    public static final String ANONYMOUS = "ANONYMOUS";

    /**
     * Crea un contexto para operaciones publicas sin usuario autenticado.
     *
     * @return actor anonimo.
     */
    public static ActorContext anonymous() {
        return new ActorContext(ANONYMOUS, ANONYMOUS);
    }
}
