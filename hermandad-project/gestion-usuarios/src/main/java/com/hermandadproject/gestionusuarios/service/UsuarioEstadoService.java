package com.hermandadproject.gestionusuarios.service;

import com.hermandadproject.gestionusuarios.model.entity.UsuarioEntity;
import com.hermandadproject.gestionusuarios.model.entity.UsuarioEstadoEntity;

import java.util.UUID;

/**
 * Servicio encargado de gestionar el ciclo de vida del estado de las cuentas de usuario.
 *
 * Centraliza operaciones de activacion, bloqueo, actividad e inactividad de cuentas,
 * dejando preparada la integracion futura con un proveedor externo de identidad.
 */
public interface UsuarioEstadoService {

    /**
     * Crea el estado inicial asociado a un usuario recien registrado.
     *
     * El estado inicial deja la cuenta pendiente de activacion, genera un token
     * de activacion temporal y establece los valores iniciales de seguridad.
     *
     * @param usuario usuario para el que se crea el estado inicial.
     * @return estado de usuario creado y persistido.
     * @throws IllegalArgumentException si el usuario es nulo, no tiene identificador
     *                                  o no existe en el repositorio de usuarios.
     * @throws IllegalStateException si ya existe un estado asociado al usuario.
     */
    UsuarioEstadoEntity crearEstadoInicial(UsuarioEntity usuario);

    /**
     * Busca el estado de usuario asociado a un token de activacion.
     *
     * @param token token de activacion recibido.
     * @return estado de usuario asociado al token.
     * @throws IllegalArgumentException si el token es nulo, esta vacio o no existe.
     */
    UsuarioEstadoEntity buscarPorToken(String token);

    /**
     * Busca el estado asociado a un usuario.
     *
     * @param usuarioId identificador del usuario.
     * @return estado de usuario asociado.
     * @throws IllegalArgumentException si el identificador es nulo o no existe estado asociado.
     */
    UsuarioEstadoEntity buscarPorUsuarioId(UUID usuarioId);

    /**
     * Activa una cuenta usando su token de activacion.
     *
     * Si el token es valido y no ha expirado, marca la cuenta como activa,
     * confirma el correo y elimina el token de activacion.
     *
     * @param token token de activacion recibido.
     * @throws IllegalArgumentException si el token es nulo, esta vacio o no existe.
     * @throws IllegalStateException si el token ha expirado o no tiene fecha de expiracion.
     */
    void activarCuenta(String token);

    /**
     * Registra un login correcto y reinicia los intentos fallidos.
     *
     * Si la cuenta esta bloqueada, primero intenta desbloquearla cuando el periodo
     * de bloqueo haya expirado. Si sigue bloqueada, no permite registrar el login.
     *
     * @param usuarioId identificador del usuario.
     * @throws IllegalArgumentException si el identificador es nulo o no existe estado asociado.
     * @throws IllegalStateException si la cuenta sigue bloqueada temporalmente.
     */
    void registrarLogin(UUID usuarioId);

    /**
     * Registra actividad reciente del usuario.
     *
     * Actualiza la fecha de ultima actividad sin modificar el estado de la cuenta.
     *
     * @param usuarioId identificador del usuario.
     * @throws IllegalArgumentException si el identificador es nulo o no existe estado asociado.
     */
    void registrarActividad(UUID usuarioId);

    /**
     * Incrementa el contador de intentos fallidos y bloquea la cuenta si procede.
     *
     * Cuando se alcanza el maximo de intentos permitidos, marca la cuenta como
     * bloqueada y establece la fecha hasta la que permanecera bloqueada.
     *
     * @param usuarioId identificador del usuario.
     * @throws IllegalArgumentException si el identificador es nulo o no existe estado asociado.
     */
    void incrementarIntentoFallido(UUID usuarioId);

    /**
     * Desbloquea la cuenta si el periodo de bloqueo ha expirado.
     *
     * Solo persiste cambios cuando existe una fecha de bloqueo y esta ya ha vencido.
     *
     * @param usuarioId identificador del usuario.
     * @throws IllegalArgumentException si el identificador es nulo o no existe estado asociado.
     */
    void desbloquearSiProcede(UUID usuarioId);

    /**
     * Marca como inactivos los usuarios activos sin login reciente.
     *
     * Busca cuentas activas cuyo ultimo login sea anterior al limite calculado
     * con el numero de dias indicado y las marca como inactivas.
     *
     * @param dias numero de dias de inactividad permitidos.
     * @throws IllegalArgumentException si el numero de dias no es mayor que cero.
     */
    void desactivarUsuariosInactivos(int dias);
}
