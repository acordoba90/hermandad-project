import { apiClient } from '../../../api/apiClient';
import { accountActivationData } from './accountActivationData';

const ACCOUNT_ACTIVATION_ENDPOINT = '/api/users/activate';

/**
 * Comprueba que el token recibido sea una cadena no vacía.
 *
 * @param {unknown} token Token obtenido de la URL.
 * @returns {boolean} `true` cuando el token puede enviarse al backend.
 */
export const isValidActivationToken = (token) =>
  typeof token === 'string' && token.trim().length > 0;

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

  if (status === 410 || backendMessage.includes('expir')) {
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
    await apiClient.patch(ACCOUNT_ACTIVATION_ENDPOINT, null, {
      params: { token: token.trim() },
    });

    return {
      success: true,
      message: accountActivationData.messages.success,
      severity: 'success',
    };
  } catch (error) {
    return {
      success: false,
      message: getActivationErrorMessage(error),
      severity: 'error',
    };
  }
};
