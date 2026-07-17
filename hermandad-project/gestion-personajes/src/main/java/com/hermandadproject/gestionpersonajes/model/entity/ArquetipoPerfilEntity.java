package com.hermandadproject.gestionpersonajes.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

/**
 * Plantilla de atributos base usada para inicializar perfiles jugables de personajes.
 */
@Entity
@Table(name = "arquetipos_perfil")
public class ArquetipoPerfilEntity {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "char(36)")
    private UUID id;

    @Column(nullable = false, unique = true, length = 100)
    private String codigo;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "liderazgo_base", nullable = false)
    private Integer liderazgoBase;

    @Column(name = "carisma_base", nullable = false)
    private Integer carismaBase;

    @Column(name = "diplomacia_base", nullable = false)
    private Integer diplomaciaBase;

    @Column(name = "organizacion_base", nullable = false)
    private Integer organizacionBase;

    @Column(name = "comunicacion_base", nullable = false)
    private Integer comunicacionBase;

    @Column(name = "influencia_base", nullable = false)
    private Integer influenciaBase;

    @Column(name = "conocimiento_cofrade_base", nullable = false)
    private Integer conocimientoCofradeBase;

    @Column(name = "protocolo_base", nullable = false)
    private Integer protocoloBase;

    @Column(name = "devocion_base", nullable = false)
    private Integer devocionBase;

    @Column(name = "disciplina_base", nullable = false)
    private Integer disciplinaBase;

    @Column(name = "empatia_base", nullable = false)
    private Integer empatiaBase;

    @Column(name = "lealtad_base", nullable = false)
    private Integer lealtadBase;

    @Column(name = "integridad_base", nullable = false)
    private Integer integridadBase;

    @Column(name = "ambicion_base", nullable = false)
    private Integer ambicionBase;

    @Column(name = "conflictividad_base", nullable = false)
    private Integer conflictividadBase;

    @Column(name = "popularidad_base", nullable = false)
    private Integer popularidadBase;

    @Column(name = "reputacion_base", nullable = false)
    private Integer reputacionBase;

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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getLiderazgoBase() {
        return liderazgoBase;
    }

    public void setLiderazgoBase(Integer liderazgoBase) {
        this.liderazgoBase = liderazgoBase;
    }

    public Integer getCarismaBase() {
        return carismaBase;
    }

    public void setCarismaBase(Integer carismaBase) {
        this.carismaBase = carismaBase;
    }

    public Integer getDiplomaciaBase() {
        return diplomaciaBase;
    }

    public void setDiplomaciaBase(Integer diplomaciaBase) {
        this.diplomaciaBase = diplomaciaBase;
    }

    public Integer getOrganizacionBase() {
        return organizacionBase;
    }

    public void setOrganizacionBase(Integer organizacionBase) {
        this.organizacionBase = organizacionBase;
    }

    public Integer getComunicacionBase() {
        return comunicacionBase;
    }

    public void setComunicacionBase(Integer comunicacionBase) {
        this.comunicacionBase = comunicacionBase;
    }

    public Integer getInfluenciaBase() {
        return influenciaBase;
    }

    public void setInfluenciaBase(Integer influenciaBase) {
        this.influenciaBase = influenciaBase;
    }

    public Integer getConocimientoCofradeBase() {
        return conocimientoCofradeBase;
    }

    public void setConocimientoCofradeBase(Integer conocimientoCofradeBase) {
        this.conocimientoCofradeBase = conocimientoCofradeBase;
    }

    public Integer getProtocoloBase() {
        return protocoloBase;
    }

    public void setProtocoloBase(Integer protocoloBase) {
        this.protocoloBase = protocoloBase;
    }

    public Integer getDevocionBase() {
        return devocionBase;
    }

    public void setDevocionBase(Integer devocionBase) {
        this.devocionBase = devocionBase;
    }

    public Integer getDisciplinaBase() {
        return disciplinaBase;
    }

    public void setDisciplinaBase(Integer disciplinaBase) {
        this.disciplinaBase = disciplinaBase;
    }

    public Integer getEmpatiaBase() {
        return empatiaBase;
    }

    public void setEmpatiaBase(Integer empatiaBase) {
        this.empatiaBase = empatiaBase;
    }

    public Integer getLealtadBase() {
        return lealtadBase;
    }

    public void setLealtadBase(Integer lealtadBase) {
        this.lealtadBase = lealtadBase;
    }

    public Integer getIntegridadBase() {
        return integridadBase;
    }

    public void setIntegridadBase(Integer integridadBase) {
        this.integridadBase = integridadBase;
    }

    public Integer getAmbicionBase() {
        return ambicionBase;
    }

    public void setAmbicionBase(Integer ambicionBase) {
        this.ambicionBase = ambicionBase;
    }

    public Integer getConflictividadBase() {
        return conflictividadBase;
    }

    public void setConflictividadBase(Integer conflictividadBase) {
        this.conflictividadBase = conflictividadBase;
    }

    public Integer getPopularidadBase() {
        return popularidadBase;
    }

    public void setPopularidadBase(Integer popularidadBase) {
        this.popularidadBase = popularidadBase;
    }

    public Integer getReputacionBase() {
        return reputacionBase;
    }

    public void setReputacionBase(Integer reputacionBase) {
        this.reputacionBase = reputacionBase;
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
