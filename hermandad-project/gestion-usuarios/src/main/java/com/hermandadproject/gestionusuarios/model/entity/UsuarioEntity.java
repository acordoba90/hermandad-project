package com.hermandadproject.gestionusuarios.model.entity;

import com.hermandadproject.gestionusuarios.model.enums.UserRoleEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
public class UsuarioEntity {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "char(36)")
    private UUID id;

    @Column(nullable = false, length = 100)
    private String nombreUsuario;

    @Column(nullable = false, unique = true, length = 150)
    private String correoElectronico;

    @Column(name = "hash_contrasena", nullable = false, length = 255)
    private String hashContrasena;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private UserRoleEnum rol;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private Instant fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private Instant fechaActualizacion;

    @Column(name = "fecha_inicio_vigencia")
    private Instant vigenteDesde;

    @Column(name = "fecha_fin_vigencia")
    private Instant vigenteHasta;

    @OneToOne(mappedBy = "usuario", fetch = FetchType.LAZY)
    private PerfilUsuarioEntity perfil;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getHashContrasena() {
        return hashContrasena;
    }

    public void setHashContrasena(String hashContrasena) {
        this.hashContrasena = hashContrasena;
    }

    public UserRoleEnum getRol() {
        return rol;
    }

    public void setRol(UserRoleEnum rol) {
        this.rol = rol;
    }

    public Instant getFechaCreacion() {
        return fechaCreacion;
    }

    public Instant getFechaActualizacion() {
        return fechaActualizacion;
    }

    public Instant getVigenteDesde() {
        return vigenteDesde;
    }

    public void setVigenteDesde(Instant vigenteDesde) {
        this.vigenteDesde = vigenteDesde;
    }

    public Instant getVigenteHasta() {
        return vigenteHasta;
    }

    public void setVigenteHasta(Instant vigenteHasta) {
        this.vigenteHasta = vigenteHasta;
    }

    public PerfilUsuarioEntity getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilUsuarioEntity perfil) {
        this.perfil = perfil;
    }

    @PrePersist
    void prePersist() {
        if (id == null) {
            id = UUID.randomUUID();
        }
        if (rol == null) {
            rol = UserRoleEnum.PLAYER;
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
