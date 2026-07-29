import { Alert, Box, Button, ButtonBase, CircularProgress, Typography } from '@mui/material';
import AccountBalanceIcon from '@mui/icons-material/AccountBalance';
import AutoAwesomeIcon from '@mui/icons-material/AutoAwesome';
import BalanceIcon from '@mui/icons-material/Balance';
import GroupsIcon from '@mui/icons-material/Groups';
import HistoryEduIcon from '@mui/icons-material/HistoryEdu';
import LightbulbIcon from '@mui/icons-material/Lightbulb';
import SavingsIcon from '@mui/icons-material/Savings';
import ViewListIcon from '@mui/icons-material/ViewList';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import { appStyles } from '../../../styles/appStyles';

const styleIcons = {
  CONCILIADOR: <BalanceIcon />,
  TRADICIONALISTA: <HistoryEduIcon />,
  RENOVADOR: <LightbulbIcon />,
  INFLUYENTE: <GroupsIcon />,
  DEVOTO: <AutoAwesomeIcon />,
  GESTOR: <SavingsIcon />,
  POPULAR: <GroupsIcon />,
  AMBICIOSO: <AccountBalanceIcon />,
  CONFLICTIVO: <AutoAwesomeIcon />,
  DISCRETO: <ViewListIcon />,
  CARISMATICO: <GroupsIcon />,
  ESTRATEGA: <AccountBalanceIcon />,
};

/** Selector de estilo de liderazgo respaldado por el catálogo de arquetipos. */
const SelectorTipoPersonaje = ({
  arquetipos,
  value,
  onChange,
  loading,
  loadError,
  onRetry,
  error,
}) => {
  const styles = appStyles.characterOnboarding;

  if (loading) {
    return (
      <Box sx={styles.catalogFeedback} role="status">
        <CircularProgress color="secondary" />
        <Typography color="text.secondary">Cargando estilos de liderazgo...</Typography>
      </Box>
    );
  }

  if (loadError) {
    return (
      <Alert
        severity="error"
        action={<Button color="inherit" onClick={onRetry}>Reintentar</Button>}
      >
        {loadError}
      </Alert>
    );
  }

  if (arquetipos.length === 0) {
    return <Alert severity="warning">No hay estilos de liderazgo disponibles.</Alert>;
  }

  return (
    <Box sx={styles.personalityLayout}>
      <Box>
        <Typography color="text.secondary" sx={styles.leadershipHelp}>
          El estilo elegido determinará los atributos iniciales del perfil del personaje.
        </Typography>
        <Box sx={styles.personalityGrid}>
          {arquetipos.map((arquetipo) => {
            const selected = value === arquetipo.id;
            return (
              <ButtonBase
                key={arquetipo.id}
                aria-pressed={selected}
                onClick={() => onChange(arquetipo.id)}
                sx={[styles.personalityOption, selected && styles.compactSelection]}
              >
                <Box sx={styles.personalityIcon}>
                  {styleIcons[arquetipo.codigo] || <AccountBalanceIcon />}
                </Box>
                <Box sx={styles.personalityCopy}>
                  <Typography fontWeight={700}>{arquetipo.nombre}</Typography>
                  <Typography variant="body2" color="text.secondary">
                    {arquetipo.descripcion}
                  </Typography>
                </Box>
                {selected && <CheckCircleIcon sx={styles.optionCheck} />}
              </ButtonBase>
            );
          })}
        </Box>
        {error && <Alert severity="error" sx={styles.inlineAlert}>{error}</Alert>}
      </Box>
    </Box>
  );
};

export default SelectorTipoPersonaje;
