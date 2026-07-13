package com.hermandadproject.gestionusuarios.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Agrupa los valores iniciales de juego asignados a los usuarios.
 * Las propiedades se leen con el prefijo {@code hermandad.game}.
 */
@ConfigurationProperties(prefix = "hermandad.game")
public class HermandadGameProperties {

    private int initialLevel;
    private int initialExperience;
    private int maxLevel;
    private String defaultAvatar;

    /**
     * Devuelve el nivel inicial de un usuario nuevo.
     *
     * @return nivel inicial configurado.
     */
    public int getInitialLevel() {
        return initialLevel;
    }

    /**
     * Actualiza el nivel inicial de un usuario nuevo.
     *
     * @param initialLevel nivel inicial configurado.
     */
    public void setInitialLevel(int initialLevel) {
        this.initialLevel = initialLevel;
    }

    /**
     * Devuelve la experiencia inicial de un usuario nuevo.
     *
     * @return experiencia inicial configurada.
     */
    public int getInitialExperience() {
        return initialExperience;
    }

    /**
     * Actualiza la experiencia inicial de un usuario nuevo.
     *
     * @param initialExperience experiencia inicial configurada.
     */
    public void setInitialExperience(int initialExperience) {
        this.initialExperience = initialExperience;
    }

    /**
     * Devuelve el nivel maximo permitido por la configuracion.
     *
     * @return nivel maximo configurado.
     */
    public int getMaxLevel() {
        return maxLevel;
    }

    /**
     * Actualiza el nivel maximo permitido por la configuracion.
     *
     * @param maxLevel nivel maximo configurado.
     */
    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    /**
     * Devuelve el avatar por defecto para perfiles nuevos.
     *
     * @return nombre o ruta publica del avatar por defecto.
     */
    public String getDefaultAvatar() {
        return defaultAvatar;
    }

    /**
     * Actualiza el avatar por defecto para perfiles nuevos.
     *
     * @param defaultAvatar nombre o ruta publica del avatar por defecto.
     */
    public void setDefaultAvatar(String defaultAvatar) {
        this.defaultAvatar = defaultAvatar;
    }
}
