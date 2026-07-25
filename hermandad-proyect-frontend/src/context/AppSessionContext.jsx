import { useCallback, useMemo, useReducer, useRef } from 'react';
import {
  obtenerArquetiposActivos,
  obtenerPersonajesPredefinidosJuntaGobierno,
} from '../services/personajeService';
import { AppSessionContext } from './appSessionContext';
import {
  APP_SESSION_ACTIONS,
  appSessionInitialState,
  appSessionReducer,
} from './appSessionReducer';

/**
 * Mantiene la sesión funcional compartida entre las pantallas de la aplicación.
 * Las credenciales y el estado exclusivo de formularios permanecen fuera de este proveedor.
 *
 * @param {{ children: import('react').ReactNode }} props contenido de la aplicación.
 */
export const AppSessionProvider = ({ children }) => {
  const [state, dispatch] = useReducer(appSessionReducer, appSessionInitialState);
  const cargasEnCurso = useRef({
    personajesPredefinidos: null,
    arquetipos: null,
  });

  const iniciarSesion = useCallback((usuario) => {
    dispatch({ type: APP_SESSION_ACTIONS.INICIAR_SESION, payload: usuario });
  }, []);

  const cerrarSesion = useCallback(() => {
    cargasEnCurso.current = { personajesPredefinidos: null, arquetipos: null };
    dispatch({ type: APP_SESSION_ACTIONS.CERRAR_SESION });
  }, []);

  const establecerPersonajeActivo = useCallback((personaje) => {
    dispatch({ type: APP_SESSION_ACTIONS.ESTABLECER_PERSONAJE_ACTIVO, payload: personaje });
  }, []);

  const establecerPartidaActiva = useCallback((partida) => {
    dispatch({ type: APP_SESSION_ACTIONS.ESTABLECER_PARTIDA_ACTIVA, payload: partida });
  }, []);

  const establecerHermandadActiva = useCallback((hermandad) => {
    dispatch({ type: APP_SESSION_ACTIONS.ESTABLECER_HERMANDAD_ACTIVA, payload: hermandad });
  }, []);

  const establecerPartidas = useCallback((partidas) => {
    dispatch({ type: APP_SESSION_ACTIONS.ESTABLECER_PARTIDAS, payload: partidas });
  }, []);

  const cargarPersonajesPredefinidos = useCallback(async ({ forzar = false } = {}) => {
    if (!forzar && state.catalogos.personajesPredefinidos !== null) {
      return state.catalogos.personajesPredefinidos;
    }
    if (cargasEnCurso.current.personajesPredefinidos) {
      return cargasEnCurso.current.personajesPredefinidos;
    }

    const carga = obtenerPersonajesPredefinidosJuntaGobierno()
      .then((personajes) => {
        dispatch({
          type: APP_SESSION_ACTIONS.ESTABLECER_PERSONAJES_PREDEFINIDOS,
          payload: personajes,
        });
        return personajes;
      })
      .finally(() => {
        cargasEnCurso.current.personajesPredefinidos = null;
      });

    cargasEnCurso.current.personajesPredefinidos = carga;
    return carga;
  }, [state.catalogos.personajesPredefinidos]);

  const cargarArquetipos = useCallback(async ({ forzar = false } = {}) => {
    if (!forzar && state.catalogos.arquetipos !== null) {
      return state.catalogos.arquetipos;
    }
    if (cargasEnCurso.current.arquetipos) {
      return cargasEnCurso.current.arquetipos;
    }

    const carga = obtenerArquetiposActivos()
      .then((arquetipos) => {
        dispatch({ type: APP_SESSION_ACTIONS.ESTABLECER_ARQUETIPOS, payload: arquetipos });
        return arquetipos;
      })
      .finally(() => {
        cargasEnCurso.current.arquetipos = null;
      });

    cargasEnCurso.current.arquetipos = carga;
    return carga;
  }, [state.catalogos.arquetipos]);

  const invalidarPersonajesPredefinidos = useCallback(() => {
    dispatch({ type: APP_SESSION_ACTIONS.INVALIDAR_PERSONAJES_PREDEFINIDOS });
  }, []);

  const invalidarArquetipos = useCallback(() => {
    dispatch({ type: APP_SESSION_ACTIONS.INVALIDAR_ARQUETIPOS });
  }, []);

  const value = useMemo(() => ({
    ...state,
    iniciarSesion,
    cerrarSesion,
    establecerPersonajeActivo,
    establecerPartidaActiva,
    establecerHermandadActiva,
    establecerPartidas,
    cargarPersonajesPredefinidos,
    cargarArquetipos,
    invalidarPersonajesPredefinidos,
    invalidarArquetipos,
  }), [
    state,
    iniciarSesion,
    cerrarSesion,
    establecerPersonajeActivo,
    establecerPartidaActiva,
    establecerHermandadActiva,
    establecerPartidas,
    cargarPersonajesPredefinidos,
    cargarArquetipos,
    invalidarPersonajesPredefinidos,
    invalidarArquetipos,
  ]);

  return <AppSessionContext.Provider value={value}>{children}</AppSessionContext.Provider>;
};
