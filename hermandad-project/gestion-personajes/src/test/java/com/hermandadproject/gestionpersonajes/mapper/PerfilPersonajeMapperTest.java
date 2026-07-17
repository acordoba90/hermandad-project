package com.hermandadproject.gestionpersonajes.mapper;

import com.hermandadproject.gestionpersonajes.model.entity.ArquetipoPerfilEntity;
import com.hermandadproject.gestionpersonajes.model.entity.PerfilPersonajeEntity;
import com.hermandadproject.gestionpersonajes.model.entity.PersonajeEntity;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class PerfilPersonajeMapperTest {

    private final PerfilPersonajeMapper mapper = new PerfilPersonajeMapper();

    @Test
    void crearDesdeArquetipoCopiaTodosLosAtributosBase() {
        PersonajeEntity personaje = new PersonajeEntity();
        personaje.setId(UUID.randomUUID());
        ArquetipoPerfilEntity conciliador = arquetipoConciliador();

        PerfilPersonajeEntity perfil = mapper.crearDesdeArquetipo(personaje, conciliador);

        assertThat(perfil.getPersonaje()).isSameAs(personaje);
        assertThat(perfil.getArquetipoOrigen()).isSameAs(conciliador);
        assertThat(perfil.getNivel()).isEqualTo(1);
        assertThat(perfil.getExperiencia()).isZero();
        assertThat(perfil.getPuntosDesarrollo()).isZero();
        assertThat(perfil.getLiderazgo()).isEqualTo(70);
        assertThat(perfil.getCarisma()).isEqualTo(75);
        assertThat(perfil.getDiplomacia()).isEqualTo(90);
        assertThat(perfil.getOrganizacion()).isEqualTo(65);
        assertThat(perfil.getComunicacion()).isEqualTo(82);
        assertThat(perfil.getInfluencia()).isEqualTo(68);
        assertThat(perfil.getConocimientoCofrade()).isEqualTo(72);
        assertThat(perfil.getProtocolo()).isEqualTo(78);
        assertThat(perfil.getDevocion()).isEqualTo(72);
        assertThat(perfil.getDisciplina()).isEqualTo(68);
        assertThat(perfil.getEmpatia()).isEqualTo(92);
        assertThat(perfil.getLealtad()).isEqualTo(82);
        assertThat(perfil.getIntegridad()).isEqualTo(86);
        assertThat(perfil.getAmbicion()).isEqualTo(30);
        assertThat(perfil.getConflictividad()).isEqualTo(10);
        assertThat(perfil.getPopularidad()).isEqualTo(78);
        assertThat(perfil.getReputacion()).isEqualTo(76);
        assertThat(perfil.getActivo()).isTrue();
    }

    @Test
    void modificarArquetipoPosteriormenteNoModificaPerfilExistente() {
        PerfilPersonajeEntity perfil = mapper.crearDesdeArquetipo(new PersonajeEntity(), arquetipoConciliador());

        perfil.getArquetipoOrigen().setDiplomaciaBase(85);

        assertThat(perfil.getDiplomacia()).isEqualTo(90);
        assertThat(perfil.getArquetipoOrigen().getDiplomaciaBase()).isEqualTo(85);
    }

    @Test
    void dosPersonajesDesdeMismoArquetipoTienenPerfilesIndependientes() {
        ArquetipoPerfilEntity arquetipo = arquetipoConciliador();
        PerfilPersonajeEntity primero = mapper.crearDesdeArquetipo(new PersonajeEntity(), arquetipo);
        PerfilPersonajeEntity segundo = mapper.crearDesdeArquetipo(new PersonajeEntity(), arquetipo);

        primero.setDiplomacia(40);

        assertThat(segundo.getDiplomacia()).isEqualTo(90);
    }

    public static ArquetipoPerfilEntity arquetipoConciliador() {
        ArquetipoPerfilEntity entity = new ArquetipoPerfilEntity();
        entity.setId(UUID.fromString("40000000-0000-0000-0000-000000000001"));
        entity.setCodigo("CONCILIADOR");
        entity.setNombre("Conciliador");
        entity.setDescripcion("Descripcion");
        entity.setLiderazgoBase(70);
        entity.setCarismaBase(75);
        entity.setDiplomaciaBase(90);
        entity.setOrganizacionBase(65);
        entity.setComunicacionBase(82);
        entity.setInfluenciaBase(68);
        entity.setConocimientoCofradeBase(72);
        entity.setProtocoloBase(78);
        entity.setDevocionBase(72);
        entity.setDisciplinaBase(68);
        entity.setEmpatiaBase(92);
        entity.setLealtadBase(82);
        entity.setIntegridadBase(86);
        entity.setAmbicionBase(30);
        entity.setConflictividadBase(10);
        entity.setPopularidadBase(78);
        entity.setReputacionBase(76);
        entity.setActivo(true);
        return entity;
    }
}
