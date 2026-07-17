package com.hermandadproject.gestionpersonajes.model.entity;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ColectivoEntityTest {

    @Test
    void contieneTodosLosCamposDelCatalogo() throws Exception {
        assertField("id", UUID.class);
        assertField("codigo", String.class);
        assertField("nombre", String.class);
        assertField("descripcion", String.class);
        assertField("activo", Boolean.class);
        assertField("fechaCreacion", Instant.class);
        assertField("fechaActualizacion", Instant.class);
    }

    @Test
    void prePersistInicializaIdActivoYFechas() throws Exception {
        ColectivoEntity entity = new ColectivoEntity();

        invoke(entity, "prePersist");

        assertThat(entity.getId()).isNotNull();
        assertThat(entity.getActivo()).isTrue();
        assertThat(entity.getFechaCreacion()).isNotNull();
        assertThat(entity.getFechaActualizacion()).isNotNull();
    }

    @Test
    void preUpdateActualizaSoloFechaActualizacion() throws Exception {
        ColectivoEntity entity = new ColectivoEntity();
        invoke(entity, "prePersist");
        Instant fechaCreacion = entity.getFechaCreacion();
        setField(entity, "fechaActualizacion", Instant.parse("2026-07-16T00:00:00Z"));

        invoke(entity, "preUpdate");

        assertThat(entity.getFechaCreacion()).isEqualTo(fechaCreacion);
        assertThat(entity.getFechaActualizacion()).isAfter(Instant.parse("2026-07-16T00:00:00Z"));
    }

    private void assertField(String name, Class<?> type) throws Exception {
        assertThat(ColectivoEntity.class.getDeclaredField(name).getType()).isEqualTo(type);
    }

    private void invoke(ColectivoEntity entity, String methodName) throws Exception {
        Method method = ColectivoEntity.class.getDeclaredMethod(methodName);
        method.setAccessible(true);
        method.invoke(entity);
    }

    private void setField(ColectivoEntity entity, String fieldName, Object value) throws Exception {
        Field field = ColectivoEntity.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(entity, value);
    }
}
