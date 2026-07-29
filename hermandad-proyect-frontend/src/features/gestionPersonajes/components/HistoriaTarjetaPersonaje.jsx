import { Box, Button, CardContent, Divider, Stack, Typography } from '@mui/material';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import InsightsOutlinedIcon from '@mui/icons-material/InsightsOutlined';
import { appStyles } from '../../../styles/appStyles';

/** Cara narrativa con la descripción, biografía y motivación del personaje. */
const HistoriaTarjetaPersonaje = ({ personaje, onBack, onShowProfile }) => {
  const styles = appStyles.characterOnboarding;

  return (
    <CardContent sx={styles.cardDetailContent}>
      <Box sx={styles.cardDetailHeader}>
        <Typography component="h2" variant="h5">
          {personaje.nombre} {personaje.apellidos}
        </Typography>
        <Typography color="text.secondary">
          {personaje.edad} años · {personaje.profesion}
        </Typography>
      </Box>
      <Divider />
      <Stack spacing={2.5} sx={styles.cardStoryScroll}>
        <Box>
          <Typography variant="overline" color="secondary.main">Descripción</Typography>
          <Typography color="text.secondary">
            {personaje.descripcion || 'Sin descripción disponible.'}
          </Typography>
        </Box>
        <Box>
          <Typography variant="overline" color="secondary.main">Biografía</Typography>
          <Typography color="text.secondary">
            {personaje.biografia || 'Sin biografía disponible.'}
          </Typography>
        </Box>
        <Box>
          <Typography variant="overline" color="secondary.main">Motivación</Typography>
          <Typography color="text.secondary">
            {personaje.motivacion || 'Sin motivación detallada.'}
          </Typography>
        </Box>
      </Stack>
      <Box sx={styles.cardFaceActions}>
        <Button variant="outlined" startIcon={<ArrowBackIcon />} onClick={onBack}>
          Personaje
        </Button>
        <Button variant="contained" endIcon={<InsightsOutlinedIcon />} onClick={onShowProfile}>
          Ver perfil
        </Button>
      </Box>
    </CardContent>
  );
};

export default HistoriaTarjetaPersonaje;
