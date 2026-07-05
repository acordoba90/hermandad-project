import { apiClient } from '../../../api/apiClient';

const LOGIN_ENDPOINT = '/api/users/validate';

export const validarCredenciales = async (credentials) => {
  try {
    const request = {
      correoElectronico: credentials.email,
      password: credentials.password,
    };

    const { data } = await apiClient.post(LOGIN_ENDPOINT, request);

    return {
      open: true,
      success: true,
      message: 'Login realizado correctamente',
      severity: 'success',
      user: data,
    };
  } catch (error) {
    return {
      open: true,
      success: false,
      message:
        error.response?.data?.message ||
        error.response?.data?.detail ||
        'Correo electrónico o contraseña incorrectos',
      severity: 'error',
    };
  }
};
