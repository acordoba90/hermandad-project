/** Devuelve los errores de los campos obligatorios de identidad. */
export const validateIdentity = (form) => {
  const errors = {};
  const age = Number(form.edad);

  if (form.nombre.trim().length < 2) errors.nombre = 'Introduce al menos 2 caracteres.';
  if (form.apellidos.trim().length < 2) errors.apellidos = 'Introduce al menos 2 caracteres.';
  if (!Number.isInteger(age) || age < 18 || age > 90) errors.edad = 'La edad debe estar entre 18 y 90 años.';
  if (form.profesion.trim().length < 2) errors.profesion = 'Introduce al menos 2 caracteres.';

  return errors;
};

/** Valida el estado completo antes de preparar el personaje personalizado. */
export const validateCustomCharacter = (form) => ({
  ...validateIdentity(form),
  ...(!form.avatarId ? { avatarId: 'Selecciona una apariencia.' } : {}),
  ...(!form.tipoPersonaje ? { tipoPersonaje: 'Selecciona un tipo de liderazgo.' } : {}),
});

/**
 * Construye en memoria el futuro contrato de creación sin inventar identificadores
 * ni realizar ninguna operación de persistencia.
 */
export const buildCustomCharacter = (form) => ({
  id: null,
  usuarioId: null,
  avatarId: form.avatarId,
  nombre: form.nombre.trim(),
  apellidos: form.apellidos.trim(),
  edad: Number(form.edad),
  profesion: form.profesion.trim(),
  biografia: form.biografia.trim(),
  motivacion: form.motivacion.trim(),
  tipoPersonaje: form.tipoPersonaje,
  rasgos: [...form.rasgos],
  personalizado: true,
  fechaCreacion: null,
});
