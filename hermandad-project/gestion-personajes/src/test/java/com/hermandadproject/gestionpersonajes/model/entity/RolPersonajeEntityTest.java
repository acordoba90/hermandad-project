package com.hermandadproject.gestionpersonajes.model.entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class RolPersonajeEntityTest {

    @Test
    void rolPerteneceAUnColectivoYPuedeCompartirsePorVariosPersonajes() {
        ColectivoEntity colectivo = new ColectivoEntity();
        colectivo.setId(UUID.randomUUID());
        RolPersonajeEntity rol = new RolPersonajeEntity();
        rol.setId(UUID.randomUUID());
        rol.setColectivo(colectivo);
        PersonajeEntity primero = new PersonajeEntity();
        PersonajeEntity segundo = new PersonajeEntity();

        primero.setColectivo(colectivo);
        primero.setRolPersonaje(rol);
        segundo.setColectivo(colectivo);
        segundo.setRolPersonaje(rol);

        assertThat(rol.getColectivo()).isSameAs(colectivo);
        assertThat(primero.getRolPersonaje()).isSameAs(rol);
        assertThat(segundo.getRolPersonaje()).isSameAs(rol);
    }
}
