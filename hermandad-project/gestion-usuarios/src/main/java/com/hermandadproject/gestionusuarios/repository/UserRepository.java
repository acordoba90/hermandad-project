package com.hermandadproject.gestionusuarios.repository;

import com.hermandadproject.gestionusuarios.model.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UsuarioEntity, UUID> {
    boolean existsByNombreUsuario(String nombreUsuario);

    boolean existsByCorreoElectronico(String correoElectronico);

    Optional<UsuarioEntity> findByCorreoElectronico(String correoElectronico);
}
