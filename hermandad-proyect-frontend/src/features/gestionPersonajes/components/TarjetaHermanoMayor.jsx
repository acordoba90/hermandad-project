import { Box, Button, Card, CardContent, Chip, Typography } from '@mui/material';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import CharacterAvatar from './CharacterAvatar';
import { appStyles } from '../../../styles/appStyles';
import { characterOnboardingTexts as texts } from '../characterOnboardingTexts';

/**
 * Tarjeta reutilizable de candidato.
 * @param {{ personaje: object, seleccionado: boolean, onSeleccionar: Function }} props
 */
const TarjetaHermanoMayor = ({ personaje, seleccionado, onSeleccionar }) => {
  const styles = appStyles.characterOnboarding;

  return (
    <Card sx={[styles.candidateCard, seleccionado && styles.candidateCardSelected]}>
      <Box sx={styles.candidatePortrait}>
        <CharacterAvatar
          avatarId={personaje.avatarId}
          nombre={personaje.nombre}
          apellidos={personaje.apellidos}
          large
        />
        <Chip label={personaje.tipoPersonaje} sx={styles.candidateType} />
        {seleccionado && (
          <Box sx={styles.selectedBadge}>
            <CheckCircleIcon fontSize="small" />
            <Typography variant="caption" fontWeight={700}>Seleccionado</Typography>
          </Box>
        )}
      </Box>
      <CardContent sx={styles.candidateContent}>
        <Box>
          <Typography component="h2" variant="h5">{personaje.nombre} {personaje.apellidos}</Typography>
          <Typography color="secondary.main">{personaje.edad} años · {personaje.profesion}</Typography>
        </Box>
        <Typography variant="body2" color="text.secondary">{personaje.biografia}</Typography>
        <Box>
          <Typography variant="overline" color="secondary.main">Motivación</Typography>
          <Typography variant="body2" color="text.secondary">{personaje.motivacion}</Typography>
        </Box>
        <Box sx={styles.traits}>
          {personaje.rasgos.map((rasgo) => <Chip key={rasgo} label={rasgo} size="small" variant="outlined" />)}
        </Box>
        <Button
          fullWidth
          variant={seleccionado ? 'contained' : 'outlined'}
          color={seleccionado ? 'secondary' : 'primary'}
          startIcon={seleccionado ? <CheckCircleIcon /> : undefined}
          aria-pressed={seleccionado}
          onClick={() => onSeleccionar(personaje)}
        >
          {seleccionado ? texts.common.selected : texts.common.select}
        </Button>
      </CardContent>
    </Card>
  );
};

export default TarjetaHermanoMayor;
