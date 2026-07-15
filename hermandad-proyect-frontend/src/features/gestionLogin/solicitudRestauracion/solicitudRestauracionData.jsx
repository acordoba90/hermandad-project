export const solicitudRestauracionData = {
  title: 'Restaurar contraseña',
  description:
    'Introduce el correo electrónico asociado a tu cuenta y te enviaremos un enlace para crear una nueva contraseña.',
  emailLabel: 'Correo electrónico',
  buttons: {
    submit: 'Enviar enlace',
    submitting: 'Enviando...',
    cancel: 'Cancelar',
    close: 'Cerrar',
  },
  messages: {
    requiredEmail: 'Introduce tu correo electrónico.',
    invalidEmail: 'Introduce un correo electrónico válido.',
    success:
      'Si el correo pertenece a una cuenta válida, recibirás un enlace para restaurar tu contraseña.',
    connectionError: 'No se ha podido conectar con el servidor.',
    genericError: 'No se ha podido solicitar la restauración. Inténtalo de nuevo.',
  },
};
