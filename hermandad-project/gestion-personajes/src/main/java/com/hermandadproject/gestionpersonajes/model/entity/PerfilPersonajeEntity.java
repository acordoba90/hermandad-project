package com.hermandadproject.gestionpersonajes.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

/**
 * Perfil evolutivo propio de un personaje, inicializado desde un arquetipo o definido manualmente.
 */
@Entity
@Table(name = "perfiles_personaje")
public class PerfilPersonajeEntity {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "char(36)")
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personaje_id", nullable = false, columnDefinition = "char(36)")
    private PersonajeEntity personaje;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "arquetipo_origen_id", columnDefinition = "char(36)")
    private ArquetipoPerfilEntity arquetipoOrigen;

    @Column(nullable = false)
    private Integer nivel;

    @Column(nullable = false)
    private Long experiencia;

    @Column(name = "puntos_desarrollo", nullable = false)
    private Integer puntosDesarrollo;

    @Column(nullable = false)
    private Integer liderazgo;

    @Column(nullable = false)
    private Integer carisma;

    @Column(nullable = false)
    private Integer diplomacia;

    @Column(nullable = false)
    private Integer organizacion;

    @Column(nullable = false)
    private Integer comunicacion;

    @Column(nullable = false)
    private Integer influencia;

    @Column(name = "conocimiento_cofrade", nullable = false)
    private Integer conocimientoCofrade;

    @Column(nullable = false)
    private Integer protocolo;

    @Column(nullable = false)
    private Integer devocion;

    @Column(nullable = false)
    private Integer disciplina;

    @Column(nullable = false)
    private Integer empatia;

    @Column(nullable = false)
    private Integer lealtad;

    @Column(nullable = false)
    private Integer integridad;

    @Column(nullable = false)
    private Integer ambicion;

    @Column(nullable = false)
    private Integer conflictividad;

    @Column(nullable = false)
    private Integer popularidad;

    @Column(nullable = false)
    private Integer reputacion;

    @Column(nullable = false)
    private Boolean activo;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private Instant fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private Instant fechaActualizacion;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public PersonajeEntity getPersonaje() {
        return personaje;
    }

    public void setPersonaje(PersonajeEntity personaje) {
        this.personaje = personaje;
    }

    public ArquetipoPerfilEntity getArquetipoOrigen() {
        return arquetipoOrigen;
    }

    public void setArquetipoOrigen(ArquetipoPerfilEntity arquetipoOrigen) {
        this.arquetipoOrigen = arquetipoOrigen;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Long getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(Long experiencia) {
        this.experiencia = experiencia;
    }

    public Integer getPuntosDesarrollo() {
        return puntosDesarrollo;
    }

    public void setPuntosDesarrollo(Integer puntosDesarrollo) {
        this.puntosDesarrollo = puntosDesarrollo;
    }

    public Integer getLiderazgo() {
        return liderazgo;
    }

    public void setLiderazgo(Integer liderazgo) {
        this.liderazgo = liderazgo;
    }

    public Integer getCarisma() {
        return carisma;
    }

    public void setCarisma(Integer carisma) {
        this.carisma = carisma;
    }

    public Integer getDiplomacia() {
        return diplomacia;
    }

    public void setDiplomacia(Integer diplomacia) {
        this.diplomacia = diplomacia;
    }

    public Integer getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(Integer organizacion) {
        this.organizacion = organizacion;
    }

    public Integer getComunicacion() {
        return comunicacion;
    }

    public void setComunicacion(Integer comunicacion) {
        this.comunicacion = comunicacion;
    }

    public Integer getInfluencia() {
        return influencia;
    }

    public void setInfluencia(Integer influencia) {
        this.influencia = influencia;
    }

    public Integer getConocimientoCofrade() {
        return conocimientoCofrade;
    }

    public void setConocimientoCofrade(Integer conocimientoCofrade) {
        this.conocimientoCofrade = conocimientoCofrade;
    }

    public Integer getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(Integer protocolo) {
        this.protocolo = protocolo;
    }

    public Integer getDevocion() {
        return devocion;
    }

    public void setDevocion(Integer devocion) {
        this.devocion = devocion;
    }

    public Integer getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Integer disciplina) {
        this.disciplina = disciplina;
    }

    public Integer getEmpatia() {
        return empatia;
    }

    public void setEmpatia(Integer empatia) {
        this.empatia = empatia;
    }

    public Integer getLealtad() {
        return lealtad;
    }

    public void setLealtad(Integer lealtad) {
        this.lealtad = lealtad;
    }

    public Integer getIntegridad() {
        return integridad;
    }

    public void setIntegridad(Integer integridad) {
        this.integridad = integridad;
    }

    public Integer getAmbicion() {
        return ambicion;
    }

    public void setAmbicion(Integer ambicion) {
        this.ambicion = ambicion;
    }

    public Integer getConflictividad() {
        return conflictividad;
    }

    public void setConflictividad(Integer conflictividad) {
        this.conflictividad = conflictividad;
    }

    public Integer getPopularidad() {
        return popularidad;
    }

    public void setPopularidad(Integer popularidad) {
        this.popularidad = popularidad;
    }

    public Integer getReputacion() {
        return reputacion;
    }

    public void setReputacion(Integer reputacion) {
        this.reputacion = reputacion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Instant getFechaCreacion() {
        return fechaCreacion;
    }

    public Instant getFechaActualizacion() {
        return fechaActualizacion;
    }

    @PrePersist
    void prePersist() {
        if (id == null) {
            id = UUID.randomUUID();
        }
        if (nivel == null) {
            nivel = 1;
        }
        if (experiencia == null) {
            experiencia = 0L;
        }
        if (puntosDesarrollo == null) {
            puntosDesarrollo = 0;
        }
        if (activo == null) {
            activo = true;
        }
        Instant now = Instant.now();
        fechaCreacion = now;
        fechaActualizacion = now;
    }

    @PreUpdate
    void preUpdate() {
        fechaActualizacion = Instant.now();
    }
}
