import { apiClient } from '../../../api/apiClient';
import { mappedService } from '../../../api/mappedService';
import { accountActivationData } from './accountActivationData';

/**
 * Comprueba que el token recibido sea una cadena no vacía.
 *
 * @param {unknown} token Token obtenido de la URL.
 * @returns {boolean} `true` cuando el token puede enviarse al backend.
 */
export const isValidActivationToken = (token) =>
  typeof token === 'string' && token.trim().length > 0;

const EXPIRED_TOKEN_BACKEND_MESSAGE = 'El token de activacion ha expirado';

/**
 * Identifica la respuesta de token expirado mediante el estado y el campo
 * estructurado `message` que devuelve actualmente el backend.
 *
 * @param {unknown} error Error producido por el endpoint de activación.
 * @returns {boolean} `true` exclusivamente para un token expirado.
 */
export const isExpiredActivationTokenError = (error) =>
  error?.response?.status === 400 &&
  error?.response?.data?.message === EXPIRED_TOKEN_BACKEND_MESSAGE;

/**
 * Traduce un error HTTP a uno de los mensajes seguros de la interfaz.
 * No devuelve detalles técnicos procedentes del backend.
 *
 * @param {unknown} error Error producido por el cliente HTTP.
 * @returns {string} Mensaje comprensible para el usuario.
 */
export const getActivationErrorMessage = (error) => {
  const status = error?.response?.status;
  const backendMessage = [
    error?.response?.data?.message,
    error?.response?.data?.detail,
  ]
    .filter((message) => typeof message === 'string')
    .join(' ')
    .toLocaleLowerCase('es');

  if (isExpiredActivationTokenError(error) || status === 410) {
    return accountActivationData.messages.expiredToken;
  }

  if (status === 409 || backendMessage.includes('ya activ')) {
    return accountActivationData.messages.alreadyActive;
  }

  if ([400, 404, 422].includes(status)) {
    return accountActivationData.messages.invalidLink;
  }

  return accountActivationData.messages.genericError;
};

/**
 * Activa una cuenta enviando el token como parámetro de consulta al endpoint.
 *
 * @param {string} token Token de activación recibido por correo.
 * @returns {Promise<{success: boolean, message: string, severity: string}>}
 * Resultado normalizado para que el componente no interprete errores HTTP.
 */
export const activateAccount = async (token) => {
  if (!isValidActivationToken(token)) {
    return {
      success: false,
      message: accountActivationData.messages.invalidLink,
      severity: 'error',
    };
  }

  try {
    const request = {
      token: token
    }
    await apiClient.post(mappedService.usuarios.userEstado.activarCuenta, request);

    return {
      success: true,
      message: accountActivationData.messages.success,
      severity: 'success',
    };
  } catch (error) {
    return {
      success: false,
      expired: isExpiredActivationTokenError(error),
      message: getActivationErrorMessage(error),
      severity: 'error',
    };
  }
};

/**
 * Solicita al servicio de correo un nuevo enlace usando el token expirado para
 * identificar la cuenta, sin pedir datos adicionales al usuario.
 *
 * @param {string} token Token de activación expirado.
 * @returns {Promise<{success: boolean, message: string, severity: string}>}
 * Resultado normalizado para la pantalla de activación.
 */
export const resendActivationLink = async (token) => {
  if (!isValidActivationToken(token)) {
    return {
      success: false,
      message: accountActivationData.messages.invalidLink,
      severity: 'error',
    };
  }

  try {
    await apiClient.post(
      mappedService.usuarios.email.reenviarEnlaceActivacion,
      { token },
    );

    return {
      success: true,
      message: accountActivationData.messages.resendSuccess,
      severity: 'success',
    };
  } catch {
    return {
      success: false,
      message: accountActivationData.messages.resendError,
      severity: 'error',
    };
  }
};
