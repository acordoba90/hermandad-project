package com.hermandadproject.gestionpersonajes.mapper;

import com.hermandadproject.gestionpersonajes.model.dto.RolPersonajeResponse;
import com.hermandadproject.gestionpersonajes.model.entity.ColectivoEntity;
import com.hermandadproject.gestionpersonajes.model.entity.RolPersonajeEntity;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class RolPersonajeMapperTest {

    private final RolPersonajeMapper mapper = new RolPersonajeMapper();

    @Test
    void toResponseMapeaRolYColectivoSinExponerRelacionesCompletas() {
        ColectivoEntity colectivo = new ColectivoEntity();
        colectivo.setId(UUID.randomUUID());
        colectivo.setCodigo("JUNTA_GOBIERNO");
        colectivo.setNombre("Junta de Gobierno");
        RolPersonajeEntity rol = new RolPersonajeEntity();
        rol.setId(UUID.randomUUID());
        rol.setColectivo(colectivo);
        rol.setCodigo("HERMANO_MAYOR");
        rol.setNombre("Hermano Mayor");
        rol.setDescripcion("Descripcion del rol");
        rol.setActivo(true);

        RolPersonajeResponse response = mapper.toResponse(rol);

        assertThat(response.id()).isEqualTo(rol.getId());
        assertThat(response.colectivoId()).isEqualTo(colectivo.getId());
        assertThat(response.colectivoCodigo()).isEqualTo("JUNTA_GOBIERNO");
        assertThat(response.colectivoNombre()).isEqualTo("Junta de Gobierno");
        assertThat(response.codigo()).isEqualTo("HERMANO_MAYOR");
        assertThat(response.nombre()).isEqualTo("Hermano Mayor");
        assertThat(response.descripcion()).isEqualTo("Descripcion del rol");
        assertThat(response.activo()).isTrue();
    }
}
