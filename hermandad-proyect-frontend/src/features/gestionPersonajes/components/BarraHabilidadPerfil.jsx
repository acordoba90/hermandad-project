import { Box, LinearProgress, Typography } from '@mui/material';
import { appStyles } from '../../../styles/appStyles';

/** Representa una estadística del perfil con valor numérico y escala visual de 0 a 100. */
const BarraHabilidadPerfil = ({ label, value }) => {
  const styles = appStyles.characterOnboarding;
  const numericValue = Number(value);
  const available = Number.isFinite(numericValue);
  const normalizedValue = available ? Math.min(100, Math.max(0, numericValue)) : 0;

  return (
    <Box sx={styles.profileSkillRow}>
      <Typography variant="caption" sx={styles.profileSkillLabel}>{label}</Typography>
      <LinearProgress
        variant="determinate"
        value={normalizedValue}
        color="secondary"
        aria-label={`${label}: ${available ? numericValue : 'sin valor'}`}
        sx={styles.profileSkillProgress}
      />
      <Typography variant="caption" sx={styles.profileSkillValue}>
        {available ? numericValue : '—'}
      </Typography>
    </Box>
  );
};

export default BarraHabilidadPerfil;
