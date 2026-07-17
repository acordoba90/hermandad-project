package com.hermandadproject.gestionpersonajes.service.impl;

import com.hermandadproject.gestionpersonajes.exception.ColectivoInactiveException;
import com.hermandadproject.gestionpersonajes.exception.RolNoPerteneceAlColectivoException;
import com.hermandadproject.gestionpersonajes.exception.RolPersonajeNotFoundException;
import com.hermandadproject.gestionpersonajes.mapper.RolPersonajeMapper;
import com.hermandadproject.gestionpersonajes.model.dto.RolPersonajeResponse;
import com.hermandadproject.gestionpersonajes.model.entity.ColectivoEntity;
import com.hermandadproject.gestionpersonajes.model.entity.RolPersonajeEntity;
import com.hermandadproject.gestionpersonajes.repository.ColectivoRepository;
import com.hermandadproject.gestionpersonajes.repository.RolPersonajeRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RolPersonajeServiceImplTest {

    @Test
    void findAllActiveConsultaSoloActivosOrdenados() {
        RolPersonajeRepository rolRepository = mock(RolPersonajeRepository.class);
        ColectivoRepository colectivoRepository = mock(ColectivoRepository.class);
        RolPersonajeServiceImpl service = new RolPersonajeServiceImpl(
                rolRepository,
                colectivoRepository,
                new RolPersonajeMapper()
        );
        ColectivoEntity colectivo = colectivo("HERMANOS", true);
        when(rolRepository.findAllByActivoTrueOrderByNombreAsc())
                .thenReturn(List.of(rol(colectivo, "HERMANO", "Hermano", true)));

        List<RolPersonajeResponse> response = service.findAllActive();

        assertThat(response).extracting(RolPersonajeResponse::codigo).containsExactly("HERMANO");
        verify(rolRepository).findAllByActivoTrueOrderByNombreAsc();
    }

    @Test
    void findActiveByColectivoIdDevuelveSoloRolesDelColectivoActivo() {
        RolPersonajeRepository rolRepository = mock(RolPersonajeRepository.class);
        ColectivoRepository colectivoRepository = mock(ColectivoRepository.class);
        RolPersonajeServiceImpl service = new RolPersonajeServiceImpl(
                rolRepository,
                colectivoRepository,
                new RolPersonajeMapper()
        );
        ColectivoEntity colectivo = colectivo("CUADRILLA_COSTALEROS", true);
        RolPersonajeEntity capataz = rol(colectivo, "CAPATAZ", "Capataz", true);
        RolPersonajeEntity costalero = rol(colectivo, "COSTALERO", "Costalero", true);
        when(colectivoRepository.findById(colectivo.getId())).thenReturn(Optional.of(colectivo));
        when(rolRepository.findAllByColectivoIdAndActivoTrueOrderByNombreAsc(colectivo.getId()))
                .thenReturn(List.of(capataz, costalero));

        List<RolPersonajeResponse> response = service.findActiveByColectivoId(colectivo.getId());

        assertThat(response).extracting(RolPersonajeResponse::codigo)
                .containsExactly("CAPATAZ", "COSTALERO");
        assertThat(response).allSatisfy(rol -> assertThat(rol.colectivoId()).isEqualTo(colectivo.getId()));
    }

    @Test
    void findActiveByColectivoIdRechazaColectivoInactivo() {
        RolPersonajeRepository rolRepository = mock(RolPersonajeRepository.class);
        ColectivoRepository colectivoRepository = mock(ColectivoRepository.class);
        RolPersonajeServiceImpl service = new RolPersonajeServiceImpl(
                rolRepository,
                colectivoRepository,
                new RolPersonajeMapper()
        );
        ColectivoEntity colectivo = colectivo("HERMANOS", false);
        when(colectivoRepository.findById(colectivo.getId())).thenReturn(Optional.of(colectivo));

        assertThatThrownBy(() -> service.findActiveByColectivoId(colectivo.getId()))
                .isInstanceOf(ColectivoInactiveException.class);
    }

    @Test
    void findActiveEntityByIdRechazaRolesInexistentesOInactivos() {
        RolPersonajeRepository rolRepository = mock(RolPersonajeRepository.class);
        ColectivoRepository colectivoRepository = mock(ColectivoRepository.class);
        RolPersonajeServiceImpl service = new RolPersonajeServiceImpl(
                rolRepository,
                colectivoRepository,
                new RolPersonajeMapper()
        );
        UUID rolId = UUID.randomUUID();
        when(rolRepository.findByIdAndActivoTrue(rolId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findActiveEntityById(rolId))
                .isInstanceOf(RolPersonajeNotFoundException.class);
    }

    @Test
    void validarPertenenciaAceptaRolDelMismoColectivoYRechazaOtroColectivo() {
        RolPersonajeServiceImpl service = new RolPersonajeServiceImpl(
                mock(RolPersonajeRepository.class),
                mock(ColectivoRepository.class),
                new RolPersonajeMapper()
        );
        ColectivoEntity junta = colectivo("JUNTA_GOBIERNO", true);
        ColectivoEntity cuadrilla = colectivo("CUADRILLA_COSTALEROS", true);
        RolPersonajeEntity hermanoMayor = rol(junta, "HERMANO_MAYOR", "Hermano Mayor", true);
        RolPersonajeEntity costalero = rol(cuadrilla, "COSTALERO", "Costalero", true);

        service.validarPertenencia(hermanoMayor, junta);

        assertThatThrownBy(() -> service.validarPertenencia(costalero, junta))
                .isInstanceOf(RolNoPerteneceAlColectivoException.class);
    }

    private ColectivoEntity colectivo(String codigo, Boolean activo) {
        ColectivoEntity colectivo = new ColectivoEntity();
        colectivo.setId(UUID.randomUUID());
        colectivo.setCodigo(codigo);
        colectivo.setNombre(codigo);
        colectivo.setActivo(activo);
        return colectivo;
    }

    private RolPersonajeEntity rol(ColectivoEntity colectivo, String codigo, String nombre, Boolean activo) {
        RolPersonajeEntity rol = new RolPersonajeEntity();
        rol.setId(UUID.randomUUID());
        rol.setColectivo(colectivo);
        rol.setCodigo(codigo);
        rol.setNombre(nombre);
        rol.setDescripcion("Descripcion del rol");
        rol.setActivo(activo);
        return rol;
    }
}
