package com.hermandadproject.gestionhermandades.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "tipo_hermandad")
public class TipoHermandadEntity {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "char(36)")
    private UUID uuid;

    @Column(nullable = false, unique = true, length = 50)
    private String codigo;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(length = 500)
    private String descripcion;

    @Column(nullable = false)
    private Integer nivel;

    @Column(nullable = false)
    private Boolean activo;

    @Column(name = "puede_estacion_penitencia", nullable = false)
    private Boolean puedeEstacionPenitencia;

    @Column(name = "puede_cultos_externos", nullable = false)
    private Boolean puedeCultosExternos;

    @Column(name = "puede_tener_sede_canonica", nullable = false)
    private Boolean puedeTenerSedeCanonica;

    @Column(name = "puede_tener_paso", nullable = false)
    private Boolean puedeTenerPaso;

    @Column(name = "prestigio_base", nullable = false)
    private Integer prestigioBase;

    @Column(nullable = false)
    private Integer orden;

    @OneToOne(mappedBy = "tipoHermandad", fetch = FetchType.LAZY)
    private TipoHermandadCaracteristicasEntity caracteristicas;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Boolean getPuedeEstacionPenitencia() {
        return puedeEstacionPenitencia;
    }

    public void setPuedeEstacionPenitencia(Boolean puedeEstacionPenitencia) {
        this.puedeEstacionPenitencia = puedeEstacionPenitencia;
    }

    public Boolean getPuedeCultosExternos() {
        return puedeCultosExternos;
    }

    public void setPuedeCultosExternos(Boolean puedeCultosExternos) {
        this.puedeCultosExternos = puedeCultosExternos;
    }

    public Boolean getPuedeTenerSedeCanonica() {
        return puedeTenerSedeCanonica;
    }

    public void setPuedeTenerSedeCanonica(Boolean puedeTenerSedeCanonica) {
        this.puedeTenerSedeCanonica = puedeTenerSedeCanonica;
    }

    public Boolean getPuedeTenerPaso() {
        return puedeTenerPaso;
    }

    public void setPuedeTenerPaso(Boolean puedeTenerPaso) {
        this.puedeTenerPaso = puedeTenerPaso;
    }

    public Integer getPrestigioBase() {
        return prestigioBase;
    }

    public void setPrestigioBase(Integer prestigioBase) {
        this.prestigioBase = prestigioBase;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public TipoHermandadCaracteristicasEntity getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(TipoHermandadCaracteristicasEntity caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    @PrePersist
    void prePersist() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
        if (activo == null) {
            activo = true;
        }
        if (puedeEstacionPenitencia == null) {
            puedeEstacionPenitencia = false;
        }
        if (puedeCultosExternos == null) {
            puedeCultosExternos = false;
        }
        if (puedeTenerSedeCanonica == null) {
            puedeTenerSedeCanonica = false;
        }
        if (puedeTenerPaso == null) {
            puedeTenerPaso = false;
        }
    }
}
