/**
 * Estilos específicos de la pantalla, basados en el tema y en el alta de usuario.
 */
export const accountActivationStyles = {
  page: {
    minHeight: '100vh',
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center',
    background: (theme) =>
      `linear-gradient(135deg, ${theme.palette.background.default} 0%, ${theme.palette.primary.dark} 45%, ${theme.palette.primary.main} 100%)`,
    p: { xs: 2, sm: 3 },
  },
  card: {
    width: '100%',
    maxWidth: 460,
    borderRadius: 4,
    backgroundColor: 'rgba(26, 18, 32, 0.95)',
    borderColor: 'divider',
    boxShadow: 20,
  },
  cardContent: {
    p: { xs: 3, sm: 4 },
    '&:last-child': {
      pb: { xs: 3, sm: 4 },
    },
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
  title: {
    fontWeight: 'bold',
    fontSize: { xs: '1.75rem', sm: '2.125rem' },
  },
  description: {
    mt: 1.5,
  },
  content: {
    mt: 3,
    display: 'flex',
    flexDirection: 'column',
    gap: 2,
  },
  statusMessage: {
    minHeight: 48,
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    textAlign: 'center',
  },
  successMessage: {
    color: 'success.light',
    fontWeight: 600,
  },
  errorMessage: {
    color: 'error.light',
    fontWeight: 600,
  },
  submitButton: {
    py: 1.4,
    fontWeight: 'bold',
    backgroundColor: 'primary.main',
    '&:hover': {
      backgroundColor: 'primary.dark',
    },
  },
  buttonProgress: {
    color: 'inherit',
  },
  personalLink: {
    textAlign: 'center',
    color: 'text.secondary',
  },
  backLogin: {
    mt: 1,
    textAlign: 'center',
  },
};
