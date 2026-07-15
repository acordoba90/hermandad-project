export const restauracionContrasenaData = {
  title: 'Crear nueva contraseña',
  description: 'Introduce y confirma la nueva contraseña que utilizarás para acceder a tu cuenta.',
  fields: {
    password: 'Nueva contraseña',
    confirmation: 'Confirmar nueva contraseña',
  },
  buttons: {
    submit: 'Actualizar contraseña',
    submitting: 'Actualizando contraseña...',
    backToLogin: 'Volver al inicio de sesión',
  },
  messages: {
    missingToken: 'El enlace de restauración no es válido o está incompleto.',
    invalidToken: 'El enlace de restauración no es válido.',
    expiredToken: 'El enlace de restauración ha expirado.',
    usedToken: 'Este enlace ya no puede utilizarse.',
    forbiddenAccount: 'No se puede restaurar la contraseña de esta cuenta.',
    invalidPassword: 'La contraseña no cumple los requisitos de seguridad.',
    requiredConfirmation: 'Confirma la nueva contraseña.',
    mismatch: 'Las contraseñas introducidas no coinciden.',
    connectionError: 'No se ha podido conectar con el servidor.',
    genericError: 'Ha ocurrido un error al actualizar la contraseña.',
    success: 'Tu contraseña se ha actualizado correctamente. Ya puedes iniciar sesión.',
  },
  routes: { login: '/', current: '/restaurar-contrasena' },
  successRedirectDelay: 1800,
};
