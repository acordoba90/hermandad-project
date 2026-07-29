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
    page: {
      position: 'relative', minHeight: '100vh', overflow: 'hidden', p: { xs: 2, sm: 3, lg: 4 },
      background: 'radial-gradient(circle at 50% -10%, rgba(109,47,131,0.42) 0%, rgba(14,10,18,0.96) 42%, #09060C 100%)',
    },
    ambientLightOne: {
      position: 'absolute', width: 360, height: 360, borderRadius: '50%', top: '8%', left: '-180px',
      background: 'radial-gradient(circle, rgba(212,175,55,0.12), transparent 68%)', pointerEvents: 'none',
    },
    ambientLightTwo: {
      position: 'absolute', width: 480, height: 480, borderRadius: '50%', right: '-260px', bottom: '-120px',
      background: 'radial-gradient(circle, rgba(75,29,92,0.3), transparent 68%)', pointerEvents: 'none',
    },
    content: { position: 'relative', zIndex: 1, width: '100%', maxWidth: 1480, mx: 'auto' },
    brandHeader: { display: 'flex', alignItems: 'center', gap: 2, mb: { xs: 4, md: 6 } },
    brandLogo: { width: { xs: 92, sm: 124 }, height: { xs: 64, sm: 78 }, objectFit: 'contain' },
    brandName: { fontWeight: 700, lineHeight: 1.1 },
    brandClaim: { display: 'block', mt: 0.5, color: 'secondary.main', letterSpacing: '0.13em', textTransform: 'uppercase' },
    view: { animation: 'onboardingFade 240ms ease-out', '@keyframes onboardingFade': { from: { opacity: 0, transform: 'translateY(8px)' }, to: { opacity: 1, transform: 'translateY(0)' } }, '@media (prefers-reduced-motion: reduce)': { animation: 'none' } },
    initialView: { display: 'flex', flexDirection: 'column', alignItems: 'center', pb: 4 },
    heroCopy: { maxWidth: 920, mx: 'auto', mb: { xs: 4, md: 6 }, textAlign: 'center' },
    eyebrow: { color: 'secondary.main', fontWeight: 700, letterSpacing: '0.18em' },
    heroTitle: { mt: 1, mb: 2, fontSize: { xs: '2.25rem', sm: '3.25rem', lg: '4rem' } },
    heroDescription: { maxWidth: 720, mx: 'auto', mt: 1.5, lineHeight: 1.8 },
    optionsGrid: { width: '100%', maxWidth: 1100, display: 'grid', gridTemplateColumns: { xs: '1fr', md: 'repeat(2, minmax(0, 1fr))' }, gap: { xs: 2.5, md: 4 } },
    optionCard: {
      height: '100%', overflow: 'hidden', borderRadius: '28px', border: '1px solid rgba(212,175,55,0.28)',
      transition: 'transform 220ms ease, border-color 220ms ease, box-shadow 220ms ease',
      '&:hover': { transform: 'translateY(-6px)', borderColor: 'secondary.main', boxShadow: '0 24px 60px rgba(0,0,0,0.5)' },
      '&:hover .onboarding-option-icon': { color: 'secondary.light', transform: 'scale(1.06)' },
      '@media (prefers-reduced-motion: reduce)': { transition: 'none', '&:hover': { transform: 'none' } },
    },
    optionActionArea: {
      height: '100%', borderRadius: 'inherit',
      '&.Mui-focusVisible': { outline: '3px solid', outlineColor: 'secondary.main', outlineOffset: -4 },
    },
    optionContent: { minHeight: { xs: 320, md: 390 }, p: { xs: 3, sm: 4.5 }, display: 'flex', flexDirection: 'column', alignItems: 'center', textAlign: 'center', '&:last-child': { pb: { xs: 3, sm: 4.5 } } },
    optionIcon: { width: 104, height: 104, mb: 3, borderRadius: '50%', display: 'flex', alignItems: 'center', justifyContent: 'center', color: 'secondary.main', background: 'radial-gradient(circle, rgba(212,175,55,0.18), rgba(212,175,55,0.03) 70%)', border: '1px solid rgba(212,175,55,0.3)', transition: 'color 220ms ease, transform 220ms ease' },
    optionIconGlyph: { fontSize: 54 },
    optionTitle: { mb: 2 },
    optionDescription: { maxWidth: 420, lineHeight: 1.75 },
    optionCta: { mt: 'auto', pt: 4, display: 'flex', alignItems: 'center', gap: 1, color: 'secondary.main' },
    footerMessage: { maxWidth: 880, mt: 5, px: { xs: 2, sm: 3 }, py: 2, display: 'flex', alignItems: 'center', justifyContent: 'center', gap: 1.5, textAlign: 'center', borderTop: '1px solid', borderColor: 'divider', color: 'secondary.main' },
    backButton: { mb: 2, color: 'text.secondary', '&:focus-visible': { outline: '2px solid', outlineColor: 'secondary.main', outlineOffset: 3 } },
    viewHeader: { maxWidth: 920, mb: { xs: 3, md: 5 }, '& h1': { mb: 1.5 }, '& p': { lineHeight: 1.8 } },
    candidatesGrid: { display: 'grid', gridTemplateColumns: { xs: '1fr', sm: 'repeat(2, minmax(0, 1fr))', lg: 'repeat(3, minmax(0, 1fr))' }, gap: 3 },
    candidateCard: { height: { xs: 720, sm: 760 }, overflow: 'hidden', borderRadius: '24px', transition: 'transform 200ms ease, border-color 200ms ease, box-shadow 200ms ease', '&:hover': { transform: 'translateY(-4px)', borderColor: 'rgba(212,175,55,0.5)' }, '@media (prefers-reduced-motion: reduce)': { transition: 'none', '&:hover': { transform: 'none' } } },
    candidateCardSelected: { border: '2px solid', borderColor: 'secondary.main', boxShadow: '0 0 0 3px rgba(212,175,55,0.1), 0 18px 42px rgba(0,0,0,0.5)' },
    cardStage: { position: 'relative', height: '100%', perspective: '1400px' },
    cardFace: { position: 'absolute', inset: 0, visibility: 'hidden', opacity: 0, transformStyle: 'preserve-3d', backfaceVisibility: 'hidden', background: 'linear-gradient(160deg, rgba(31,20,39,0.99), rgba(18,12,24,0.99))', transition: 'transform 480ms cubic-bezier(0.4, 0, 0.2, 1), opacity 260ms ease, visibility 0s linear 480ms', '@media (prefers-reduced-motion: reduce)': { transition: 'opacity 160ms ease, visibility 0s linear 160ms', transform: 'none' } },
    cardFaceActive: { zIndex: 2, visibility: 'visible', opacity: 1, transform: 'rotateY(0deg)', transitionDelay: '0s', '@media (prefers-reduced-motion: reduce)': { transform: 'none' } },
    cardFaceBefore: { zIndex: 1, transform: 'rotateY(-92deg)', transformOrigin: 'left center', '@media (prefers-reduced-motion: reduce)': { transform: 'none' } },
    cardFaceAfter: { zIndex: 1, transform: 'rotateY(92deg)', transformOrigin: 'right center', '@media (prefers-reduced-motion: reduce)': { transform: 'none' } },
    cardPresentationContent: { height: '100%', boxSizing: 'border-box', p: { xs: 2.5, sm: 3 }, display: 'flex', flexDirection: 'column', gap: 2.5, '&:last-child': { pb: { xs: 2.5, sm: 3 } } },
    cardIdentityHeader: { display: 'grid', gridTemplateColumns: 'auto minmax(0, 1fr)', alignItems: 'center', gap: 2, minHeight: 76 },
    cardIdentityCopy: { minWidth: 0, '& h2': { overflow: 'hidden', textOverflow: 'ellipsis' }, '& > .MuiChip-root': { mt: 1 } },
    characterPortrait: { flex: 1, minHeight: 0, overflow: 'hidden', display: 'flex', alignItems: 'center', justifyContent: 'center', borderRadius: '20px', border: '1px solid rgba(212,175,55,0.32)', boxShadow: 'inset 0 0 48px rgba(0,0,0,0.28)' },
    characterPortraitImage: { width: '100%', height: '100%', objectFit: 'cover', objectPosition: 'center top' },
    characterPortraitFallback: { fontSize: { xs: 150, sm: 190 }, color: 'rgba(244,235,221,0.68)', filter: 'drop-shadow(0 14px 18px rgba(0,0,0,0.35))' },
    cardFaceActions: { mt: 'auto', display: 'grid', gridTemplateColumns: { xs: '1fr', sm: 'repeat(2, minmax(0, 1fr))' }, gap: 1.5, '& > button': { minHeight: 46, px: 1.5 } },
    cardDetailContent: { height: '100%', boxSizing: 'border-box', p: { xs: 2.5, sm: 3 }, display: 'flex', flexDirection: 'column', gap: 2, '&:last-child': { pb: { xs: 2.5, sm: 3 } } },
    cardDetailHeader: { minHeight: 66, '& h2': { mb: 0.75 }, '& p': { lineHeight: 1.5 } },
    cardStoryScroll: { flex: 1, minHeight: 0, overflowY: 'auto', pr: 1, '& p': { lineHeight: 1.7 } },
    profileCardHeader: { minHeight: 66, display: 'flex', alignItems: 'flex-start', justifyContent: 'space-between', gap: 1.5, '& h2': { mb: 0.5 } },
    profileMeta: { display: 'flex', flexWrap: 'wrap', gap: 1 },
    profileSkillsScroll: { flex: 1, minHeight: 0, overflowY: 'auto', pr: 1, display: 'flex', flexDirection: 'column', gap: 2 },
    profileSkillGroup: { display: 'flex', flexDirection: 'column', gap: 0.75 },
    profileSkillGroupTitle: { display: 'block', pb: 0.5, borderBottom: '1px solid', borderColor: 'divider', fontWeight: 700, letterSpacing: '0.13em' },
    profileSkillRow: { display: 'grid', gridTemplateColumns: 'minmax(108px, 0.85fr) minmax(72px, 1fr) 28px', alignItems: 'center', gap: 1 },
    profileSkillLabel: { color: 'text.secondary', lineHeight: 1.2 },
    profileSkillProgress: { height: 7, borderRadius: 999, backgroundColor: 'rgba(255,255,255,0.08)', '& .MuiLinearProgress-bar': { borderRadius: 999 } },
    profileSkillValue: { textAlign: 'right', color: 'text.primary', fontWeight: 700 },
    profileMissingAlert: { mt: 1 },
    catalogFeedback: { minHeight: 220, display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center', gap: 2 },
    selectionSummaryCard: { mt: 4, borderRadius: '26px' },
    selectionSummaryContent: { p: { xs: 2.5, sm: 4 }, display: 'flex', flexDirection: 'column', gap: 3, '&:last-child': { pb: { xs: 2.5, sm: 4 } } },
    confirmButton: { alignSelf: { xs: 'stretch', sm: 'flex-end' }, py: 1.3 },
    characterAvatar: { width: 68, height: 68, fontSize: '1.25rem', fontWeight: 800, border: '2px solid rgba(212,175,55,0.52)', boxShadow: '0 12px 28px rgba(0,0,0,0.35)' },
    characterAvatarLarge: { width: 126, height: 126, fontSize: '2rem' },
    avatarTones: {
      wine: { background: 'linear-gradient(145deg, #7B1E1E, #351015)' },
      gold: { background: 'linear-gradient(145deg, #D4AF37, #6F5616)', color: 'secondary.contrastText' },
      purple: { background: 'linear-gradient(145deg, #6D2F83, #2A1035)' },
      silver: { background: 'linear-gradient(145deg, #8D8A92, #343039)' },
      green: { background: 'linear-gradient(145deg, #35624A, #142C21)' },
      blue: { background: 'linear-gradient(145deg, #315B7A, #162B3D)' },
    },
    summary: { p: { xs: 2, sm: 3 }, display: 'flex', flexDirection: 'column', gap: 2.5, borderRadius: '20px', background: 'rgba(255,255,255,0.025)', border: '1px solid', borderColor: 'divider' },
    summaryIdentity: { display: 'grid', gridTemplateColumns: { xs: '1fr', sm: 'auto minmax(0, 1fr) auto' }, alignItems: 'center', gap: 2.5, textAlign: { xs: 'center', sm: 'left' }, '& > .MuiAvatar-root': { mx: { xs: 'auto', sm: 0 } }, '& > .MuiChip-root': { justifySelf: { xs: 'center', sm: 'end' } } },
    summaryIdentityText: { minWidth: 0 },
    creationCard: { borderRadius: '28px' },
    creationContent: { p: { xs: 2.5, sm: 4, lg: 5 }, '&:last-child': { pb: { xs: 2.5, sm: 4, lg: 5 } } },
    creationStepper: { mb: { xs: 3, md: 5 }, '& .MuiStepLabel-label.Mui-active, & .MuiStepLabel-label.Mui-completed': { color: 'secondary.main' }, '& .MuiStepIcon-root.Mui-active, & .MuiStepIcon-root.Mui-completed': { color: 'secondary.main' } },
    stepPanel: { minHeight: { md: 430 }, p: { xs: 0, sm: 1 } },
    stepTitle: { mb: 3 },
    formGrid: { display: 'grid', gridTemplateColumns: { xs: '1fr', md: 'repeat(2, minmax(0, 1fr))' }, gap: 2.5 },
    formStack: { display: 'flex', flexDirection: 'column', gap: 1 },
    characterCounter: { alignSelf: 'flex-end', color: 'text.secondary', mb: 1.5 },
    stepActions: { mt: 4, pt: 3, display: 'flex', justifyContent: 'space-between', gap: 2, borderTop: '1px solid', borderColor: 'divider', '& > button': { width: { xs: '100%', sm: 'auto' } }, flexDirection: { xs: 'column-reverse', sm: 'row' } },
    reviewActions: { ml: { sm: 'auto' }, display: 'flex', gap: 2, flexDirection: { xs: 'column-reverse', sm: 'row' }, '& > button': { width: { xs: '100%', sm: 'auto' } } },
    reviewPanel: { display: 'flex', flexDirection: 'column', gap: 3 },
    avatarSelectorLayout: { display: 'grid', gridTemplateColumns: { xs: '1fr', lg: 'minmax(240px, 0.7fr) minmax(0, 1.3fr)' }, gap: 4, alignItems: 'center' },
    avatarPreview: { minHeight: 300, p: 3, display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center', gap: 2, borderRadius: '22px', background: 'radial-gradient(circle, rgba(212,175,55,0.12), rgba(75,29,92,0.16) 55%, rgba(0,0,0,0.12))', border: '1px solid', borderColor: 'divider' },
    sectionTitle: { mb: 2.5 },
    avatarGrid: { display: 'grid', gridTemplateColumns: { xs: 'repeat(2, minmax(0, 1fr))', sm: 'repeat(3, minmax(0, 1fr))' }, gap: 2 },
    avatarOption: { position: 'relative', minHeight: 132, p: 2, borderRadius: '18px', display: 'flex', flexDirection: 'column', gap: 1.25, border: '1px solid', borderColor: 'divider', backgroundColor: 'rgba(255,255,255,0.025)', '&:hover': { borderColor: 'secondary.main', backgroundColor: 'rgba(212,175,55,0.06)' }, '&:focus-visible': { outline: '2px solid', outlineColor: 'secondary.main', outlineOffset: 2 } },
    compactSelection: { border: '2px solid', borderColor: 'secondary.main', backgroundColor: 'rgba(212,175,55,0.08)' },
    optionCheck: { position: 'absolute', top: 10, right: 10, color: 'secondary.main' },
    inlineAlert: { mt: 2 },
    personalityLayout: { display: 'flex', flexDirection: 'column', gap: 4 },
    leadershipHelp: { mb: 2.5, lineHeight: 1.7 },
    personalityGrid: { display: 'grid', gridTemplateColumns: { xs: '1fr', md: 'repeat(2, minmax(0, 1fr))' }, gap: 2 },
    personalityOption: { position: 'relative', minHeight: 130, p: 2.5, display: 'grid', gridTemplateColumns: 'auto minmax(0, 1fr)', gap: 2, alignItems: 'start', textAlign: 'left', borderRadius: '18px', border: '1px solid', borderColor: 'divider', backgroundColor: 'rgba(255,255,255,0.025)', '&:hover': { borderColor: 'secondary.main', backgroundColor: 'rgba(212,175,55,0.06)' }, '&:focus-visible': { outline: '2px solid', outlineColor: 'secondary.main', outlineOffset: 2 } },
    personalityIcon: { width: 46, height: 46, display: 'flex', alignItems: 'center', justifyContent: 'center', borderRadius: '50%', color: 'secondary.main', backgroundColor: 'rgba(212,175,55,0.1)' },
    personalityCopy: { pr: 2, '& p': { mt: 0.75, lineHeight: 1.55 } },
  },
};
