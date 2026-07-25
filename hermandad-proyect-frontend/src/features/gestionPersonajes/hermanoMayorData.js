export const hermanoMayorAvatars = [
  { id: 'cirio-granate', initials: 'HM', label: 'Cirio granate', tone: 'wine' },
  { id: 'medalla-dorada', initials: 'MG', label: 'Medalla dorada', tone: 'gold' },
  { id: 'noche-morada', initials: 'NM', label: 'Noche morada', tone: 'purple' },
  { id: 'plata-antigua', initials: 'PA', label: 'Plata antigua', tone: 'silver' },
  { id: 'terciopelo-verde', initials: 'TV', label: 'Terciopelo verde', tone: 'green' },
  { id: 'azul-capilla', initials: 'AC', label: 'Azul capilla', tone: 'blue' },
];

export const hermanoMayorTypes = [
  { id: 'Tradicional', icon: 'history', description: 'Defiende las costumbres, la historia y las formas heredadas.' },
  { id: 'Organizador', icon: 'order', description: 'Prioriza el orden, la planificación y la eficiencia.' },
  { id: 'Conciliador', icon: 'agreement', description: 'Busca acuerdos y cuida la convivencia entre los hermanos.' },
  { id: 'Renovador', icon: 'renew', description: 'Impulsa cambios y nuevas formas de gestionar la hermandad.' },
  { id: 'Espiritual', icon: 'faith', description: 'Da prioridad a la fe, los cultos y la vida cristiana.' },
  { id: 'Gestor', icon: 'management', description: 'Centra sus decisiones en la estabilidad económica y organizativa.' },
  { id: 'Cofrade de barrio', icon: 'neighborhood', description: 'Refuerza el vínculo con la feligresía y el entorno social.' },
  { id: 'Joven prometedor', icon: 'future', description: 'Representa una nueva generación con ideas y energía.' },
];

export const hermanoMayorTraits = [
  'Prudente', 'Carismático', 'Cercano', 'Ambicioso', 'Culto',
  'Humilde', 'Innovador', 'Devoto', 'Organizado', 'Dialogante',
];

export const hermanoMayorCandidates = [
  {
    id: 'eduardo-martin-de-la-vega', avatarId: 'medalla-dorada', nombre: 'Eduardo Martín', apellidos: 'de la Vega',
    edad: 62, profesion: 'Abogado', tipoPersonaje: 'Tradicional', personalizado: false,
    biografia: 'Cofrade veterano, formado durante décadas dentro de la hermandad. Conoce profundamente sus reglas, su historia y sus costumbres.',
    motivacion: 'Conservar el legado recibido y garantizar que la hermandad mantenga su identidad.',
    rasgos: ['Prudente', 'Culto', 'Tradicional'],
  },
  {
    id: 'carmen-romero-valdes', avatarId: 'cirio-granate', nombre: 'Carmen', apellidos: 'Romero Valdés',
    edad: 55, profesion: 'Empresaria', tipoPersonaje: 'Renovadora', personalizado: false,
    biografia: 'Hermana comprometida, con experiencia en gestión y capacidad para modernizar estructuras sin perder las raíces.',
    motivacion: 'Abrir la hermandad a nuevas generaciones y mejorar su organización.',
    rasgos: ['Innovadora', 'Organizada', 'Ambiciosa'],
  },
  {
    id: 'manuel-ruiz-marquez', avatarId: 'terciopelo-verde', nombre: 'Manuel', apellidos: 'Ruiz Márquez',
    edad: 48, profesion: 'Profesor', tipoPersonaje: 'Conciliador', personalizado: false,
    biografia: 'Hermano cercano y respetado, acostumbrado a escuchar y alcanzar acuerdos entre distintas sensibilidades.',
    motivacion: 'Fortalecer la unión entre los hermanos y crear un proyecto común.',
    rasgos: ['Cercano', 'Dialogante', 'Empático'],
  },
  {
    id: 'alvaro-jimenez-pardo', avatarId: 'azul-capilla', nombre: 'Álvaro', apellidos: 'Jiménez Pardo',
    edad: 39, profesion: 'Economista', tipoPersonaje: 'Gestor', personalizado: false,
    biografia: 'Profesional metódico y comprometido, con una visión basada en la planificación y la estabilidad económica.',
    motivacion: 'Sanear las cuentas y construir una hermandad sostenible.',
    rasgos: ['Metódico', 'Responsable', 'Eficiente'],
  },
  {
    id: 'lucia-mendoza-torres', avatarId: 'noche-morada', nombre: 'Lucía', apellidos: 'Mendoza Torres',
    edad: 32, profesion: 'Periodista', tipoPersonaje: 'Joven prometedora', personalizado: false,
    biografia: 'Hermana activa, comunicativa y con una visión moderna sobre la presencia social de la hermandad.',
    motivacion: 'Acercar la hermandad a los jóvenes y reforzar su comunicación.',
    rasgos: ['Carismática', 'Creativa', 'Comunicativa'],
  },
  {
    id: 'francisco-leon-cabrera', avatarId: 'plata-antigua', nombre: 'Francisco', apellidos: 'León Cabrera',
    edad: 66, profesion: 'Jubilado', tipoPersonaje: 'Cofrade de barrio', personalizado: false,
    biografia: 'Hermano muy querido, profundamente ligado a la feligresía y a la vida cotidiana de la hermandad.',
    motivacion: 'Mantener viva la relación entre la hermandad, su barrio y sus vecinos.',
    rasgos: ['Humilde', 'Cercano', 'Devoto'],
  },
];
