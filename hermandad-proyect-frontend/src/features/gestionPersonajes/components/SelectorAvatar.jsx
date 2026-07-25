import { Alert, Box, ButtonBase, Typography } from '@mui/material';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import CharacterAvatar from './CharacterAvatar';
import { hermanoMayorAvatars } from '../hermanoMayorData';
import { appStyles } from '../../../styles/appStyles';

/** Selector local de apariencia basado en siluetas e iniciales del tema. */
const SelectorAvatar = ({ value, onChange, error }) => {
  const styles = appStyles.characterOnboarding;
  const selectedAvatar = hermanoMayorAvatars.find((avatar) => avatar.id === value);

  return (
    <Box sx={styles.avatarSelectorLayout}>
      <Box sx={styles.avatarPreview}>
        <CharacterAvatar avatarId={value} large />
        <Typography variant="h5">{selectedAvatar?.label || 'Elige una apariencia'}</Typography>
        <Typography color="text.secondary" textAlign="center">
          La imagen es provisional y podrá sustituirse por el editor visual definitivo.
        </Typography>
      </Box>
      <Box>
        <Typography component="h2" variant="h5" sx={styles.sectionTitle}>Apariencias disponibles</Typography>
        <Box sx={styles.avatarGrid} aria-label="Apariencias disponibles">
          {hermanoMayorAvatars.map((avatar) => {
            const selected = avatar.id === value;
            return (
              <ButtonBase
                key={avatar.id}
                aria-label={`Seleccionar ${avatar.label}`}
                aria-pressed={selected}
                onClick={() => onChange(avatar.id)}
                sx={[styles.avatarOption, selected && styles.compactSelection]}
              >
                <CharacterAvatar avatarId={avatar.id} />
                <Typography variant="body2">{avatar.label}</Typography>
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

export default SelectorAvatar;
