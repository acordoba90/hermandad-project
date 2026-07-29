export const ONBOARDING_VIEWS = {
  INITIAL: 'inicio',
  SELECTION: 'seleccion',
  CREATION: 'creacion',
};

export const PREDEFINED_AVATAR_TONES = ['wine', 'gold', 'purple', 'silver', 'green', 'blue'];

export const CHARACTER_CARD_FACES = Object.freeze({
  PRESENTATION: 'presentation',
  STORY: 'story',
  PROFILE: 'profile',
});

export const PROFILE_SKILL_GROUPS = Object.freeze([
  {
    title: 'Gestión',
    skills: [
      ['liderazgo', 'Liderazgo'],
      ['carisma', 'Carisma'],
      ['organizacion', 'Organización'],
      ['diplomacia', 'Diplomacia'],
      ['comunicacion', 'Comunicación'],
      ['influencia', 'Influencia'],
      ['conocimientoCofrade', 'Conocimiento cofrade'],
      ['protocolo', 'Protocolo'],
    ],
  },
  {
    title: 'Valores',
    skills: [
      ['devocion', 'Devoción'],
      ['disciplina', 'Disciplina'],
      ['empatia', 'Empatía'],
      ['lealtad', 'Lealtad'],
      ['integridad', 'Integridad'],
    ],
  },
  {
    title: 'Reputación',
    skills: [
      ['ambicion', 'Ambición'],
      ['conflictividad', 'Conflictividad'],
      ['popularidad', 'Popularidad'],
      ['reputacion', 'Reputación'],
    ],
  },
]);

export const CREATION_STEPS = {
  IDENTITY: 0,
  APPEARANCE: 1,
  BACKGROUND: 2,
  PERSONALITY: 3,
  REVIEW: 4,
};

export const INITIAL_CHARACTER_FORM = {
  avatarId: '',
  nombre: '',
  apellidos: '',
  edad: '',
  profesion: '',
  biografia: '',
  motivacion: '',
  arquetipoPerfilId: '',
};

export const CHARACTER_FIELD_LIMITS = {
  nombre: 100,
  apellidos: 150,
  profesion: 150,
  biografia: 600,
  motivacion: 400,
};
