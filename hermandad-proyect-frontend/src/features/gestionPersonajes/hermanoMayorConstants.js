export const ONBOARDING_VIEWS = {
  INITIAL: 'inicio',
  SELECTION: 'seleccion',
  CREATION: 'creacion',
};

export const CREATION_STEPS = {
  IDENTITY: 0,
  APPEARANCE: 1,
  BACKGROUND: 2,
  PERSONALITY: 3,
  REVIEW: 4,
};

export const MAX_TRAITS = 3;

export const INITIAL_CHARACTER_FORM = {
  avatarId: '',
  nombre: '',
  apellidos: '',
  edad: '',
  profesion: '',
  biografia: '',
  motivacion: '',
  tipoPersonaje: '',
  rasgos: [],
};

export const CHARACTER_FIELD_LIMITS = {
  nombre: 100,
  apellidos: 150,
  profesion: 150,
  biografia: 600,
  motivacion: 400,
};
