import { Avatar } from '@mui/material';
import { appStyles } from '../../../styles/appStyles';
import { hermanoMayorAvatars } from '../hermanoMayorData';

/** Representación local del retrato mientras no exista un catálogo de imágenes. */
const CharacterAvatar = ({
  avatarId,
  urlAvatar,
  tone,
  nombre = '',
  apellidos = '',
  large = false,
}) => {
  const styles = appStyles.characterOnboarding;
  const avatar = hermanoMayorAvatars.find((item) => item.id === avatarId);
  const initials = `${nombre.charAt(0)}${apellidos.charAt(0)}`.toUpperCase() || avatar?.initials || 'HM';

  return (
    <Avatar
      src={urlAvatar || undefined}
      alt={urlAvatar ? `Retrato de ${nombre} ${apellidos}`.trim() : undefined}
      aria-label={!urlAvatar ? (avatar ? `Apariencia ${avatar.label}` : 'Apariencia del Hermano Mayor') : undefined}
      sx={[
        styles.characterAvatar,
        styles.avatarTones[tone || avatar?.tone || 'wine'],
        large && styles.characterAvatarLarge,
      ]}
    >
      {initials}
    </Avatar>
  );
};

export default CharacterAvatar;
