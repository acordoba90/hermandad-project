import { Box, Chip, Divider, Stack, Typography } from '@mui/material';
import AutoAwesomeIcon from '@mui/icons-material/AutoAwesome';
import CharacterAvatar from './CharacterAvatar';
import { appStyles } from '../../../styles/appStyles';

/**
 * Resumen compartido por la selección de candidatos y la revisión del formulario.
 * @param {{ personaje: object }} props
 */
const ResumenHermanoMayor = ({ personaje }) => {
  const styles = appStyles.characterOnboarding;

  return (
    <Box sx={styles.summary}>
      <Box sx={styles.summaryIdentity}>
        <CharacterAvatar
          avatarId={personaje.avatarId}
          nombre={personaje.nombre}
          apellidos={personaje.apellidos}
          large
        />
        <Box sx={styles.summaryIdentityText}>
          <Typography component="h3" variant="h5">
            {personaje.nombre} {personaje.apellidos}
          </Typography>
          <Typography color="secondary.main" fontWeight={700}>{personaje.tipoPersonaje}</Typography>
          <Typography color="text.secondary">
            {personaje.edad} años · {personaje.profesion}
          </Typography>
        </Box>
        {personaje.personalizado && (
          <Chip icon={<AutoAwesomeIcon />} label="Personaje personalizado" color="secondary" />
        )}
      </Box>
      <Divider />
      <Stack spacing={2}>
        <Box>
          <Typography variant="overline" color="secondary.main">Biografía</Typography>
          <Typography color="text.secondary">
            {personaje.biografia || 'Sin biografía por el momento.'}
          </Typography>
        </Box>
        <Box>
          <Typography variant="overline" color="secondary.main">Motivación</Typography>
          <Typography color="text.secondary">
            {personaje.motivacion || 'Sin motivación detallada por el momento.'}
          </Typography>
        </Box>
        {personaje.rasgos?.length > 0 && (
          <Box sx={styles.traits}>
            {personaje.rasgos.map((rasgo) => <Chip key={rasgo} label={rasgo} variant="outlined" />)}
          </Box>
        )}
      </Stack>
    </Box>
  );
};

export default ResumenHermanoMayor;
