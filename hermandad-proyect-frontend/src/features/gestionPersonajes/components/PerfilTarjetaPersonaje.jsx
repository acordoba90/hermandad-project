import { Alert, Box, Button, CardContent, Chip, Divider, Typography } from '@mui/material';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import BarraHabilidadPerfil from './BarraHabilidadPerfil';
import { PROFILE_SKILL_GROUPS } from '../hermanoMayorConstants';
import { getLeadershipStyle } from '../characterOnboardingFunctions';
import { appStyles } from '../../../styles/appStyles';

/** Cara de habilidades con todos los valores persistidos en el perfil del personaje. */
const PerfilTarjetaPersonaje = ({ personaje, seleccionado, onBack, onSelect }) => {
  const styles = appStyles.characterOnboarding;
  const perfil = personaje.perfil;
  const estiloLiderazgo = getLeadershipStyle(personaje);

  return (
    <CardContent sx={styles.cardDetailContent}>
      <Box sx={styles.profileCardHeader}>
        <Box>
          <Typography component="h2" variant="h5">
            {personaje.nombre} {personaje.apellidos}
          </Typography>
          <Typography color="secondary.main" fontWeight={700}>{estiloLiderazgo.nombre}</Typography>
        </Box>
        {seleccionado && <Chip label="Seleccionado" color="secondary" size="small" />}
      </Box>

      {perfil ? (
        <>
          <Box sx={styles.profileMeta}>
            <Chip label={`Nivel ${perfil.nivel ?? 1}`} size="small" />
            <Chip label={`Experiencia ${perfil.experiencia ?? 0}`} size="small" />
            <Chip label={`Puntos ${perfil.puntosDesarrollo ?? 0}`} size="small" />
          </Box>
          <Divider />
          <Box sx={styles.profileSkillsScroll}>
            {PROFILE_SKILL_GROUPS.map((group) => (
              <Box key={group.title} sx={styles.profileSkillGroup}>
                <Typography variant="overline" color="secondary.main" sx={styles.profileSkillGroupTitle}>
                  {group.title}
                </Typography>
                {group.skills.map(([field, label]) => (
                  <BarraHabilidadPerfil key={field} label={label} value={perfil[field]} />
                ))}
              </Box>
            ))}
          </Box>
        </>
      ) : (
        <Alert severity="warning" sx={styles.profileMissingAlert}>
          Este personaje todavía no dispone de un perfil de habilidades.
        </Alert>
      )}

      <Box sx={styles.cardFaceActions}>
        <Button variant="outlined" startIcon={<ArrowBackIcon />} onClick={onBack}>
          Historia
        </Button>
        <Button
          variant="contained"
          color="secondary"
          startIcon={<CheckCircleIcon />}
          onClick={onSelect}
          disabled={seleccionado}
        >
          {seleccionado ? 'Seleccionado' : 'Seleccionar'}
        </Button>
      </Box>
    </CardContent>
  );
};

export default PerfilTarjetaPersonaje;
