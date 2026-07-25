package com.hermandadproject.gestionpersonajes.service.impl;

import com.hermandadproject.gestionpersonajes.exception.ColectivoInactiveException;
import com.hermandadproject.gestionpersonajes.exception.RolNoPerteneceAlColectivoException;
import com.hermandadproject.gestionpersonajes.mapper.PerfilPersonajeMapper;
import com.hermandadproject.gestionpersonajes.mapper.PersonajeMapper;
import com.hermandadproject.gestionpersonajes.model.dto.PersonajeCreateRequest;
import com.hermandadproject.gestionpersonajes.model.dto.PersonajeResponse;
import com.hermandadproject.gestionpersonajes.model.entity.ColectivoEntity;
import com.hermandadproject.gestionpersonajes.model.entity.PersonajeEntity;
import com.hermandadproject.gestionpersonajes.model.entity.RolPersonajeEntity;
import com.hermandadproject.gestionpersonajes.model.enums.GenderEnum;
import com.hermandadproject.gestionpersonajes.repository.ColectivoRepository;
import com.hermandadproject.gestionpersonajes.repository.PersonajeRepository;
import com.hermandadproject.gestionpersonajes.service.PerfilPersonajeService;
import com.hermandadproject.gestionpersonajes.service.RolPersonajeService;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PersonajeServiceImplTest {

    @Test
    void createCreaPerfilCuandoRecibeArquetipo() {
        PersonajeRepository personajeRepository = mock(PersonajeRepository.class);
        ColectivoRepository colectivoRepository = mock(ColectivoRepository.class);
        PerfilPersonajeService perfilPersonajeService = mock(PerfilPersonajeService.class);
        RolPersonajeService rolPersonajeService = mock(RolPersonajeService.class);
        PersonajeServiceImpl service = service(
                personajeRepository,
                colectivoRepository,
                perfilPersonajeService,
                rolPersonajeService
        );
        ColectivoEntity colectivo = colectivo();
        RolPersonajeEntity rol = rol(colectivo, "HERMANO", "Hermano", true);
        UUID arquetipoId = UUID.randomUUID();
        when(colectivoRepository.findById(colectivo.getId())).thenReturn(Optional.of(colectivo));
        when(rolPersonajeService.findActiveEntityById(rol.getId())).thenReturn(rol);
        when(personajeRepository.save(any())).thenAnswer(invocation -> {
            PersonajeEntity entity = invocation.getArgument(0);
            return guardarConId(entity);
        });

        service.create(new PersonajeCreateRequest(
                "PERSONAJE_TEST",
                null,
                null,
                colectivo.getId(),
                rol.getId(),
                "Nombre",
                null,
                30,
                GenderEnum.NO_ESPECIFICADO,
                null,
                null,
                null,
                null,
                null,
                true,
                arquetipoId,
                null
        ));

        verify(perfilPersonajeService).crearDesdeArquetipo(any(PersonajeEntity.class), org.mockito.Mockito.eq(arquetipoId));
        verify(rolPersonajeService).validarPertenencia(rol, colectivo);
    }

    @Test
    void createRechazaRolDeOtroColectivoYNoPersistePersonaje() {
        PersonajeRepository personajeRepository = mock(PersonajeRepository.class);
        ColectivoRepository colectivoRepository = mock(ColectivoRepository.class);
        PerfilPersonajeService perfilPersonajeService = mock(PerfilPersonajeService.class);
        RolPersonajeService rolPersonajeService = mock(RolPersonajeService.class);
        PersonajeServiceImpl service = service(
                personajeRepository,
                colectivoRepository,
                perfilPersonajeService,
                rolPersonajeService
        );
        ColectivoEntity junta = colectivo("JUNTA_GOBIERNO", "Junta de Gobierno", true);
        ColectivoEntity cuadrilla = colectivo("CUADRILLA_COSTALEROS", "Cuadrilla de Costaleros", true);
        RolPersonajeEntity costalero = rol(cuadrilla, "COSTALERO", "Costalero", true);
        when(colectivoRepository.findById(junta.getId())).thenReturn(Optional.of(junta));
        when(rolPersonajeService.findActiveEntityById(costalero.getId())).thenReturn(costalero);
        doThrow(new RolNoPerteneceAlColectivoException("El rol de personaje no pertenece al colectivo seleccionado"))
                .when(rolPersonajeService).validarPertenencia(costalero, junta);

        assertThatThrownBy(() -> service.create(request(junta.getId(), costalero.getId())))
                .isInstanceOf(RolNoPerteneceAlColectivoException.class);

        verify(personajeRepository, never()).save(any());
        verify(perfilPersonajeService, never()).crearDesdeArquetipo(any(), any());
    }

    @Test
    void createPersistePersonajeConColectivoYRolCoherentes() {
        PersonajeRepository personajeRepository = mock(PersonajeRepository.class);
        ColectivoRepository colectivoRepository = mock(ColectivoRepository.class);
        PerfilPersonajeService perfilPersonajeService = mock(PerfilPersonajeService.class);
        RolPersonajeService rolPersonajeService = mock(RolPersonajeService.class);
        PersonajeServiceImpl service = service(
                personajeRepository,
                colectivoRepository,
                perfilPersonajeService,
                rolPersonajeService
        );
        ColectivoEntity junta = colectivo("JUNTA_GOBIERNO", "Junta de Gobierno", true);
        RolPersonajeEntity hermanoMayor = rol(junta, "HERMANO_MAYOR", "Hermano Mayor", true);
        when(colectivoRepository.findById(junta.getId())).thenReturn(Optional.of(junta));
        when(rolPersonajeService.findActiveEntityById(hermanoMayor.getId())).thenReturn(hermanoMayor);
        when(personajeRepository.save(any())).thenAnswer(invocation -> {
            PersonajeEntity entity = invocation.getArgument(0);
            return guardarConId(entity);
        });

        PersonajeResponse response = service.create(request(junta.getId(), hermanoMayor.getId()));

        assertThat(response.colectivoId()).isEqualTo(junta.getId());
        assertThat(response.colectivoCode()).isEqualTo("JUNTA_GOBIERNO");
        assertThat(response.rolPersonajeId()).isEqualTo(hermanoMayor.getId());
        assertThat(response.rolPersonajeCodigo()).isEqualTo("HERMANO_MAYOR");
        assertThat(response.rolPersonajeNombre()).isEqualTo("Hermano Mayor");
        verify(personajeRepository).save(any(PersonajeEntity.class));
        verify(rolPersonajeService).validarPertenencia(hermanoMayor, junta);
    }

    @Test
    void createRechazaColectivoInactivo() {
        PersonajeRepository personajeRepository = mock(PersonajeRepository.class);
        ColectivoRepository colectivoRepository = mock(ColectivoRepository.class);
        PerfilPersonajeService perfilPersonajeService = mock(PerfilPersonajeService.class);
        RolPersonajeService rolPersonajeService = mock(RolPersonajeService.class);
        PersonajeServiceImpl service = service(
                personajeRepository,
                colectivoRepository,
                perfilPersonajeService,
                rolPersonajeService
        );
        ColectivoEntity colectivo = colectivo("HERMANOS", "Hermanos", false);
        UUID rolId = UUID.randomUUID();
        when(colectivoRepository.findById(colectivo.getId())).thenReturn(Optional.of(colectivo));

        assertThatThrownBy(() -> service.create(request(colectivo.getId(), rolId)))
                .isInstanceOf(ColectivoInactiveException.class);

        verify(rolPersonajeService, never()).findActiveEntityById(any());
        verify(personajeRepository, never()).save(any());
    }

    private PersonajeServiceImpl service(
            PersonajeRepository personajeRepository,
            ColectivoRepository colectivoRepository,
            PerfilPersonajeService perfilPersonajeService,
            RolPersonajeService rolPersonajeService
    ) {
        return new PersonajeServiceImpl(
                personajeRepository,
                colectivoRepository,
                new PersonajeMapper(new PerfilPersonajeMapper()),
                perfilPersonajeService,
                rolPersonajeService
        );
    }

    private PersonajeEntity guardarConId(PersonajeEntity entity) {
        entity.setId(UUID.randomUUID());
        return entity;
    }

    private ColectivoEntity colectivo() {
        return colectivo("HERMANOS", "Hermanos", true);
    }

    private ColectivoEntity colectivo(String codigo, String nombre, Boolean activo) {
        ColectivoEntity colectivo = new ColectivoEntity();
        colectivo.setId(UUID.randomUUID());
        colectivo.setCodigo(codigo);
        colectivo.setNombre(nombre);
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

    private PersonajeCreateRequest request(UUID colectivoId, UUID rolPersonajeId) {
        return request(colectivoId, rolPersonajeId, null);
    }

    private PersonajeCreateRequest request(UUID colectivoId, UUID rolPersonajeId, UUID arquetipoPerfilId) {
        return new PersonajeCreateRequest(
                "PERSONAJE_TEST",
                null,
                null,
                colectivoId,
                rolPersonajeId,
                "Nombre",
                null,
                30,
                GenderEnum.NO_ESPECIFICADO,
                null,
                null,
                null,
                null,
                null,
                true,
                arquetipoPerfilId,
                null
        );
    }

}
