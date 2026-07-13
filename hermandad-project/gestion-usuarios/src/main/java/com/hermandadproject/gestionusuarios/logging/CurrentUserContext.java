package com.hermandadproject.gestionusuarios.logging;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Obtiene el actor autenticado desde Spring Security sin introducir un sistema
 * de autenticacion nuevo. Si no hay autenticacion real, devuelve ANONYMOUS.
 */
@Component
public class CurrentUserContext {

    /**
     * Resuelve el usuario que inicia la operacion actual.
     *
     * @return contexto de actor seguro para logs.
     */
    public ActorContext getCurrentActor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            return ActorContext.anonymous();
        }

        String principalName = authentication.getName();
        if (principalName == null || principalName.isBlank()) {
            return ActorContext.anonymous();
        }

        return new ActorContext(principalName, principalName);
    }
}
