package com.hermandadproject.gestionpersonajes.service.impl;

import com.hermandadproject.gestionpersonajes.mapper.ArquetipoPerfilMapper;
import com.hermandadproject.gestionpersonajes.model.dto.ArquetipoPerfilResponse;
import com.hermandadproject.gestionpersonajes.repository.ArquetipoPerfilRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.hermandadproject.gestionpersonajes.mapper.PerfilPersonajeMapperTest.arquetipoConciliador;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ArquetipoPerfilServiceImplTest {

    @Test
    void consultaArquetiposActivosOrdenados() {
        ArquetipoPerfilRepository repository = mock(ArquetipoPerfilRepository.class);
        when(repository.findAllByActivoTrueOrderByNombreAsc()).thenReturn(List.of(arquetipoConciliador()));
        ArquetipoPerfilServiceImpl service = new ArquetipoPerfilServiceImpl(repository, new ArquetipoPerfilMapper());

        List<ArquetipoPerfilResponse> response = service.findAllActive();

        assertThat(response).extracting(ArquetipoPerfilResponse::codigo).containsExactly("CONCILIADOR");
        verify(repository).findAllByActivoTrueOrderByNombreAsc();
    }
}
