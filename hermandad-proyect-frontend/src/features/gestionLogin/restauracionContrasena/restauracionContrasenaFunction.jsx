import { apiClient } from '../../../api/apiClient';
import { mappedService } from '../../../api/mappedService';
import { restauracionContrasenaData } from './restauracionContrasenaData';

export const isValidResetToken = (token) =>
  typeof token === 'string' && token.trim().length > 0;

const normalizeBackendMessage = (error) =>
  [error?.response?.data?.message, error?.response?.data?.detail]
    .find((message) => typeof message === 'string')
    ?.toLocaleLowerCase('es') || '';

/** Traduce únicamente errores funcionales conocidos; no expone detalles técnicos. */
export const getPasswordResetErrorMessage = (error) => {
  const message = normalizeBackendMessage(error);
  if (!error?.response) return restauracionContrasenaData.messages.connectionError;
  if (message.includes('expir')) return restauracionContrasenaData.messages.expiredToken;
  if (message.includes('ya no') || message.includes('utiliz')) {
    return restauracionContrasenaData.messages.usedToken;
  }
  if (message.includes('no se puede restaurar')) {
    return restauracionContrasenaData.messages.forbiddenAccount;
  }
  if (message.includes('no coinciden')) return restauracionContrasenaData.messages.mismatch;
  if (message.includes('requisitos de seguridad')) {
    return restauracionContrasenaData.messages.invalidPassword;
  }
  if ([400, 404, 410, 422].includes(error?.response?.status)) {
    return restauracionContrasenaData.messages.invalidToken;
  }
  return restauracionContrasenaData.messages.genericError;
};

export const restablecerContrasena = async ({
  token,
  nuevaContrasena,
  confirmacionContrasena,
}) => {
  try {
    await apiClient.post(
      mappedService.usuarios.user.confirmarRestauracionContrasena,
      { token, nuevaContrasena, confirmacionContrasena },
    );
    return { success: true, message: restauracionContrasenaData.messages.success };
  } catch (error) {
    return { success: false, message: getPasswordResetErrorMessage(error) };
  }
};
