package com.hermandadproject.gestionpersonajes.service.impl;

import com.hermandadproject.gestionpersonajes.exception.ArquetipoPerfilInactiveException;
import com.hermandadproject.gestionpersonajes.exception.ArquetipoPerfilNotFoundException;
import com.hermandadproject.gestionpersonajes.exception.PerfilPersonajeAlreadyExistsException;
import com.hermandadproject.gestionpersonajes.mapper.PerfilPersonajeMapper;
import com.hermandadproject.gestionpersonajes.model.entity.ArquetipoPerfilEntity;
import com.hermandadproject.gestionpersonajes.model.entity.PerfilPersonajeEntity;
import com.hermandadproject.gestionpersonajes.model.entity.PersonajeEntity;
import com.hermandadproject.gestionpersonajes.repository.ArquetipoPerfilRepository;
import com.hermandadproject.gestionpersonajes.repository.PerfilPersonajeRepository;
import com.hermandadproject.gestionpersonajes.repository.PersonajeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static com.hermandadproject.gestionpersonajes.mapper.PerfilPersonajeMapperTest.arquetipoConciliador;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PerfilPersonajeServiceImplTest {

    private PerfilPersonajeRepository perfilRepository;
    private ArquetipoPerfilRepository arquetipoRepository;
    private PerfilPersonajeServiceImpl service;

    @BeforeEach
    void setUp() {
        perfilRepository = mock(PerfilPersonajeRepository.class);
        arquetipoRepository = mock(ArquetipoPerfilRepository.class);
        service = new PerfilPersonajeServiceImpl(
                perfilRepository,
                mock(PersonajeRepository.class),
                arquetipoRepository,
                new PerfilPersonajeMapper()
        );
    }

    @Test
    void noCreaPerfilSiElPersonajeYaTieneUno() {
        PersonajeEntity personaje = personaje();
        UUID arquetipoId = UUID.randomUUID();
        when(perfilRepository.existsByPersonajeId(personaje.getId())).thenReturn(true);

        assertThatThrownBy(() -> service.crearDesdeArquetipo(personaje, arquetipoId))
                .isInstanceOf(PerfilPersonajeAlreadyExistsException.class);
        verify(perfilRepository, never()).save(any());
    }

    @Test
    void noCreaPerfilDesdeArquetipoInexistente() {
        PersonajeEntity personaje = personaje();
        UUID arquetipoId = UUID.randomUUID();
        when(arquetipoRepository.findById(arquetipoId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.crearDesdeArquetipo(personaje, arquetipoId))
                .isInstanceOf(ArquetipoPerfilNotFoundException.class);
        verify(perfilRepository, never()).save(any());
    }

    @Test
    void noCreaPerfilDesdeArquetipoInactivo() {
        PersonajeEntity personaje = personaje();
        ArquetipoPerfilEntity arquetipo = arquetipoConciliador();
        arquetipo.setActivo(false);
        when(arquetipoRepository.findById(arquetipo.getId())).thenReturn(Optional.of(arquetipo));

        assertThatThrownBy(() -> service.crearDesdeArquetipo(personaje, arquetipo.getId()))
                .isInstanceOf(ArquetipoPerfilInactiveException.class);
        verify(perfilRepository, never()).save(any());
    }

    @Test
    void creaPerfilDesdeArquetipoActivo() {
        PersonajeEntity personaje = personaje();
        ArquetipoPerfilEntity arquetipo = arquetipoConciliador();
        when(arquetipoRepository.findById(arquetipo.getId())).thenReturn(Optional.of(arquetipo));
        when(perfilRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        PerfilPersonajeEntity perfil = service.crearDesdeArquetipo(personaje, arquetipo.getId());

        assertThat(perfil.getDiplomacia()).isEqualTo(90);
        assertThat(perfil.getNivel()).isEqualTo(1);
        assertThat(perfil.getExperiencia()).isZero();
        assertThat(perfil.getPuntosDesarrollo()).isZero();
        assertThat(personaje.getPerfil()).isSameAs(perfil);
        verify(perfilRepository).save(any());
    }

    private PersonajeEntity personaje() {
        PersonajeEntity entity = new PersonajeEntity();
        entity.setId(UUID.randomUUID());
        return entity;
    }
}
