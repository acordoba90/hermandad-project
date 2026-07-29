import { Box, Chip, Divider, Stack, Typography } from '@mui/material';
import AutoAwesomeIcon from '@mui/icons-material/AutoAwesome';
import CharacterAvatar from './CharacterAvatar';
import { appStyles } from '../../../styles/appStyles';
import { getLeadershipStyle } from '../characterOnboardingFunctions';

/**
 * Resumen compartido por la selección de candidatos y la revisión del formulario.
 * @param {{ personaje: object }} props
 */
const ResumenHermanoMayor = ({ personaje }) => {
  const styles = appStyles.characterOnboarding;
  const estiloLiderazgo = getLeadershipStyle(personaje);

  return (
    <Box sx={styles.summary}>
      <Box sx={styles.summaryIdentity}>
        <CharacterAvatar
          avatarId={personaje.avatarId}
          urlAvatar={personaje.urlAvatar}
          tone={personaje.avatarTone}
          nombre={personaje.nombre}
          apellidos={personaje.apellidos}
          large
        />
        <Box sx={styles.summaryIdentityText}>
          <Typography component="h3" variant="h5">
            {personaje.nombre} {personaje.apellidos}
          </Typography>
          <Typography color="secondary.main" fontWeight={700}>{estiloLiderazgo.nombre}</Typography>
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
        {estiloLiderazgo.descripcion && (
          <Box>
            <Typography variant="overline" color="secondary.main">Estilo de liderazgo</Typography>
            <Typography color="text.secondary">{estiloLiderazgo.descripcion}</Typography>
          </Box>
        )}
      </Stack>
    </Box>
  );
};

export default ResumenHermanoMayor;
