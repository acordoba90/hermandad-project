package com.hermandadproject.gestionpersonajes.model;

import com.hermandadproject.gestionpersonajes.model.dto.PerfilPersonalizadoRequest;

/**
 * Reglas funcionales compartidas para perfiles jugables de personajes.
 */
public final class PerfilPersonajeRules {

    public static final int TOTAL_PUNTOS_PERFIL_PERSONALIZADO = 1000;

    private PerfilPersonajeRules() {
    }

    /**
     * Calcula los puntos repartidos en un perfil personalizado.
     *
     * @param request atributos personalizados
     * @return suma total de atributos
     */
    public static int totalPuntos(PerfilPersonalizadoRequest request) {
        return request.liderazgo()
                + request.carisma()
                + request.diplomacia()
                + request.organizacion()
                + request.comunicacion()
                + request.influencia()
                + request.conocimientoCofrade()
                + request.protocolo()
                + request.devocion()
                + request.disciplina()
                + request.empatia()
                + request.lealtad()
                + request.integridad()
                + request.ambicion()
                + request.conflictividad()
                + request.popularidad()
                + request.reputacion();
    }
}
