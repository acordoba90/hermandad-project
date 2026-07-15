package com.hermandadproject.gestionusuarios.model.entity;

import com.hermandadproject.gestionusuarios.model.enums.AccountStatusEnum;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "usuarios_estado")
public class UsuarioEstadoEntity {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "char(36)")
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true, columnDefinition = "char(36)")
    private UsuarioEntity usuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status", nullable = false, length = 20)
    private AccountStatusEnum accountStatus;

    @Column(name = "email_verified", nullable = false)
    private Boolean emailVerified;

    @Column(name = "activation_token", length = 100)
    private String activationToken;

    @Column(name = "activation_token_expiration")
    private Instant activationTokenExpiration;

    @Column(name = "token_restauracion_contrasena", length = 100)
    private String tokenRestauracionContrasena;

    @Column(name = "expiracion_token_restauracion_contrasena")
    private Instant expiracionTokenRestauracionContrasena;

    @Column(name = "last_login")
    private Instant lastLogin;

    @Column(name = "last_activity")
    private Instant lastActivity;

    @Column(name = "failed_login_attempts", nullable = false)
    private Integer failedLoginAttempts;

    @Column(name = "locked_until")
    private Instant lockedUntil;

    @Column(name = "password_changed_at")
    private Instant passwordChangedAt;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private Instant fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private Instant fechaActualizacion;

    @PrePersist
    void prePersist() {
        if (id == null) {
            id = UUID.randomUUID();
        }

        if (accountStatus == null) {
            accountStatus = AccountStatusEnum.PENDING;
        }

        if (emailVerified == null) {
            emailVerified = false;
        }

        if (failedLoginAttempts == null) {
            failedLoginAttempts = 0;
        }

        Instant now = Instant.now();
        fechaCreacion = now;
        fechaActualizacion = now;
    }

    @PreUpdate
    void preUpdate() {
        fechaActualizacion = Instant.now();
    }

    // getters y setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public AccountStatusEnum getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatusEnum accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getActivationToken() {
        return activationToken;
    }

    public void setActivationToken(String activationToken) {
        this.activationToken = activationToken;
    }

    public Instant getActivationTokenExpiration() {
        return activationTokenExpiration;
    }

    public void setActivationTokenExpiration(Instant activationTokenExpiration) {
        this.activationTokenExpiration = activationTokenExpiration;
    }

    public String getTokenRestauracionContrasena() {
        return tokenRestauracionContrasena;
    }

    public void setTokenRestauracionContrasena(String tokenRestauracionContrasena) {
        this.tokenRestauracionContrasena = tokenRestauracionContrasena;
    }

    public Instant getExpiracionTokenRestauracionContrasena() {
        return expiracionTokenRestauracionContrasena;
    }

    public void setExpiracionTokenRestauracionContrasena(Instant expiracionTokenRestauracionContrasena) {
        this.expiracionTokenRestauracionContrasena = expiracionTokenRestauracionContrasena;
    }

    public Instant getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Instant lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Instant getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(Instant lastActivity) {
        this.lastActivity = lastActivity;
    }

    public Integer getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(Integer failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    public Instant getLockedUntil() {
        return lockedUntil;
    }

    public void setLockedUntil(Instant lockedUntil) {
        this.lockedUntil = lockedUntil;
    }

    public Instant getPasswordChangedAt() {
        return passwordChangedAt;
    }

    public void setPasswordChangedAt(Instant passwordChangedAt) {
        this.passwordChangedAt = passwordChangedAt;
    }

    public Instant getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Instant fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Instant getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Instant fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
}
