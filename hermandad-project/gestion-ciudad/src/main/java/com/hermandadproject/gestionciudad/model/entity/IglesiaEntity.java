package com.hermandadproject.gestionciudad.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "iglesias")
public class IglesiaEntity {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "char(36)")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ciudad_id", nullable = false)
    private CiudadEntity ciudad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nodo_ciudad_id", nullable = false)
    private NodoCiudadEntity nodoCiudad;

    @Column(nullable = false, unique = true, length = 100)
    private String codigo;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(length = 500)
    private String descripcion;

    @Column(nullable = false)
    private Integer capacidad;

    @Column(nullable = false)
    private Integer prestigio;

    @Column(name = "disponible_como_sede", nullable = false)
    private Boolean disponibleComoSede;

    @Column(nullable = false)
    private Boolean construible;

    @Column(name = "coste_construccion", precision = 12, scale = 2)
    private BigDecimal costeConstruccion;

    @Column(name = "meses_construccion")
    private Integer mesesConstruccion;

    @Column(nullable = false)
    private Boolean activa;

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

    public CiudadEntity getCiudad() {
        return ciudad;
    }

    public void setCiudad(CiudadEntity ciudad) {
        this.ciudad = ciudad;
    }

    public NodoCiudadEntity getNodoCiudad() {
        return nodoCiudad;
    }

    public void setNodoCiudad(NodoCiudadEntity nodoCiudad) {
        this.nodoCiudad = nodoCiudad;
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

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public Integer getPrestigio() {
        return prestigio;
    }

    public void setPrestigio(Integer prestigio) {
        this.prestigio = prestigio;
    }

    public Boolean getDisponibleComoSede() {
        return disponibleComoSede;
    }

    public void setDisponibleComoSede(Boolean disponibleComoSede) {
        this.disponibleComoSede = disponibleComoSede;
    }

    public Boolean getConstruible() {
        return construible;
    }

    public void setConstruible(Boolean construible) {
        this.construible = construible;
    }

    public BigDecimal getCosteConstruccion() {
        return costeConstruccion;
    }

    public void setCosteConstruccion(BigDecimal costeConstruccion) {
        this.costeConstruccion = costeConstruccion;
    }

    public Integer getMesesConstruccion() {
        return mesesConstruccion;
    }

    public void setMesesConstruccion(Integer mesesConstruccion) {
        this.mesesConstruccion = mesesConstruccion;
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
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
        if (activa == null) {
            activa = true;
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
