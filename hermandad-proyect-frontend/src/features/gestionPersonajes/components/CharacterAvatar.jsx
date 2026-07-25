import { Avatar } from '@mui/material';
import { appStyles } from '../../../styles/appStyles';
import { hermanoMayorAvatars } from '../hermanoMayorData';

/** Representación local del retrato mientras no exista un catálogo de imágenes. */
const CharacterAvatar = ({ avatarId, nombre = '', apellidos = '', large = false }) => {
  const styles = appStyles.characterOnboarding;
  const avatar = hermanoMayorAvatars.find((item) => item.id === avatarId);
  const initials = `${nombre.charAt(0)}${apellidos.charAt(0)}`.toUpperCase() || avatar?.initials || 'HM';

  return (
    <Avatar
      aria-label={avatar ? `Apariencia ${avatar.label}` : 'Apariencia del Hermano Mayor'}
      sx={[
        styles.characterAvatar,
        styles.avatarTones[avatar?.tone || 'wine'],
        large && styles.characterAvatarLarge,
      ]}
    >
      {initials}
    </Avatar>
  );
};

export default CharacterAvatar;
