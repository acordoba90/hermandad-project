package com.hermandadproject.gestionrecompensas.model.entity;

import com.hermandadproject.gestionrecompensas.model.enums.RewardItemTypeEnum;
import com.hermandadproject.gestionrecompensas.model.enums.RewardRarityEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "recompensas_sobre")
public class RecompensaSobreEntity {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "char(36)")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apertura_id", nullable = false, columnDefinition = "char(36)")
    private AperturaSobreRecompensaEntity apertura;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_elemento", nullable = false, length = 50)
    private RewardItemTypeEnum tipoElemento;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "id_elemento", columnDefinition = "char(36)")
    private UUID idElemento;

    @Column(name = "codigo_elemento", nullable = false, length = 100)
    private String codigoElemento;

    @Column(name = "nombre_elemento", nullable = false, length = 150)
    private String nombreElemento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private RewardRarityEnum rareza;

    @Column(nullable = false)
    private Integer cantidad;

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

    public AperturaSobreRecompensaEntity getApertura() {
        return apertura;
    }

    public void setApertura(AperturaSobreRecompensaEntity apertura) {
        this.apertura = apertura;
    }

    public RewardItemTypeEnum getTipoElemento() {
        return tipoElemento;
    }

    public void setTipoElemento(RewardItemTypeEnum tipoElemento) {
        this.tipoElemento = tipoElemento;
    }

    public UUID getIdElemento() {
        return idElemento;
    }

    public void setIdElemento(UUID idElemento) {
        this.idElemento = idElemento;
    }

    public String getCodigoElemento() {
        return codigoElemento;
    }

    public void setCodigoElemento(String codigoElemento) {
        this.codigoElemento = codigoElemento;
    }

    public String getNombreElemento() {
        return nombreElemento;
    }

    public void setNombreElemento(String nombreElemento) {
        this.nombreElemento = nombreElemento;
    }

    public RewardRarityEnum getRareza() {
        return rareza;
    }

    public void setRareza(RewardRarityEnum rareza) {
        this.rareza = rareza;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
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
        Instant now = Instant.now();
        fechaCreacion = now;
        fechaActualizacion = now;
    }

    @PreUpdate
    void preUpdate() {
        fechaActualizacion = Instant.now();
    }
}
