import { apiClient } from '../../api/apiClient';
import { mappedService } from '../../api/mappedService';

const validPreset = (character) =>
  character?.activo === true &&
  character?.personalizado === false &&
  character?.colectivoCode === 'JUNTA_GOBIERNO' &&
  character?.rolPersonajeCodigo === 'HERMANO_MAYOR';

export const loadCharacterOnboardingCatalogs = async () => {
  const [archetypesResult, charactersResult] = await Promise.allSettled([
    apiClient.get(mappedService.gestionPersonajes.arquetipos.activos),
    apiClient.get(mappedService.gestionPersonajes.personajes.preestablecidosJuntaGobierno),
  ]);

  return {
    archetypes: archetypesResult.status === 'fulfilled'
      ? archetypesResult.value.data.filter((item) => item.activo === true)
      : [],
    characters: charactersResult.status === 'fulfilled'
      ? charactersResult.value.data.filter(validPreset)
      : [],
    archetypesError: archetypesResult.status === 'rejected',
    charactersError: charactersResult.status === 'rejected',
  };
};

export const createMainCharacter = async (form) => {
  const payload = {
    nombre: form.nombre.trim(),
    apellidos: form.apellidos.trim(),
    edad: Number(form.edad),
    genero: form.genero,
    profesion: form.profesion.trim() || null,
    biografia: form.biografia.trim() || null,
    motivacion: form.motivacion.trim() || null,
    arquetipoPerfilId: form.arquetipoPerfilId,
    personalizado: true,
  };
  const { data } = await apiClient.post(
    mappedService.gestionPersonajes.personajes.crearHermanoMayor,
    payload,
  );
  return data;
};

export const getRequestErrorMessage = (error, fallback) =>
  error.response?.data?.message || error.response?.data?.detail || fallback;
