import { createTheme } from '@mui/material/styles';

const theme = createTheme({
  palette: {
    mode: 'dark',

    primary: {
      main: '#4B1D5C', // morado nazareno
      light: '#6D2F83',
      dark: '#2A1035',
      contrastText: '#F8F3E7',
    },

    secondary: {
      main: '#D4AF37', // dorado barroco
      light: '#E7C96A',
      dark: '#9B7A1F',
      contrastText: '#1A1020',
    },

    error: {
      main: '#B3261E',
    },

    warning: {
      main: '#C88A2D',
    },

    success: {
      main: '#3F7D4A',
    },

    info: {
      main: '#7A8FA6',
    },

    background: {
      default: '#0E0A12', // fondo principal casi negro morado
      paper: '#1A1220',   // tarjetas/paneles
    },

    text: {
      primary: '#F4EBDD',
      secondary: '#B9A8C4',
    },

    divider: 'rgba(212, 175, 55, 0.22)',
  },

  typography: {
    fontFamily: '"Cinzel", "Cormorant Garamond", "Roboto", sans-serif',

    h1: {
      fontFamily: '"Cinzel", serif',
      fontWeight: 700,
      letterSpacing: '0.04em',
    },
    h2: {
      fontFamily: '"Cinzel", serif',
      fontWeight: 700,
      letterSpacing: '0.035em',
    },
    h3: {
      fontFamily: '"Cinzel", serif',
      fontWeight: 600,
    },
    h4: {
      fontFamily: '"Cinzel", serif',
      fontWeight: 600,
    },
    button: {
      fontWeight: 700,
      letterSpacing: '0.04em',
      textTransform: 'none',
    },
  },

  shape: {
    borderRadius: 14,
  },

  components: {
    MuiPaper: {
      styleOverrides: {
        root: {
          backgroundImage:
            'linear-gradient(145deg, rgba(75,29,92,0.25), rgba(14,10,18,0.95))',
          border: '1px solid rgba(212, 175, 55, 0.18)',
          boxShadow: '0 10px 30px rgba(0,0,0,0.35)',
        },
      },
    },

    MuiCard: {
      styleOverrides: {
        root: {
          background:
            'linear-gradient(160deg, rgba(31,20,39,0.98), rgba(18,12,24,0.98))',
          border: '1px solid rgba(212, 175, 55, 0.22)',
          boxShadow: '0 14px 40px rgba(0,0,0,0.45)',
        },
      },
    },

    MuiButton: {
      styleOverrides: {
        root: {
          borderRadius: 999,
          paddingInline: 22,
        },
        containedPrimary: {
          background:
            'linear-gradient(135deg, #4B1D5C 0%, #6D2F83 100%)',
          border: '1px solid rgba(212, 175, 55, 0.35)',
        },
        containedSecondary: {
          background:
            'linear-gradient(135deg, #D4AF37 0%, #9B7A1F 100%)',
          color: '#160D1C',
        },
        outlined: {
          borderColor: 'rgba(212, 175, 55, 0.55)',
        },
      },
    },

    MuiAppBar: {
      styleOverrides: {
        root: {
          background:
            'linear-gradient(90deg, #0E0A12 0%, #2A1035 55%, #0E0A12 100%)',
          borderBottom: '1px solid rgba(212, 175, 55, 0.25)',
        },
      },
    },

    MuiDrawer: {
      styleOverrides: {
        paper: {
          background:
            'linear-gradient(180deg, #130B18 0%, #211029 100%)',
          borderRight: '1px solid rgba(212, 175, 55, 0.22)',
        },
      },
    },

    MuiChip: {
      styleOverrides: {
        root: {
          borderRadius: 999,
          fontWeight: 600,
        },
      },
    },
  },
});

export default theme;