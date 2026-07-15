const UserController = '/api/users';
const UsertEstadoController = '/api/usuarios-estado';
const EmailController = '/api/emails';

/**
 * Mapa centralizado de las rutas REST consumidas por el frontend.
 * Cada grupo representa el recurso expuesto por un servicio backend.
 */
export const mappedService = {
  /** Endpoints del servicio gestion-usuarios. */
  usuarios: {
    user: {
      basePath: UserController,
      crear: UserController,
      validarCredenciales: `${UserController}/validate`,
      solicitarRestauracionContrasena: `${UserController}/password-reset/request`,
      confirmarRestauracionContrasena: `${UserController}/password-reset/confirm`,
    },
    userEstado: {
      basePath: UsertEstadoController,
      activarCuenta: `${UsertEstadoController}/activar-cuenta`,
    },
    email: {
      basePath: EmailController,
      reenviarEnlaceActivacion: `${EmailController}/activacion-token-expirado`,
    }
  },
};
