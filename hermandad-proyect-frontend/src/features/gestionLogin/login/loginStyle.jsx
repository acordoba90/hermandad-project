export const loginPageStyles = {
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

  logoIcon: {
    display: 'block',
    width: 300,
    objectFit: 'contain',
    mx: 'auto',
    mb: 1.5,
  },

  subtitle: {
    mt: 1,
  },

  divider: {
    mb: 3,
    borderColor: 'rgba(212, 175, 55, 0.25)',
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

  forgotPassword: {
    textAlign: 'center',
    mt: 3,
  },

  registerText: {
    mt: 3,
  },
};
