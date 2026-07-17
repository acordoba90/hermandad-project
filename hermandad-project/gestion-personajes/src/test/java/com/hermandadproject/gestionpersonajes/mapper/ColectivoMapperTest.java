package com.hermandadproject.gestionpersonajes.mapper;

import com.hermandadproject.gestionpersonajes.model.dto.ColectivoCreateRequest;
import com.hermandadproject.gestionpersonajes.model.dto.ColectivoResponse;
import com.hermandadproject.gestionpersonajes.model.dto.ColectivoUpdateRequest;
import com.hermandadproject.gestionpersonajes.model.entity.ColectivoEntity;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ColectivoMapperTest {

    private final ColectivoMapper mapper = new ColectivoMapper();

    @Test
    void toEntityMapeaCamposDeCreacion() {
        ColectivoEntity entity = mapper.toEntity(new ColectivoCreateRequest(
                "JUNTA_GOBIERNO",
                "Junta de Gobierno",
                "Descripcion funcional"
        ));

        assertThat(entity.getCodigo()).isEqualTo("JUNTA_GOBIERNO");
        assertThat(entity.getNombre()).isEqualTo("Junta de Gobierno");
        assertThat(entity.getDescripcion()).isEqualTo("Descripcion funcional");
        assertThat(entity.getActivo()).isTrue();
    }

    @Test
    void toResponseMapeaTodosLosCampos() {
        UUID id = UUID.randomUUID();
        Instant fechaCreacion = Instant.parse("2026-07-16T00:00:00Z");
        Instant fechaActualizacion = Instant.parse("2026-07-16T01:00:00Z");
        ColectivoEntity entity = colectivo(id, fechaCreacion, fechaActualizacion);

        ColectivoResponse response = mapper.toResponse(entity);

        assertThat(response.id()).isEqualTo(id);
        assertThat(response.codigo()).isEqualTo("HERMANOS");
        assertThat(response.nombre()).isEqualTo("Hermanos");
        assertThat(response.descripcion()).isEqualTo("Descripcion");
        assertThat(response.activo()).isTrue();
        assertThat(response.fechaCreacion()).isEqualTo(fechaCreacion);
        assertThat(response.fechaActualizacion()).isEqualTo(fechaActualizacion);
    }

    @Test
    void updateEntityNoModificaCodigoNiFechas() {
        Instant fechaCreacion = Instant.parse("2026-07-16T00:00:00Z");
        Instant fechaActualizacion = Instant.parse("2026-07-16T01:00:00Z");
        ColectivoEntity entity = colectivo(UUID.randomUUID(), fechaCreacion, fechaActualizacion);

        mapper.updateEntity(entity, new ColectivoUpdateRequest(
                "Hermanos actualizados",
                "Nueva descripcion",
                false
        ));

        assertThat(entity.getCodigo()).isEqualTo("HERMANOS");
        assertThat(entity.getNombre()).isEqualTo("Hermanos actualizados");
        assertThat(entity.getDescripcion()).isEqualTo("Nueva descripcion");
        assertThat(entity.getActivo()).isFalse();
        assertThat(entity.getFechaCreacion()).isEqualTo(fechaCreacion);
        assertThat(entity.getFechaActualizacion()).isEqualTo(fechaActualizacion);
    }

    private ColectivoEntity colectivo(UUID id, Instant fechaCreacion, Instant fechaActualizacion) {
        ColectivoEntity entity = new ColectivoEntity();
        entity.setId(id);
        entity.setCodigo("HERMANOS");
        entity.setNombre("Hermanos");
        entity.setDescripcion("Descripcion");
        entity.setActivo(true);
        setFechas(entity, fechaCreacion, fechaActualizacion);
        return entity;
    }

    private void setFechas(ColectivoEntity entity, Instant fechaCreacion, Instant fechaActualizacion) {
        try {
            java.lang.reflect.Field creacion = ColectivoEntity.class.getDeclaredField("fechaCreacion");
            java.lang.reflect.Field actualizacion = ColectivoEntity.class.getDeclaredField("fechaActualizacion");
            creacion.setAccessible(true);
            actualizacion.setAccessible(true);
            creacion.set(entity, fechaCreacion);
            actualizacion.set(entity, fechaActualizacion);
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException(exception);
        }
    }
}
