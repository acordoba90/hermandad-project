import { Alert, Box, ButtonBase, Chip, Typography } from '@mui/material';
import AccountBalanceIcon from '@mui/icons-material/AccountBalance';
import AutoAwesomeIcon from '@mui/icons-material/AutoAwesome';
import BalanceIcon from '@mui/icons-material/Balance';
import GroupsIcon from '@mui/icons-material/Groups';
import HistoryEduIcon from '@mui/icons-material/HistoryEdu';
import LightbulbIcon from '@mui/icons-material/Lightbulb';
import SavingsIcon from '@mui/icons-material/Savings';
import ViewListIcon from '@mui/icons-material/ViewList';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import { hermanoMayorTraits, hermanoMayorTypes } from '../hermanoMayorData';
import { MAX_TRAITS } from '../hermanoMayorConstants';
import { appStyles } from '../../../styles/appStyles';

const typeIcons = {
  history: <HistoryEduIcon />,
  order: <ViewListIcon />,
  agreement: <BalanceIcon />,
  renew: <LightbulbIcon />,
  faith: <AutoAwesomeIcon />,
  management: <SavingsIcon />,
  neighborhood: <GroupsIcon />,
  future: <AccountBalanceIcon />,
};

/** Selector del arquetipo principal y de un máximo de tres rasgos personales. */
const SelectorTipoPersonaje = ({ tipo, rasgos, onTipoChange, onRasgoToggle, error }) => {
  const styles = appStyles.characterOnboarding;
  const remaining = MAX_TRAITS - rasgos.length;

  return (
    <Box sx={styles.personalityLayout}>
      <Box>
        <Typography component="h2" variant="h5" sx={styles.sectionTitle}>Estilo de liderazgo</Typography>
        <Box sx={styles.personalityGrid}>
          {hermanoMayorTypes.map((option) => {
            const selected = tipo === option.id;
            return (
              <ButtonBase
                key={option.id}
                aria-pressed={selected}
                onClick={() => onTipoChange(option.id)}
                sx={[styles.personalityOption, selected && styles.compactSelection]}
              >
                <Box sx={styles.personalityIcon}>{typeIcons[option.icon]}</Box>
                <Box sx={styles.personalityCopy}>
                  <Typography fontWeight={700}>{option.id}</Typography>
                  <Typography variant="body2" color="text.secondary">{option.description}</Typography>
                </Box>
                {selected && <CheckCircleIcon sx={styles.optionCheck} />}
              </ButtonBase>
            );
          })}
        </Box>
        {error && <Alert severity="error" sx={styles.inlineAlert}>{error}</Alert>}
      </Box>
      <Box>
        <Box sx={styles.traitHeading}>
          <Box>
            <Typography component="h2" variant="h5">Rasgos personales</Typography>
            <Typography color="text.secondary">Escoge hasta tres cualidades que definan su carácter.</Typography>
          </Box>
          <Chip
            label={remaining > 0 ? `${remaining} disponibles` : 'Selección completa'}
            color={remaining > 0 ? 'default' : 'secondary'}
          />
        </Box>
        <Box sx={styles.traits}>
          {hermanoMayorTraits.map((trait) => {
            const selected = rasgos.includes(trait);
            const disabled = !selected && remaining === 0;
            return (
              <Chip
                key={trait}
                label={trait}
                clickable
                disabled={disabled}
                color={selected ? 'secondary' : 'default'}
                variant={selected ? 'filled' : 'outlined'}
                aria-pressed={selected}
                onClick={() => onRasgoToggle(trait)}
              />
            );
          })}
        </Box>
      </Box>
    </Box>
  );
};

export default SelectorTipoPersonaje;
