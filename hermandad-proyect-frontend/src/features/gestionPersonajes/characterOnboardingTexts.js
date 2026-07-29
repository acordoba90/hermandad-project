export const characterOnboardingTexts = {
  brand: {
    name: 'Hermandad Project',
    claim: 'El juego de gestión de cofradías',
  },
  initial: {
    eyebrow: 'La primera decisión...',
    title: 'Elige a tu Hermano Mayor',
    subtitle: 'Esta será tu primera gran decisión al frente de la hermandad.',
    description: 'Selecciona uno de los perfiles disponibles o crea un Hermano Mayor completamente personalizado.',
    footer: 'El Hermano Mayor será tu representación dentro de la hermandad y condicionará parte de tu experiencia de juego.',
    options: {
      select: {
        title: 'Elegir personaje',
        description: 'Selecciona uno de los Hermanos Mayores disponibles, cada uno con su propia historia, personalidad y estilo de liderazgo.',
        action: 'Ver personajes',
      },
      create: {
        title: 'Crear mi personaje',
        description: 'Personaliza la identidad, apariencia, trayectoria y forma de liderar de tu propio Hermano Mayor.',
        action: 'Crear personaje',
      },
    },
  },
  selection: {
    title: 'Elige a tu Hermano Mayor',
    subtitle: 'Cada candidato posee una historia, una personalidad y una forma diferente de entender la vida de hermandad.',
    summaryTitle: 'Tu candidato',
    summaryEmpty: 'Selecciona un candidato para revisar aquí su perfil antes de confirmar.',
    confirm: 'Confirmar Hermano Mayor',
    ready: 'Hermano Mayor seleccionado correctamente.',
    loadError: 'No se han podido cargar los Hermanos Mayores. Comprueba la conexión e inténtalo de nuevo.',
  },
  creation: {
    title: 'Crea a tu Hermano Mayor',
    subtitle: 'Define su identidad, su trayectoria y la forma en la que liderará la hermandad.',
    steps: ['Identidad', 'Apariencia', 'Trayectoria', 'Estilo de liderazgo', 'Revisión'],
    ready: 'Los datos del personaje están listos para enviarse a gestion-personajes.',
    leadershipLoadError: 'No se han podido cargar los estilos de liderazgo. Inténtalo de nuevo.',
  },
  common: {
    back: 'Volver',
    next: 'Continuar',
    previous: 'Anterior',
    select: 'Seleccionar',
    selected: 'Seleccionado',
    edit: 'Volver a editar',
    create: 'Crear Hermano Mayor',
  },
};
