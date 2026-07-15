package com.hermandadproject.gestionusuarios.repository;

import com.hermandadproject.gestionusuarios.model.entity.UsuarioEstadoEntity;
import com.hermandadproject.gestionusuarios.model.enums.AccountStatusEnum;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio de acceso a datos para el estado de las cuentas de usuario.
 *
 * Expone consultas derivadas de Spring Data JPA para localizar estados por
 * token de activacion, usuario asociado y criterios de inactividad.
 */
public interface UsuarioEstadoRepository extends JpaRepository<UsuarioEstadoEntity, UUID> {

    /**
     * Busca el estado de usuario asociado a un token de activacion.
     *
     * @param activationToken token de activacion generado para la cuenta.
     * @return estado asociado al token, o vacio si no existe.
     */
    Optional<UsuarioEstadoEntity> findByActivationToken(String activationToken);

    /**
     * Busca el estado asociado a un token de restauracion de contrasena.
     *
     * La consulta bloquea el registro para impedir que dos confirmaciones
     * concurrentes consuman el mismo token.
     *
     * @param tokenRestauracionContrasena token de restauracion recibido.
     * @return estado asociado al token, o vacio si no existe.
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<UsuarioEstadoEntity> findByTokenRestauracionContrasena(String tokenRestauracionContrasena);

    /**
     * Busca el estado asociado al identificador de un usuario.
     *
     * @param usuarioId identificador del usuario propietario del estado.
     * @return estado asociado al usuario, o vacio si no existe.
     */
    Optional<UsuarioEstadoEntity> findByUsuarioId(UUID usuarioId);

    /**
     * Obtiene estados con un estado de cuenta concreto y ultimo login anterior
     * a la fecha limite indicada.
     *
     * @param accountStatus estado de cuenta por el que se filtra.
     * @param fechaLimite fecha maxima de ultimo login admitida.
     * @return lista de estados que cumplen los criterios.
     */
    List<UsuarioEstadoEntity> findByAccountStatusAndLastLoginBefore(
            AccountStatusEnum accountStatus,
            Instant fechaLimite
    );

    /**
     * Comprueba si existe un estado asociado a un usuario.
     *
     * @param usuarioId identificador del usuario.
     * @return true si el usuario ya tiene estado asociado; false en caso contrario.
     */
    boolean existsByUsuarioId(UUID usuarioId);
}
