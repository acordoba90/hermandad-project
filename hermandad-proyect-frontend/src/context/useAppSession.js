import { useContext } from 'react';
import { AppSessionContext } from './appSessionContext';

/**
 * Obtiene la sesión funcional compartida y exige que exista su proveedor.
 *
 * @returns {object} estado y operaciones de la sesión.
 * @throws {Error} si se utiliza fuera de AppSessionProvider.
 */
export const useAppSession = () => {
  const context = useContext(AppSessionContext);
  if (!context) {
    throw new Error('useAppSession debe utilizarse dentro de AppSessionProvider.');
  }
  return context;
};
