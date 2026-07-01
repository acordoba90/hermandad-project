export const altaUsuarioStyles = {
  page: {
    minHeight: '100vh',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    background:
      'linear-gradient(135deg, #0E0A12 0%, #2A1035 45%, #4B1D5C 100%)',
    padding: 2,
  },

  card: {
    width: '100%',
    maxWidth: 420,
    borderRadius: 4,
    backgroundColor: 'rgba(26, 18, 32, 0.95)',
    border: '1px solid rgba(212, 175, 55, 0.35)',
    boxShadow: '0 20px 60px rgba(0,0,0,0.45)',
  },

  cardContent: {
    padding: 4,
  },

  header: {
    textAlign: 'center',
    mb: 3,
  },

  subtitle: {
    mt: 1,
  },

  form: {
    mt: 2,
  },

  submitButton: {
    mt: 3,
    py: 1.4,
    fontWeight: 'bold',
    backgroundColor: 'primary.main',
    '&:hover': {
      backgroundColor: 'primary.dark',
    },
  },

  backLogin: {
    mt: 2,
    textAlign: 'center',
  },

  footerLegend: {
    mt: 'auto',
    mb: 2,
    alignSelf: 'flex-end',
    color: 'rgba(255,255,255,0.7)',
    fontSize: '0.75rem',
    letterSpacing: 1,
  },
};