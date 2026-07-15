import { apiClient } from '../../../api/apiClient';
import { mappedService } from '../../../api/mappedService';
import { solicitudRestauracionData } from './solicitudRestauracionData';

const EMAIL_PATTERN = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

export const validateResetEmail = (email) => {
  const normalizedEmail = typeof email === 'string' ? email.trim() : '';
  if (!normalizedEmail) return solicitudRestauracionData.messages.requiredEmail;
  if (normalizedEmail.length > 150 || !EMAIL_PATTERN.test(normalizedEmail)) {
    return solicitudRestauracionData.messages.invalidEmail;
  }
  return '';
};

/** Solicita el correo sin intentar comprobar la existencia de la cuenta. */
export const solicitarRestauracionContrasena = async (correoElectronico) => {
  try {
    const { data } = await apiClient.post(
      mappedService.usuarios.user.solicitarRestauracionContrasena,
      { correoElectronico: correoElectronico.trim() },
    );
    return {
      success: true,
      message: data?.message || solicitudRestauracionData.messages.success,
    };
  } catch (error) {
    const isConnectionError = !error?.response;
    return {
      success: false,
      message: isConnectionError
        ? solicitudRestauracionData.messages.connectionError
        : solicitudRestauracionData.messages.genericError,
    };
  }
};
