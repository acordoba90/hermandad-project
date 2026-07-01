package com.hermandadproject.gestionrecorridos.model.entity;

import com.hermandadproject.gestionrecorridos.model.enums.EstadoRecorridoEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "recorridos")
public class RecorridoEntity {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "char(36)")
    private UUID id;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "hermandad_id", nullable = false, columnDefinition = "char(36)")
    private UUID idHermandad;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "ciudad_id", nullable = false, columnDefinition = "char(36)")
    private UUID idCiudad;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "mapa_ciudad_id", nullable = false, columnDefinition = "char(36)")
    private UUID idMapaCiudad;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "iglesia_sede_id", nullable = false, columnDefinition = "char(36)")
    private UUID idIglesiaSede;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "nodo_inicio_id", nullable = false, columnDefinition = "char(36)")
    private UUID idNodoInicio;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "nodo_fin_id", nullable = false, columnDefinition = "char(36)")
    private UUID idNodoFin;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(length = 500)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private EstadoRecorridoEnum estado;

    @Column(name = "distancia_total_metros", nullable = false)
    private Integer distanciaTotalMetros;

    @Column(name = "minutos_estimados", nullable = false)
    private Integer minutosEstimados;

    @Column(name = "dificultad_total", nullable = false)
    private Integer dificultadTotal;

    @Column(name = "pasa_carrera_oficial", nullable = false)
    private Boolean pasaCarreraOficial;

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

    public UUID getIdHermandad() {
        return idHermandad;
    }

    public void setIdHermandad(UUID idHermandad) {
        this.idHermandad = idHermandad;
    }

    public UUID getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(UUID idCiudad) {
        this.idCiudad = idCiudad;
    }

    public UUID getIdMapaCiudad() {
        return idMapaCiudad;
    }

    public void setIdMapaCiudad(UUID idMapaCiudad) {
        this.idMapaCiudad = idMapaCiudad;
    }

    public UUID getIdIglesiaSede() {
        return idIglesiaSede;
    }

    public void setIdIglesiaSede(UUID idIglesiaSede) {
        this.idIglesiaSede = idIglesiaSede;
    }

    public UUID getIdNodoInicio() {
        return idNodoInicio;
    }

    public void setIdNodoInicio(UUID idNodoInicio) {
        this.idNodoInicio = idNodoInicio;
    }

    public UUID getIdNodoFin() {
        return idNodoFin;
    }

    public void setIdNodoFin(UUID idNodoFin) {
        this.idNodoFin = idNodoFin;
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

    public EstadoRecorridoEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoRecorridoEnum estado) {
        this.estado = estado;
    }

    public Integer getDistanciaTotalMetros() {
        return distanciaTotalMetros;
    }

    public void setDistanciaTotalMetros(Integer distanciaTotalMetros) {
        this.distanciaTotalMetros = distanciaTotalMetros;
    }

    public Integer getMinutosEstimados() {
        return minutosEstimados;
    }

    public void setMinutosEstimados(Integer minutosEstimados) {
        this.minutosEstimados = minutosEstimados;
    }

    public Integer getDificultadTotal() {
        return dificultadTotal;
    }

    public void setDificultadTotal(Integer dificultadTotal) {
        this.dificultadTotal = dificultadTotal;
    }

    public Boolean getPasaCarreraOficial() {
        return pasaCarreraOficial;
    }

    public void setPasaCarreraOficial(Boolean pasaCarreraOficial) {
        this.pasaCarreraOficial = pasaCarreraOficial;
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
        if (estado == null) {
            estado = EstadoRecorridoEnum.BORRADOR;
        }
        if (distanciaTotalMetros == null) {
            distanciaTotalMetros = 0;
        }
        if (minutosEstimados == null) {
            minutosEstimados = 0;
        }
        if (dificultadTotal == null) {
            dificultadTotal = 0;
        }
        if (pasaCarreraOficial == null) {
            pasaCarreraOficial = false;
        }
        if (activo == null) {
            activo = true;
        }
        Instant ahora = Instant.now();
        fechaCreacion = ahora;
        fechaActualizacion = ahora;
    }

    @PreUpdate
    void preUpdate() {
        fechaActualizacion = Instant.now();
    }
}
