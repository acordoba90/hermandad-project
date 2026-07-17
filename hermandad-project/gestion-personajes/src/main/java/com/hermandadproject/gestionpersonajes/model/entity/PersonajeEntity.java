package com.hermandadproject.gestionpersonajes.model.entity;

import com.hermandadproject.gestionpersonajes.model.enums.GenderEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

@Entity
@Table(name = "personajes")
public class PersonajeEntity {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "char(36)")
    private UUID id;

    @Column(nullable = false, unique = true, length = 100)
    private String codigo;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "id_usuario", columnDefinition = "char(36)")
    private UUID usuarioId;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "id_avatar", columnDefinition = "char(36)")
    private UUID avatarId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_colectivo", nullable = false, columnDefinition = "char(36)")
    private ColectivoEntity colectivo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "rol_personaje_id", nullable = false, columnDefinition = "char(36)")
    private RolPersonajeEntity rolPersonaje;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 150)
    private String apellidos;

    @Column
    private Integer edad;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private GenderEnum genero;

    @Column(length = 150)
    private String origen;

    @Column(length = 150)
    private String profesion;

    @Column(length = 500)
    private String descripcion;

    @Column(columnDefinition = "TEXT")
    private String biografia;

    @Column(columnDefinition = "TEXT")
    private String motivacion;

    @Column(name = "tipo_personaje", length = 100)
    private String tipoPersonaje;

    @Column
    private Boolean personalizado;

    @Column(name = "url_avatar", length = 255)
    private String urlAvatar;

    @Column(nullable = false)
    private Boolean activo;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private Instant fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private Instant fechaActualizacion;

    @OneToOne(mappedBy = "personaje", fetch = FetchType.LAZY)
    private PerfilPersonajeEntity perfil;

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

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UUID usuarioId) {
        this.usuarioId = usuarioId;
    }

    public UUID getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(UUID avatarId) {
        this.avatarId = avatarId;
    }

    public ColectivoEntity getColectivo() {
        return colectivo;
    }

    public void setColectivo(ColectivoEntity colectivo) {
        this.colectivo = colectivo;
    }

    public RolPersonajeEntity getRolPersonaje() {
        return rolPersonaje;
    }

    public void setRolPersonaje(RolPersonajeEntity rolPersonaje) {
        this.rolPersonaje = rolPersonaje;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public GenderEnum getGenero() {
        return genero;
    }

    public void setGenero(GenderEnum genero) {
        this.genero = genero;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getMotivacion() {
        return motivacion;
    }

    public void setMotivacion(String motivacion) {
        this.motivacion = motivacion;
    }

    public String getTipoPersonaje() {
        return tipoPersonaje;
    }

    public void setTipoPersonaje(String tipoPersonaje) {
        this.tipoPersonaje = tipoPersonaje;
    }

    public Boolean getPersonalizado() {
        return personalizado;
    }

    public void setPersonalizado(Boolean personalizado) {
        this.personalizado = personalizado;
    }

    public String getUrlAvatar() {
        return urlAvatar;
    }

    public void setUrlAvatar(String urlAvatar) {
        this.urlAvatar = urlAvatar;
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

    public PerfilPersonajeEntity getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilPersonajeEntity perfil) {
        this.perfil = perfil;
    }

    @PrePersist
    void prePersist() {
        if (id == null) {
            id = UUID.randomUUID();
        }
        if (activo == null) {
            activo = true;
        }
        if (personalizado == null) {
            personalizado = false;
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
