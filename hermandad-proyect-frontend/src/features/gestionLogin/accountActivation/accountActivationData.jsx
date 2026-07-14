/**
 * Textos y constantes de navegación de la pantalla de activación de cuenta.
 */
export const accountActivationData = {
  title: 'Activación de cuenta',
  description:
    'Para activar tu cuenta y comenzar a disfrutar de Hermandad Project, pulsa el botón inferior.',
  personalLink: 'Este enlace es personal y solo puede utilizarse una vez.',
  buttons: {
    activate: 'Activar cuenta',
    activating: 'Activando cuenta...',
    resend: 'Enviar un nuevo enlace de activación',
    resending: 'Enviando nuevo enlace...',
    accept: 'Aceptar',
    backToLogin: 'Volver al inicio de sesión',
  },
  messages: {
    success: 'Tu cuenta se ha activado correctamente.',
    invalidLink: 'El enlace de activación no es válido.',
    expiredToken: 'El enlace de activación ha caducado.',
    alreadyActive: 'La cuenta ya se encuentra activada.',
    genericError: 'No se ha podido activar la cuenta. Inténtalo de nuevo.',
    resendError:
      'No se ha podido enviar un nuevo enlace. Inténtalo de nuevo.',
    resendSuccess:
      'Se ha enviado un nuevo enlace de activación. Revise su bandeja de correo electrónico.',
    manualClose:
      'El nuevo enlace ha sido enviado. Ya puede cerrar esta pestaña.',
  },
  modal: {
    title: 'Nuevo enlace enviado',
  },
  accessibility: {
    activationIcon: 'Activación segura de la cuenta',
    loading: 'Activación de la cuenta en curso',
    resendLoading: 'Envío del nuevo enlace de activación en curso',
  },
  routes: {
    login: '/',
    success: '/partida',
  },
  redirectDelay: 1200,
};
