import { createTheme } from '@mui/material/styles';

// Tokens visuales internos compartidos por el tema y los estilos de pantalla.
const colors = {
  primary: '#4B1D5C',
  primaryLight: '#6D2F83',
  primaryDark: '#2A1035',
  secondary: '#D4AF37',
  secondaryLight: '#E7C96A',
  secondaryDark: '#9B7A1F',
  background: '#0E0A12',
  paper: '#1A1220',
  text: '#F4EBDD',
  textSecondary: '#B9A8C4',
};

const publicPage = {
  minHeight: '100vh',
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'center',
  background: (theme) =>
    `linear-gradient(135deg, ${theme.palette.background.default} 0%, ${theme.palette.primary.dark} 45%, ${theme.palette.primary.main} 100%)`,
  padding: 2,
};

const responsivePublicPage = { ...publicPage, p: { xs: 2, sm: 3 } };

const publicCard = {
  width: '100%',
  maxWidth: 460,
  borderRadius: 4,
  backgroundColor: 'rgba(26, 18, 32, 0.95)',
  borderColor: 'divider',
  boxShadow: 20,
};

const compactPublicCard = {
  ...publicCard,
  maxWidth: 420,
  border: '1px solid rgba(212, 175, 55, 0.35)',
  boxShadow: '0 20px 60px rgba(0,0,0,0.45)',
};
const publicCardContent = {
  p: { xs: 3, sm: 4 },
  '&:last-child': { pb: { xs: 3, sm: 4 } },
};
const publicHeader = { textAlign: 'center', mb: 3 };
const publicLogo = {
  display: 'block',
  width: 300,
  maxWidth: '100%',
  objectFit: 'contain',
  mx: 'auto',
  mb: 1.5,
};
const publicTitle = {
  fontWeight: 'bold',
  fontSize: { xs: '1.75rem', sm: '2.125rem' },
};
const publicDescription = { mt: 1.5 };
const primaryAction = { py: 1.4, fontWeight: 'bold' };
const backToLogin = { mt: 2, textAlign: 'center' };
const feedbackAlert = { mt: 2 };
const loadingProgress = { color: 'inherit' };
const dialogPaper = { width: '100%', maxWidth: 480, borderRadius: 4 };
const dialogTitle = { fontWeight: 'bold', pb: 1 };
const dialogDescription = { color: 'text.secondary', mb: 2 };
const responsiveDialogActions = {
  px: { xs: 3, sm: 4 },
  pb: 3,
  gap: 1,
  flexDirection: { xs: 'column-reverse', sm: 'row' },
  '& > button': { width: { xs: '100%', sm: 'auto' } },
};

/** Tema visual único de Hermandad Project. */
export const appTheme = createTheme({
  palette: {
    mode: 'dark',
    primary: {
      main: colors.primary,
      light: colors.primaryLight,
      dark: colors.primaryDark,
      contrastText: '#F8F3E7',
    },
    secondary: {
      main: colors.secondary,
      light: colors.secondaryLight,
      dark: colors.secondaryDark,
      contrastText: '#1A1020',
    },
    error: { main: '#B3261E' },
    warning: { main: '#C88A2D' },
    success: { main: '#3F7D4A' },
    info: { main: '#7A8FA6' },
    background: { default: colors.background, paper: colors.paper },
    text: { primary: colors.text, secondary: colors.textSecondary },
    divider: 'rgba(212, 175, 55, 0.22)',
  },
  typography: {
    fontFamily: '"Cinzel", "Cormorant Garamond", "Roboto", sans-serif',
    h1: { fontFamily: '"Cinzel", serif', fontWeight: 700, letterSpacing: '0.04em' },
    h2: { fontFamily: '"Cinzel", serif', fontWeight: 700, letterSpacing: '0.035em' },
    h3: { fontFamily: '"Cinzel", serif', fontWeight: 600 },
    h4: { fontFamily: '"Cinzel", serif', fontWeight: 600 },
    button: { fontWeight: 700, letterSpacing: '0.04em', textTransform: 'none' },
  },
  shape: { borderRadius: 14 },
  components: {
    MuiPaper: {
      styleOverrides: {
        root: {
          backgroundImage: 'linear-gradient(145deg, rgba(75,29,92,0.25), rgba(14,10,18,0.95))',
          border: '1px solid rgba(212, 175, 55, 0.18)',
          boxShadow: '0 10px 30px rgba(0,0,0,0.35)',
        },
      },
    },
    MuiCard: {
      styleOverrides: {
        root: {
          background: 'linear-gradient(160deg, rgba(31,20,39,0.98), rgba(18,12,24,0.98))',
          border: '1px solid rgba(212, 175, 55, 0.22)',
          boxShadow: '0 14px 40px rgba(0,0,0,0.45)',
        },
      },
    },
    MuiButton: {
      styleOverrides: {
        root: { borderRadius: 999, paddingInline: 22 },
        containedPrimary: {
          background: `linear-gradient(135deg, ${colors.primary} 0%, ${colors.primaryLight} 100%)`,
          border: '1px solid rgba(212, 175, 55, 0.35)',
        },
        containedSecondary: {
          background: `linear-gradient(135deg, ${colors.secondary} 0%, ${colors.secondaryDark} 100%)`,
          color: '#160D1C',
        },
        outlined: { borderColor: 'rgba(212, 175, 55, 0.55)' },
      },
    },
    MuiAppBar: {
      styleOverrides: {
        root: {
          background: `linear-gradient(90deg, ${colors.background} 0%, ${colors.primaryDark} 55%, ${colors.background} 100%)`,
          borderBottom: '1px solid rgba(212, 175, 55, 0.25)',
        },
      },
    },
    MuiDrawer: {
      styleOverrides: {
        paper: {
          background: 'linear-gradient(180deg, #130B18 0%, #211029 100%)',
          borderRight: '1px solid rgba(212, 175, 55, 0.22)',
        },
      },
    },
    MuiChip: { styleOverrides: { root: { borderRadius: 999, fontWeight: 600 } } },
  },
});

/** Reglas globales servidas por MUI GlobalStyles. */
export const appGlobalStyles = {
  'html, body, #root': { minHeight: '100%' },
  body: { margin: 0, minWidth: 320 },
  '#root': { minHeight: '100vh' },
};

/** Todos los estilos estáticos propios de pantallas y componentes. */
export const appStyles = {
  layout: {
    publicPage,
    publicCard,
    compactPublicCard,
    publicCardContent,
    publicHeader,
    publicLogo,
    publicTitle,
    publicDescription,
  },
  buttons: { primaryAction },
  feedback: {
    alert: feedbackAlert,
    loadingProgress,
    snackbarAlert: { width: '100%', borderRadius: 3 },
  },
  navigation: { backToLogin },
  dialogs: {
    paper: dialogPaper,
    title: dialogTitle,
    description: dialogDescription,
    responsiveActions: responsiveDialogActions,
  },
  gestionLogin: {
    login: {
      page: publicPage,
      card: compactPublicCard,
      cardContent: { padding: 4 },
      header: publicHeader,
      logoIcon: publicLogo,
      subtitle: { mt: 1 },
      divider: { mb: 3, borderColor: 'rgba(212, 175, 55, 0.25)' },
      submitButton: { ...primaryAction, mt: 3 },
      forgotPassword: { textAlign: 'center', mt: 3 },
      forgotPasswordLink: { font: 'inherit', cursor: 'pointer' },
      registerText: { mt: 3 },
    },
    registration: {
      page: publicPage,
      card: compactPublicCard,
      cardContent: { padding: 4 },
      header: publicHeader,
      logoIcon: publicLogo,
      subtitle: { mt: 1 },
      form: { mt: 2 },
      submitButton: { ...primaryAction, mt: 3 },
      backLogin: backToLogin,
      footerLegend: {
        mt: 'auto',
        mb: 2,
        alignSelf: 'flex-end',
        color: 'rgba(255,255,255,0.7)',
        fontSize: '0.75rem',
        letterSpacing: 1,
      },
    },
    accountActivation: {
      page: { ...responsivePublicPage, flexDirection: 'column' },
      card: publicCard,
      cardContent: publicCardContent,
      header: publicHeader,
      logoIcon: publicLogo,
      title: publicTitle,
      description: publicDescription,
      content: { mt: 3, display: 'flex', flexDirection: 'column', gap: 2 },
      statusMessage: {
        minHeight: 48,
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        textAlign: 'center',
      },
      successMessage: { color: 'success.light', fontWeight: 600 },
      errorMessage: { color: 'error.light', fontWeight: 600 },
      submitButton: primaryAction,
      buttonProgress: loadingProgress,
      personalLink: { textAlign: 'center', color: 'text.secondary' },
      backLogin: { ...backToLogin, mt: 1 },
      dialogPaper: { width: '100%', maxWidth: 440, borderRadius: 4 },
      dialogTitle: { textAlign: 'center', fontWeight: 'bold' },
      dialogContent: { textAlign: 'center', color: 'text.secondary' },
      dialogActions: { px: 3, pb: 3 },
      dialogButton: { py: 1.2, fontWeight: 'bold' },
    },
    passwordResetRequest: {
      paper: dialogPaper,
      title: dialogTitle,
      description: dialogDescription,
      alert: feedbackAlert,
      actions: responsiveDialogActions,
      progress: loadingProgress,
    },
    passwordReset: {
      page: responsivePublicPage,
      card: publicCard,
      cardContent: publicCardContent,
      header: publicHeader,
      logoIcon: publicLogo,
      title: publicTitle,
      description: publicDescription,
      form: { mt: 2 },
      requirements: { mt: 1, mb: 1, pl: 2.5, color: 'text.secondary' },
      passwordToggle: { color: 'text.secondary' },
      alert: feedbackAlert,
      submitButton: { ...primaryAction, mt: 2 },
      progress: loadingProgress,
      backLogin: backToLogin,
    },
  },
  seleccionPartida: {
    page: {
      minHeight: '100vh',
      background: 'radial-gradient(circle at top, rgba(75,29,92,0.35) 0%, #0E0A12 45%, #09060C 100%)',
      padding: { xs: 2, md: 4 },
    },
    content: { maxWidth: '1600px', margin: '0 auto' },
    topBar: { display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 5, gap: 2, flexWrap: 'wrap' },
    brand: { display: 'flex', alignItems: 'center', gap: 2 },
    logoIcon: publicLogo,
    brandTitle: { fontWeight: 700, lineHeight: 1, color: 'text.primary' },
    brandSubtitle: { color: 'secondary.main', letterSpacing: '0.15em', textTransform: 'uppercase' },
    userBox: {
      display: 'flex', alignItems: 'center', gap: 2, padding: '12px 18px', borderRadius: '18px',
      background: 'rgba(255,255,255,0.04)', border: '1px solid rgba(212,175,55,0.18)', backdropFilter: 'blur(12px)',
    },
    userIcon: { color: 'secondary.main' },
    header: { mb: 4 },
    pageTitle: { fontWeight: 700, mb: 1, color: 'text.primary' },
    grid: { display: 'grid', gridTemplateColumns: { xs: '1fr', md: 'repeat(2, 1fr)', xl: 'repeat(4, 1fr)' }, gap: 3, mb: 5 },
    gameCard: {
      position: 'relative', overflow: 'hidden', borderRadius: '28px', minHeight: '520px', display: 'flex', flexDirection: 'column',
      background: 'linear-gradient(180deg, rgba(31,20,39,0.98) 0%, rgba(18,12,24,0.98) 100%)',
      border: '1px solid rgba(212,175,55,0.15)', transition: 'all 0.25s ease',
      '&:hover': { transform: 'translateY(-6px)', border: '1px solid rgba(212,175,55,0.45)', boxShadow: '0 25px 50px rgba(0,0,0,0.45)' },
    },
    lastGameCard: { border: '1px solid rgba(212,175,55,0.35)', boxShadow: '0 0 25px rgba(212,175,55,0.12)' },
    lastGameChip: {
      position: 'absolute', top: 18, right: 18, zIndex: 2, fontWeight: 700, color: '#120C18',
      background: 'linear-gradient(135deg, rgba(212,175,55,1) 0%, rgba(155,122,31,1) 100%)',
    },
    gameCardContent: { display: 'flex', flexDirection: 'column', flex: 1, padding: 4 },
    shieldWrapper: {
      width: 92, height: 92, borderRadius: '50%', margin: '0 auto 24px auto', display: 'flex', alignItems: 'center', justifyContent: 'center',
      background: 'radial-gradient(circle, rgba(212,175,55,0.22) 0%, rgba(212,175,55,0.06) 70%)', border: '1px solid rgba(212,175,55,0.25)',
    },
    shieldIcon: { fontSize: 48, color: 'secondary.main' },
    gameTitle: { textAlign: 'center', fontWeight: 700, mb: 1, color: 'text.primary' },
    city: { textAlign: 'center', color: 'secondary.main', fontWeight: 600, mb: 0.5 },
    statsBox: { mt: 4, display: 'flex', flexDirection: 'column', gap: 2.2 },
    statRow: { display: 'flex', flexDirection: 'column', gap: 0.8 },
    statLabel: { display: 'flex', alignItems: 'center', gap: 1, color: 'text.secondary' },
    statProgressWrapper: { display: 'flex', alignItems: 'center', gap: 1.5 },
    statProgress: { flex: 1, height: 10, borderRadius: 999, backgroundColor: 'rgba(255,255,255,0.08)' },
    statValue: { minWidth: '30px', textAlign: 'right', fontWeight: 700, color: 'text.primary' },
    moneyRow: {
      mt: 1, display: 'flex', justifyContent: 'space-between', alignItems: 'center', padding: '10px 14px', borderRadius: '14px',
      background: 'rgba(255,255,255,0.03)', border: '1px solid rgba(212,175,55,0.08)',
    },
    money: { fontWeight: 700, color: 'secondary.main' },
    lastAccess: { display: 'flex', alignItems: 'center', gap: 1, mt: 'auto', mb: 3, color: 'text.secondary' },
    continueButton: {
      py: 1.4, borderRadius: '14px', fontWeight: 700,
      background: 'linear-gradient(135deg, #4B1D5C 0%, #6D2F83 100%)',
      '&:hover': { background: 'linear-gradient(135deg, #5A2370 0%, #7C3695 100%)' },
    },
    newGameCard: {
      minHeight: '520px', borderRadius: '28px', border: '2px dashed rgba(212,175,55,0.25)', transition: 'all 0.25s ease',
      background: 'linear-gradient(180deg, rgba(26,18,32,0.75) 0%, rgba(15,10,20,0.9) 100%)',
      '&:hover': { transform: 'translateY(-6px)', border: '2px dashed rgba(212,175,55,0.55)', boxShadow: '0 20px 40px rgba(0,0,0,0.35)' },
    },
    newGameContent: { height: '100%', display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center', padding: 4, textAlign: 'center' },
    newGameIconWrapper: {
      width: 100, height: 100, borderRadius: '50%', display: 'flex', alignItems: 'center', justifyContent: 'center', mb: 3,
      background: 'radial-gradient(circle, rgba(212,175,55,0.18) 0%, rgba(212,175,55,0.03) 70%)', border: '1px solid rgba(212,175,55,0.22)',
    },
    newGameIcon: { fontSize: 54, color: 'secondary.main' },
    newGameTitle: { fontWeight: 700, mb: 2 },
    newGameText: { maxWidth: '260px', mb: 4, lineHeight: 1.7 },
    newGameButton: {
      borderRadius: '14px', px: 3, py: 1.2, fontWeight: 700, borderColor: 'rgba(212,175,55,0.45)',
      '&:hover': { borderColor: 'secondary.main', background: 'rgba(212,175,55,0.08)' },
    },
    adviceCard: {
      borderRadius: '24px', background: 'linear-gradient(135deg, rgba(44,24,58,0.95) 0%, rgba(18,12,24,0.98) 100%)',
      border: '1px solid rgba(212,175,55,0.15)',
    },
    adviceContent: { display: 'flex', alignItems: 'center', gap: 3, padding: 3 },
    adviceIcon: { fontSize: 42, color: 'secondary.main' },
    adviceArrow: { marginLeft: 'auto', color: 'secondary.main' },
  },
  characterOnboarding: {
    page: { minHeight: '100vh', background: 'radial-gradient(circle at top, rgba(75,29,92,0.32), #0E0A12 48%)', p: { xs: 2, md: 4 } },
    content: { maxWidth: 1500, mx: 'auto' },
    header: { display: 'flex', alignItems: 'center', gap: 2, mb: 4 },
    brandIcon: { fontSize: 44, color: 'secondary.main' },
    welcome: { display: 'flex', flexDirection: 'column', gap: 2, mb: 4, maxWidth: 900 },
    stepper: { mb: 4, overflowX: 'auto', pb: 1 },
    columns: { display: 'grid', gridTemplateColumns: { xs: 'minmax(0, 1fr)', lg: 'repeat(2, minmax(0, 1fr))' }, gap: 3, alignItems: 'start' },
    panelContent: { p: { xs: 2.5, sm: 4 }, display: 'flex', flexDirection: 'column', gap: 2.5 },
    form: { display: 'flex', flexDirection: 'column', gap: 2.5 },
    formGrid: { display: 'grid', gridTemplateColumns: { xs: '1fr', sm: 'repeat(2, minmax(0, 1fr))' }, gap: 2 },
    searchIcon: { mr: 1, color: 'text.secondary' },
    characterCard: { borderColor: 'divider' },
    selectedCharacter: { border: '2px solid', borderColor: 'secondary.main', boxShadow: '0 0 0 3px rgba(212,175,55,0.12)' },
    characterContent: { display: 'grid', gridTemplateColumns: { xs: 'auto 1fr', sm: 'auto 1fr auto' }, gap: 2, alignItems: 'center', '&:last-child': { pb: 2 } },
    characterInfo: { minWidth: 0 },
    avatar: { width: 64, height: 64, bgcolor: 'primary.main' },
    footerNote: { textAlign: 'center', color: 'text.secondary', mt: 4 },
    confirmation: { maxWidth: 620, mx: 'auto', mt: { xs: 2, md: 8 }, textAlign: 'center' },
    confirmationIcon: { fontSize: 64, color: 'secondary.main', mx: 'auto' },
    largeAvatar: { width: 112, height: 112, mx: 'auto', bgcolor: 'primary.main' },
  },
};
