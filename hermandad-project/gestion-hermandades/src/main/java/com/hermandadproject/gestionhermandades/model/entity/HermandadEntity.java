package com.hermandadproject.gestionhermandades.model.entity;

import com.hermandadproject.gestionhermandades.model.enums.EstadoHermandad;
import jakarta.persistence.Column;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "hermandades")
public class HermandadEntity {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "char(36)")
    private UUID id;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "usuario_id", nullable = false, columnDefinition = "char(36)")
    private UUID idUsuario;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String ciudad;

    @Column(name = "anio_fundacion")
    private Integer anioFundacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 30)
    private EstadoHermandad estado;

    @Column(name = "prestigio", nullable = false)
    private Integer prestigio;

    @Column(name = "popularidad", nullable = false)
    private Integer popularidad;

    @Column(name = "devocion", nullable = false)
    private Integer devocion;

    @Column(name = "solemnidad", nullable = false)
    private Integer solemnidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uuid_tipo_hermandad", nullable = false)
    private TipoHermandadEntity tipoHermandad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uuid_carisma_principal")
    private CarismaHermandadEntity carismaPrincipal;

    @ManyToMany
    @JoinTable(
            name = "hermandad_carisma_secundario",
            joinColumns = @JoinColumn(name = "uuid_hermandad", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "uuid_carisma", referencedColumnName = "uuid")
    )
    private Set<CarismaHermandadEntity> carismasSecundarios = new HashSet<>();

    @OneToOne(mappedBy = "hermandad", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private EconomiaHermandadEntity economia;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal dinero;

    @Column(nullable = false)
    private Integer prestigioGlobal;

    @Column(nullable = false)
    private Integer devocionGlobal;

    @Column(name = "satisfaccion_interna", nullable = false)
    private Integer satisfaccionInterna;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(UUID idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Integer getAnioFundacion() {
        return anioFundacion;
    }

    public void setAnioFundacion(Integer anioFundacion) {
        this.anioFundacion = anioFundacion;
    }

    public EstadoHermandad getEstado() {
        return estado;
    }

    public void setEstado(EstadoHermandad estado) {
        this.estado = estado;
    }

    public Integer getPrestigio() {
        return prestigio;
    }

    public void setPrestigio(Integer prestigio) {
        this.prestigio = prestigio;
    }

    public Integer getPopularidad() {
        return popularidad;
    }

    public void setPopularidad(Integer popularidad) {
        this.popularidad = popularidad;
    }

    public Integer getDevocion() {
        return devocion;
    }

    public void setDevocion(Integer devocion) {
        this.devocion = devocion;
    }

    public Integer getSolemnidad() {
        return solemnidad;
    }

    public void setSolemnidad(Integer solemnidad) {
        this.solemnidad = solemnidad;
    }

    public TipoHermandadEntity getTipoHermandad() {
        return tipoHermandad;
    }

    public void setTipoHermandad(TipoHermandadEntity tipoHermandad) {
        this.tipoHermandad = tipoHermandad;
    }

    public CarismaHermandadEntity getCarismaPrincipal() {
        return carismaPrincipal;
    }

    public void setCarismaPrincipal(CarismaHermandadEntity carismaPrincipal) {
        this.carismaPrincipal = carismaPrincipal;
    }

    public Set<CarismaHermandadEntity> getCarismasSecundarios() {
        return carismasSecundarios;
    }

    public void setCarismasSecundarios(Set<CarismaHermandadEntity> carismasSecundarios) {
        this.carismasSecundarios = carismasSecundarios;
    }

    public EconomiaHermandadEntity getEconomia() {
        return economia;
    }

    public void setEconomia(EconomiaHermandadEntity economia) {
        this.economia = economia;
    }

    public BigDecimal getDinero() {
        return dinero;
    }

    public void setDinero(BigDecimal dinero) {
        this.dinero = dinero;
    }

    public Integer getPrestigioGlobal() {
        return prestigioGlobal;
    }

    public void setPrestigioGlobal(Integer prestigioGlobal) {
        this.prestigioGlobal = prestigioGlobal;
    }

    public Integer getDevocionGlobal() {
        return devocionGlobal;
    }

    public void setDevocionGlobal(Integer devocionGlobal) {
        this.devocionGlobal = devocionGlobal;
    }

    public Integer getSatisfaccionInterna() {
        return satisfaccionInterna;
    }

    public void setSatisfaccionInterna(Integer satisfaccionInterna) {
        this.satisfaccionInterna = satisfaccionInterna;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    @PrePersist
    void prePersist() {
        if (id == null) {
            id = UUID.randomUUID();
        }
        if (estado == null) {
            estado = EstadoHermandad.EN_FORMACION;
        }
        if (prestigio == null) {
            prestigio = 0;
        }
        if (popularidad == null) {
            popularidad = 0;
        }
        if (devocion == null) {
            devocion = 0;
        }
        if (solemnidad == null) {
            solemnidad = 0;
        }
        LocalDateTime now = LocalDateTime.now();
        fechaCreacion = now;
        fechaActualizacion = now;
    }

    @PreUpdate
    void preUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
}
