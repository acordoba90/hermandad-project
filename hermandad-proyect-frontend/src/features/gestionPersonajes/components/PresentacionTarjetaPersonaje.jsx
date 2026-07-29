import { Box, Button, CardContent, Chip, Typography } from '@mui/material';
import InsightsOutlinedIcon from '@mui/icons-material/InsightsOutlined';
import MenuBookOutlinedIcon from '@mui/icons-material/MenuBookOutlined';
import PersonIcon from '@mui/icons-material/Person';
import CharacterAvatar from './CharacterAvatar';
import { appStyles } from '../../../styles/appStyles';
import { getLeadershipStyle } from '../characterOnboardingFunctions';

/** Cara inicial de la tarjeta con identidad, retrato y accesos a sus detalles. */
const PresentacionTarjetaPersonaje = ({
  personaje,
  seleccionado,
  onShowStory,
  onShowProfile,
}) => {
  const styles = appStyles.characterOnboarding;
  const estiloLiderazgo = getLeadershipStyle(personaje);
  const nombreCompleto = `${personaje.nombre} ${personaje.apellidos || ''}`.trim();

  return (
    <CardContent sx={styles.cardPresentationContent}>
      <Box sx={styles.cardIdentityHeader}>
        <CharacterAvatar
          tone={personaje.avatarTone}
          nombre={personaje.nombre}
          apellidos={personaje.apellidos}
        />
        <Box sx={styles.cardIdentityCopy}>
          <Typography component="h2" variant="h5">{nombreCompleto}</Typography>
          <Typography color="secondary.main" fontWeight={700}>
            {estiloLiderazgo.nombre}
          </Typography>
          {seleccionado && <Chip label="Seleccionado" color="secondary" size="small" />}
        </Box>
      </Box>

      <Box sx={[styles.characterPortrait, styles.avatarTones[personaje.avatarTone || 'wine']]}>
        {personaje.urlAvatar ? (
          <Box
            component="img"
            src={personaje.urlAvatar}
            alt={`Retrato de ${nombreCompleto}`}
            sx={styles.characterPortraitImage}
          />
        ) : (
          <PersonIcon sx={styles.characterPortraitFallback} aria-hidden="true" />
        )}
      </Box>

      <Box sx={styles.cardFaceActions}>
        <Button
          variant="contained"
          startIcon={<MenuBookOutlinedIcon />}
          onClick={onShowStory}
        >
          Ver historia
        </Button>
        <Button
          variant="contained"
          endIcon={<InsightsOutlinedIcon />}
          onClick={onShowProfile}
        >
          Ver perfil
        </Button>
      </Box>
    </CardContent>
  );
};

export default PresentacionTarjetaPersonaje;
