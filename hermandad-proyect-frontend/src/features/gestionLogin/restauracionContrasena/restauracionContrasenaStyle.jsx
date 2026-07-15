export const restauracionContrasenaStyles = {
  page: {
    minHeight: '100vh',
    display: 'flex',
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
    '&:last-child': { pb: { xs: 3, sm: 4 } },
  },
  header: { textAlign: 'center', mb: 3 },
  logoIcon: { display: 'block', width: 300, maxWidth: '100%', objectFit: 'contain', mx: 'auto', mb: 1.5 },
  title: { fontWeight: 'bold', fontSize: { xs: '1.75rem', sm: '2.125rem' } },
  description: { mt: 1.5 },
  form: { mt: 2 },
  requirements: { mt: 1, mb: 1, pl: 2.5, color: 'text.secondary' },
  passwordToggle: { color: 'text.secondary' },
  alert: { mt: 2 },
  submitButton: { mt: 2, py: 1.4, fontWeight: 'bold' },
  progress: { color: 'inherit' },
  backLogin: { mt: 2, textAlign: 'center' },
};
