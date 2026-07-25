export const APP_SESSION_ACTIONS = Object.freeze({
  INICIAR_SESION: 'INICIAR_SESION',
  CERRAR_SESION: 'CERRAR_SESION',
  ESTABLECER_PERSONAJE_ACTIVO: 'ESTABLECER_PERSONAJE_ACTIVO',
  ESTABLECER_PARTIDA_ACTIVA: 'ESTABLECER_PARTIDA_ACTIVA',
  ESTABLECER_HERMANDAD_ACTIVA: 'ESTABLECER_HERMANDAD_ACTIVA',
  ESTABLECER_PARTIDAS: 'ESTABLECER_PARTIDAS',
  ESTABLECER_PERSONAJES_PREDEFINIDOS: 'ESTABLECER_PERSONAJES_PREDEFINIDOS',
  ESTABLECER_ARQUETIPOS: 'ESTABLECER_ARQUETIPOS',
  INVALIDAR_PERSONAJES_PREDEFINIDOS: 'INVALIDAR_PERSONAJES_PREDEFINIDOS',
  INVALIDAR_ARQUETIPOS: 'INVALIDAR_ARQUETIPOS',
});

export const appSessionInitialState = Object.freeze({
  usuario: null,
  personajeActivo: null,
  partidaActiva: null,
  hermandadActiva: null,
  partidas: null,
  catalogos: {
    personajesPredefinidos: null,
    arquetipos: null,
  },
});

/**
 * Aplica las transiciones de la sesión compartida manteniendo juntas las
 * limpiezas necesarias cuando cambia el usuario autenticado.
 *
 * @param {typeof appSessionInitialState} state estado actual.
 * @param {{ type: string, payload?: unknown }} action transición solicitada.
 * @returns {typeof appSessionInitialState} nuevo estado de sesión.
 */
export const appSessionReducer = (state, action) => {
  switch (action.type) {
    case APP_SESSION_ACTIONS.INICIAR_SESION:
      return {
        ...appSessionInitialState,
        catalogos: { ...appSessionInitialState.catalogos },
        usuario: action.payload,
      };
    case APP_SESSION_ACTIONS.CERRAR_SESION:
      return {
        ...appSessionInitialState,
        catalogos: { ...appSessionInitialState.catalogos },
      };
    case APP_SESSION_ACTIONS.ESTABLECER_PERSONAJE_ACTIVO:
      return { ...state, personajeActivo: action.payload };
    case APP_SESSION_ACTIONS.ESTABLECER_PARTIDA_ACTIVA:
      return { ...state, partidaActiva: action.payload };
    case APP_SESSION_ACTIONS.ESTABLECER_HERMANDAD_ACTIVA:
      return { ...state, hermandadActiva: action.payload };
    case APP_SESSION_ACTIONS.ESTABLECER_PARTIDAS:
      return { ...state, partidas: action.payload };
    case APP_SESSION_ACTIONS.ESTABLECER_PERSONAJES_PREDEFINIDOS:
      return {
        ...state,
        catalogos: { ...state.catalogos, personajesPredefinidos: action.payload },
      };
    case APP_SESSION_ACTIONS.ESTABLECER_ARQUETIPOS:
      return {
        ...state,
        catalogos: { ...state.catalogos, arquetipos: action.payload },
      };
    case APP_SESSION_ACTIONS.INVALIDAR_PERSONAJES_PREDEFINIDOS:
      return {
        ...state,
        catalogos: { ...state.catalogos, personajesPredefinidos: null },
      };
    case APP_SESSION_ACTIONS.INVALIDAR_ARQUETIPOS:
      return {
        ...state,
        catalogos: { ...state.catalogos, arquetipos: null },
      };
    default:
      return state;
  }
};
