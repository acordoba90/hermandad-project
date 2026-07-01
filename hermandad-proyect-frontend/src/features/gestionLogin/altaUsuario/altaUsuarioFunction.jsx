import { apiClient } from '../../../api/apiClient';

export const crearUsuario = async (usuario) => {
  try {
    await apiClient.post('/api/users', usuario);

    return {
      open: true,
      success: true,
      message: 'El usuario ha sido dado de alta de forma correcta',
      severity: 'success',
    };
  } catch (error) {
    if (error.response?.status === 409) {
      return {
        open: true,
        success: false,
        message:
          error.response?.data?.message ||
          error.response?.data?.detail ||
          'El usuario ya existe',
        severity: 'warning',
      };
    }

    return {
      open: true,
      success: false,
      message: 'Se ha producido un error al dar de alta el usuario',
      severity: 'error',
    };
  }
};


export const handleAltaUsuarioSubmit = async (event) => {
  event.preventDefault();

  const formData = new FormData(event.currentTarget);

  const usuario = {
    nombreUsuario: formData.get('nombreUsuario'),
    correoElectronico: formData.get('email'),
    password: formData.get('password'),
  };

  const confirmPassword = formData.get('confirmPassword');

  if (confirmPassword !== usuario.password) {
    return {
      open: true,
      success: false,
      message: 'Las contraseñas introducidas no coinciden',
      severity: 'error',
    };
  } else {
    return await crearUsuario(usuario);
  };
};
