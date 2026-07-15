export const PASSWORD_MIN_LENGTH = 8;
export const PASSWORD_MAX_LENGTH = 72;

export const passwordRequirements = [
  'Al menos 8 caracteres.',
  'Una letra mayúscula.',
  'Un número.',
  'Un carácter especial.',
];

/**
 * Replica la política configurada en gestion-usuarios para validar antes de enviar.
 * La validación definitiva continúa realizándose en el backend.
 */
export const validatePassword = (password) => {
  if (!password) return 'Introduce una nueva contraseña.';
  if (password.length < PASSWORD_MIN_LENGTH) {
    return 'La contraseña debe tener al menos 8 caracteres.';
  }
  if (password.length > PASSWORD_MAX_LENGTH) {
    return 'La contraseña no puede superar los 72 caracteres.';
  }
  if (!/[A-Z]/.test(password) || !/\d/.test(password) || !/[^\p{L}\p{N}]/u.test(password)) {
    return 'La contraseña no cumple los requisitos de seguridad.';
  }
  return '';
};
