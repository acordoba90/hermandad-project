export const solicitudRestauracionStyles = {
  paper: {
    width: '100%',
    maxWidth: 480,
    borderRadius: 4,
  },
  title: { fontWeight: 'bold', pb: 1 },
  description: { color: 'text.secondary', mb: 2 },
  alert: { mt: 2 },
  actions: {
    px: { xs: 3, sm: 4 },
    pb: 3,
    gap: 1,
    flexDirection: { xs: 'column-reverse', sm: 'row' },
    '& > button': { width: { xs: '100%', sm: 'auto' } },
  },
  progress: { color: 'inherit' },
};
