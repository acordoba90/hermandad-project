package com.hermandadproject.gestionpersonajes.service.impl;

import com.hermandadproject.gestionpersonajes.exception.ColectivoAlreadyExistsException;
import com.hermandadproject.gestionpersonajes.mapper.ColectivoMapper;
import com.hermandadproject.gestionpersonajes.model.dto.ColectivoCreateRequest;
import com.hermandadproject.gestionpersonajes.model.dto.ColectivoResponse;
import com.hermandadproject.gestionpersonajes.model.entity.ColectivoEntity;
import com.hermandadproject.gestionpersonajes.repository.ColectivoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

class ColectivoServiceImplTest {

    private ColectivoRepository colectivoRepository;
    private ColectivoServiceImpl service;

    @BeforeEach
    void setUp() {
        colectivoRepository = mock(ColectivoRepository.class);
        service = new ColectivoServiceImpl(colectivoRepository, new ColectivoMapper());
    }

    @Test
    void createRechazaCodigoDuplicado() {
        ColectivoCreateRequest request = new ColectivoCreateRequest(
                "JUNTA_GOBIERNO",
                "Junta de Gobierno",
                "Descripcion"
        );
        when(colectivoRepository.existsByCodigo("JUNTA_GOBIERNO")).thenReturn(true);

        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(ColectivoAlreadyExistsException.class);
        verify(colectivoRepository, never()).save(any());
    }

    @Test
    void findAllActiveUsaConsultaDeActivosOrdenados() {
        ColectivoEntity hermanos = colectivo("HERMANOS", "Hermanos", true);
        ColectivoEntity junta = colectivo("JUNTA_GOBIERNO", "Junta de Gobierno", true);
        when(colectivoRepository.findByActivoTrueOrderByNombreAsc()).thenReturn(List.of(hermanos, junta));

        List<ColectivoResponse> response = service.findAllActive();

        assertThat(response).extracting(ColectivoResponse::codigo)
                .containsExactly("HERMANOS", "JUNTA_GOBIERNO");
        verify(colectivoRepository).findByActivoTrueOrderByNombreAsc();
    }

    private ColectivoEntity colectivo(String codigo, String nombre, Boolean activo) {
        ColectivoEntity entity = new ColectivoEntity();
        entity.setId(UUID.randomUUID());
        entity.setCodigo(codigo);
        entity.setNombre(nombre);
        entity.setDescripcion("Descripcion");
        entity.setActivo(activo);
        return entity;
    }
}
