package com.hermandadproject.gestionhermandades.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "tipo_hermandad_caracteristicas")
public class TipoHermandadCaracteristicasEntity {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "char(36)")
    private UUID uuid;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_hermandad_uuid", nullable = false, unique = true)
    private TipoHermandadEntity tipoHermandad;

    @Column(name = "resumen_jugable", length = 500)
    private String resumenJugable;

    @Column(name = "coste_mantenimiento_base", nullable = false, precision = 12, scale = 2)
    private BigDecimal costeMantenimientoBase;

    @Column(name = "ingresos_base", nullable = false, precision = 12, scale = 2)
    private BigDecimal ingresosBase;

    @Column(name = "dificultad_base", nullable = false)
    private Integer dificultadBase;

    @Column(name = "devocion_base", nullable = false)
    private Integer devocionBase;

    @Column(name = "influencia_eclesiastica_base", nullable = false)
    private Integer influenciaEclesiasticaBase;

    @Column(name = "influencia_social_base", nullable = false)
    private Integer influenciaSocialBase;

    @Column(name = "capacidad_crecimiento", nullable = false)
    private Integer capacidadCrecimiento;

    @Column(name = "permite_carrera_oficial", nullable = false)
    private Boolean permiteCarreraOficial;

    @Column(name = "permite_patrimonio_avanzado", nullable = false)
    private Boolean permitePatrimonioAvanzado;

    @Column(name = "permite_banda_musica", nullable = false)
    private Boolean permiteBandaMusica;

    @Column(name = "permite_cuerpo_nazarenos", nullable = false)
    private Boolean permiteCuerpoNazarenos;

    @Column(name = "permite_cuadrilla_costaleros", nullable = false)
    private Boolean permiteCuadrillaCostaleros;

    @Column(name = "tipo_previo_requerido", length = 50)
    private String tipoPrevioRequerido;

    @Column(name = "requisitos_evolucion", length = 1000)
    private String requisitosEvolucion;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private Instant fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private Instant fechaActualizacion;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public TipoHermandadEntity getTipoHermandad() {
        return tipoHermandad;
    }

    public void setTipoHermandad(TipoHermandadEntity tipoHermandad) {
        this.tipoHermandad = tipoHermandad;
    }

    public String getResumenJugable() {
        return resumenJugable;
    }

    public void setResumenJugable(String resumenJugable) {
        this.resumenJugable = resumenJugable;
    }

    public BigDecimal getCosteMantenimientoBase() {
        return costeMantenimientoBase;
    }

    public void setCosteMantenimientoBase(BigDecimal costeMantenimientoBase) {
        this.costeMantenimientoBase = costeMantenimientoBase;
    }

    public BigDecimal getIngresosBase() {
        return ingresosBase;
    }

    public void setIngresosBase(BigDecimal ingresosBase) {
        this.ingresosBase = ingresosBase;
    }

    public Integer getDificultadBase() {
        return dificultadBase;
    }

    public void setDificultadBase(Integer dificultadBase) {
        this.dificultadBase = dificultadBase;
    }

    public Integer getDevocionBase() {
        return devocionBase;
    }

    public void setDevocionBase(Integer devocionBase) {
        this.devocionBase = devocionBase;
    }

    public Integer getInfluenciaEclesiasticaBase() {
        return influenciaEclesiasticaBase;
    }

    public void setInfluenciaEclesiasticaBase(Integer influenciaEclesiasticaBase) {
        this.influenciaEclesiasticaBase = influenciaEclesiasticaBase;
    }

    public Integer getInfluenciaSocialBase() {
        return influenciaSocialBase;
    }

    public void setInfluenciaSocialBase(Integer influenciaSocialBase) {
        this.influenciaSocialBase = influenciaSocialBase;
    }

    public Integer getCapacidadCrecimiento() {
        return capacidadCrecimiento;
    }

    public void setCapacidadCrecimiento(Integer capacidadCrecimiento) {
        this.capacidadCrecimiento = capacidadCrecimiento;
    }

    public Boolean getPermiteCarreraOficial() {
        return permiteCarreraOficial;
    }

    public void setPermiteCarreraOficial(Boolean permiteCarreraOficial) {
        this.permiteCarreraOficial = permiteCarreraOficial;
    }

    public Boolean getPermitePatrimonioAvanzado() {
        return permitePatrimonioAvanzado;
    }

    public void setPermitePatrimonioAvanzado(Boolean permitePatrimonioAvanzado) {
        this.permitePatrimonioAvanzado = permitePatrimonioAvanzado;
    }

    public Boolean getPermiteBandaMusica() {
        return permiteBandaMusica;
    }

    public void setPermiteBandaMusica(Boolean permiteBandaMusica) {
        this.permiteBandaMusica = permiteBandaMusica;
    }

    public Boolean getPermiteCuerpoNazarenos() {
        return permiteCuerpoNazarenos;
    }

    public void setPermiteCuerpoNazarenos(Boolean permiteCuerpoNazarenos) {
        this.permiteCuerpoNazarenos = permiteCuerpoNazarenos;
    }

    public Boolean getPermiteCuadrillaCostaleros() {
        return permiteCuadrillaCostaleros;
    }

    public void setPermiteCuadrillaCostaleros(Boolean permiteCuadrillaCostaleros) {
        this.permiteCuadrillaCostaleros = permiteCuadrillaCostaleros;
    }

    public String getTipoPrevioRequerido() {
        return tipoPrevioRequerido;
    }

    public void setTipoPrevioRequerido(String tipoPrevioRequerido) {
        this.tipoPrevioRequerido = tipoPrevioRequerido;
    }

    public String getRequisitosEvolucion() {
        return requisitosEvolucion;
    }

    public void setRequisitosEvolucion(String requisitosEvolucion) {
        this.requisitosEvolucion = requisitosEvolucion;
    }

    public Instant getFechaCreacion() {
        return fechaCreacion;
    }

    public Instant getFechaActualizacion() {
        return fechaActualizacion;
    }

    @PrePersist
    void prePersist() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
        if (costeMantenimientoBase == null) {
            costeMantenimientoBase = BigDecimal.ZERO;
        }
        if (ingresosBase == null) {
            ingresosBase = BigDecimal.ZERO;
        }
        if (dificultadBase == null) {
            dificultadBase = 1;
        }
        if (devocionBase == null) {
            devocionBase = 0;
        }
        if (influenciaEclesiasticaBase == null) {
            influenciaEclesiasticaBase = 0;
        }
        if (influenciaSocialBase == null) {
            influenciaSocialBase = 0;
        }
        if (capacidadCrecimiento == null) {
            capacidadCrecimiento = 0;
        }
        if (permiteCarreraOficial == null) {
            permiteCarreraOficial = false;
        }
        if (permitePatrimonioAvanzado == null) {
            permitePatrimonioAvanzado = false;
        }
        if (permiteBandaMusica == null) {
            permiteBandaMusica = false;
        }
        if (permiteCuerpoNazarenos == null) {
            permiteCuerpoNazarenos = false;
        }
        if (permiteCuadrillaCostaleros == null) {
            permiteCuadrillaCostaleros = false;
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
