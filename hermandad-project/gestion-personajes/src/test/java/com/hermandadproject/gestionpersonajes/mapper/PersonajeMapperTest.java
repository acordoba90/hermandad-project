package com.hermandadproject.gestionpersonajes.mapper;

import com.hermandadproject.gestionpersonajes.model.dto.PersonajeCreateRequest;
import com.hermandadproject.gestionpersonajes.model.dto.PersonajeResponse;
import com.hermandadproject.gestionpersonajes.model.dto.PersonajeUpdateRequest;
import com.hermandadproject.gestionpersonajes.model.entity.ColectivoEntity;
import com.hermandadproject.gestionpersonajes.model.entity.PersonajeEntity;
import com.hermandadproject.gestionpersonajes.model.entity.RolPersonajeEntity;
import com.hermandadproject.gestionpersonajes.model.enums.GenderEnum;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PersonajeMapperTest {

    private final PersonajeMapper mapper = new PersonajeMapper(new PerfilPersonajeMapper());

    @Test
    void toEntityMapeaCamposDeCreacion() {
        ColectivoEntity colectivo = colectivo();
        RolPersonajeEntity rol = rol(colectivo, "CAPATAZ", "Capataz", true);
        UUID usuarioId = UUID.randomUUID();
        UUID avatarId = UUID.randomUUID();

        PersonajeEntity entity = mapper.toEntity(new PersonajeCreateRequest(
                "PERFIL_COSTALERO",
                usuarioId,
                avatarId,
                colectivo.getId(),
                rol.getId(),
                "Manuel",
                "Rios",
                47,
                GenderEnum.MALE,
                "Barrio de la Calzada",
                "Capataz",
                "Descripcion breve del personaje",
                "Biografia completa del personaje",
                "Mantener la cuadrilla unida",
                "COSTALERO",
                true,
                null,
                "https://example.com/avatar.png"
        ), colectivo, rol);

        assertThat(entity.getCodigo()).isEqualTo("PERFIL_COSTALERO");
        assertThat(entity.getUsuarioId()).isEqualTo(usuarioId);
        assertThat(entity.getAvatarId()).isEqualTo(avatarId);
        assertThat(entity.getColectivo()).isSameAs(colectivo);
        assertThat(entity.getRolPersonaje()).isSameAs(rol);
        assertThat(entity.getNombre()).isEqualTo("Manuel");
        assertThat(entity.getApellidos()).isEqualTo("Rios");
        assertThat(entity.getEdad()).isEqualTo(47);
        assertThat(entity.getGenero()).isEqualTo(GenderEnum.MALE);
        assertThat(entity.getOrigen()).isEqualTo("Barrio de la Calzada");
        assertThat(entity.getProfesion()).isEqualTo("Capataz");
        assertThat(entity.getDescripcion()).isEqualTo("Descripcion breve del personaje");
        assertThat(entity.getBiografia()).isEqualTo("Biografia completa del personaje");
        assertThat(entity.getMotivacion()).isEqualTo("Mantener la cuadrilla unida");
        assertThat(entity.getTipoPersonaje()).isEqualTo("COSTALERO");
        assertThat(entity.getPersonalizado()).isTrue();
        assertThat(entity.getUrlAvatar()).isEqualTo("https://example.com/avatar.png");
        assertThat(entity.getActivo()).isTrue();
    }

    @Test
    void toResponseMapeaCamposDeEntidadSinExponerRelacionesCompletas() {
        ColectivoEntity colectivo = colectivo();
        RolPersonajeEntity rol = rol(colectivo, "PERIODISTA", "Periodista", true);
        UUID usuarioId = UUID.randomUUID();
        UUID avatarId = UUID.randomUUID();
        PersonajeEntity entity = new PersonajeEntity();
        entity.setId(UUID.randomUUID());
        entity.setCodigo("PERFIL_PERIODISTA");
        entity.setUsuarioId(usuarioId);
        entity.setAvatarId(avatarId);
        entity.setColectivo(colectivo);
        entity.setRolPersonaje(rol);
        entity.setNombre("Lucia");
        entity.setApellidos("Morales");
        entity.setEdad(32);
        entity.setGenero(GenderEnum.FEMALE);
        entity.setOrigen("Centro historico");
        entity.setProfesion("Periodista");
        entity.setDescripcion("Descripcion breve");
        entity.setBiografia("Biografia completa");
        entity.setMotivacion("Cubrir la actualidad cofrade");
        entity.setTipoPersonaje("PRENSA");
        entity.setPersonalizado(false);
        entity.setUrlAvatar("https://example.com/lucia.png");
        entity.setActivo(true);

        PersonajeResponse response = mapper.toResponse(entity);

        assertThat(response.id()).isEqualTo(entity.getId());
        assertThat(response.codigo()).isEqualTo("PERFIL_PERIODISTA");
        assertThat(response.usuarioId()).isEqualTo(usuarioId);
        assertThat(response.avatarId()).isEqualTo(avatarId);
        assertThat(response.colectivoId()).isEqualTo(colectivo.getId());
        assertThat(response.colectivoCode()).isEqualTo(colectivo.getCodigo());
        assertThat(response.colectivoName()).isEqualTo(colectivo.getNombre());
        assertThat(response.rolPersonajeId()).isEqualTo(rol.getId());
        assertThat(response.rolPersonajeCodigo()).isEqualTo("PERIODISTA");
        assertThat(response.rolPersonajeNombre()).isEqualTo("Periodista");
        assertThat(response.nombre()).isEqualTo("Lucia");
        assertThat(response.apellidos()).isEqualTo("Morales");
        assertThat(response.edad()).isEqualTo(32);
        assertThat(response.genero()).isEqualTo(GenderEnum.FEMALE);
        assertThat(response.origen()).isEqualTo("Centro historico");
        assertThat(response.profesion()).isEqualTo("Periodista");
        assertThat(response.descripcion()).isEqualTo("Descripcion breve");
        assertThat(response.biografia()).isEqualTo("Biografia completa");
        assertThat(response.motivacion()).isEqualTo("Cubrir la actualidad cofrade");
        assertThat(response.tipoPersonaje()).isEqualTo("PRENSA");
        assertThat(response.personalizado()).isFalse();
        assertThat(response.urlAvatar()).isEqualTo("https://example.com/lucia.png");
        assertThat(response.activo()).isTrue();
    }

    @Test
    void updateEntityMapeaCamposActualizables() {
        PersonajeEntity entity = new PersonajeEntity();
        ColectivoEntity colectivo = colectivo();
        RolPersonajeEntity rol = rol(colectivo, "HERMANO_MAYOR", "Hermano Mayor", true);
        UUID usuarioId = UUID.randomUUID();
        UUID avatarId = UUID.randomUUID();

        mapper.updateEntity(entity, new PersonajeUpdateRequest(
                usuarioId,
                avatarId,
                colectivo.getId(),
                rol.getId(),
                "Antonio",
                "Vargas",
                54,
                GenderEnum.MALE,
                "San Lorenzo",
                "Hermano mayor",
                "Descripcion actualizada",
                "Biografia actualizada",
                "Servir a la hermandad",
                "JUNTA",
                true,
                "https://example.com/antonio.png",
                false
        ), colectivo, rol);

        assertThat(entity.getUsuarioId()).isEqualTo(usuarioId);
        assertThat(entity.getAvatarId()).isEqualTo(avatarId);
        assertThat(entity.getColectivo()).isSameAs(colectivo);
        assertThat(entity.getRolPersonaje()).isSameAs(rol);
        assertThat(entity.getNombre()).isEqualTo("Antonio");
        assertThat(entity.getApellidos()).isEqualTo("Vargas");
        assertThat(entity.getEdad()).isEqualTo(54);
        assertThat(entity.getGenero()).isEqualTo(GenderEnum.MALE);
        assertThat(entity.getOrigen()).isEqualTo("San Lorenzo");
        assertThat(entity.getProfesion()).isEqualTo("Hermano mayor");
        assertThat(entity.getDescripcion()).isEqualTo("Descripcion actualizada");
        assertThat(entity.getBiografia()).isEqualTo("Biografia actualizada");
        assertThat(entity.getMotivacion()).isEqualTo("Servir a la hermandad");
        assertThat(entity.getTipoPersonaje()).isEqualTo("JUNTA");
        assertThat(entity.getPersonalizado()).isTrue();
        assertThat(entity.getUrlAvatar()).isEqualTo("https://example.com/antonio.png");
        assertThat(entity.getActivo()).isFalse();
    }

    private ColectivoEntity colectivo() {
        ColectivoEntity colectivo = new ColectivoEntity();
        colectivo.setId(UUID.randomUUID());
        colectivo.setCodigo("CUADRILLA");
        colectivo.setNombre("Cuadrilla");
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
