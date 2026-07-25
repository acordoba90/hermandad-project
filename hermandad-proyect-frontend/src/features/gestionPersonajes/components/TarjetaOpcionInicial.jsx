import { Box, Card, CardActionArea, CardContent, Typography } from '@mui/material';
import ArrowForwardIcon from '@mui/icons-material/ArrowForward';
import { appStyles } from '../../../styles/appStyles';

/**
 * Tarjeta accesible que abre una de las dos ramas del onboarding.
 * @param {{ title: string, description: string, action: string, icon: React.ReactNode, onSelect: Function }} props
 */
const TarjetaOpcionInicial = ({ title, description, action, icon, onSelect }) => {
  const styles = appStyles.characterOnboarding;

  return (
    <Card sx={styles.optionCard}>
      <CardActionArea onClick={onSelect} sx={styles.optionActionArea}>
        <CardContent sx={styles.optionContent}>
          <Box className="onboarding-option-icon" sx={styles.optionIcon}>{icon}</Box>
          <Typography component="h2" variant="h4" sx={styles.optionTitle}>{title}</Typography>
          <Typography color="text.secondary" sx={styles.optionDescription}>{description}</Typography>
          <Box sx={styles.optionCta}>
            <Typography component="span" fontWeight={700}>{action}</Typography>
            <ArrowForwardIcon fontSize="small" />
          </Box>
        </CardContent>
      </CardActionArea>
    </Card>
  );
};

export default TarjetaOpcionInicial;
