import { Alert, Box, Button, Card, CardContent, Typography } from '@mui/material';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import HowToRegIcon from '@mui/icons-material/HowToReg';
import TarjetaHermanoMayor from './TarjetaHermanoMayor';
import ResumenHermanoMayor from './ResumenHermanoMayor';
import { hermanoMayorCandidates } from '../hermanoMayorData';
import { characterOnboardingTexts as texts } from '../characterOnboardingTexts';
import { appStyles } from '../../../styles/appStyles';

/** Pantalla de selección local de un candidato predefinido. */
const SeleccionHermanoMayor = ({
  seleccionado,
  onSeleccionar,
  onConfirmar,
  onVolver,
  onNotify,
}) => {
  const styles = appStyles.characterOnboarding;

  const handleConfirmarSeleccion = () => {
    if (!seleccionado) return;
    onConfirmar(seleccionado);
    // TODO: enviar la selección al servicio gestion-personajes cuando exista el contrato de asignación.
    onNotify(texts.selection.ready);
  };

  return (
    <Box sx={styles.view}>
      <Button startIcon={<ArrowBackIcon />} onClick={onVolver} sx={styles.backButton}>
        {texts.common.back}
      </Button>
      <Box sx={styles.viewHeader}>
        <Typography component="h1" variant="h3">{texts.selection.title}</Typography>
        <Typography color="text.secondary">{texts.selection.subtitle}</Typography>
      </Box>
      <Box sx={styles.candidatesGrid}>
        {hermanoMayorCandidates.map((personaje) => (
          <TarjetaHermanoMayor
            key={personaje.id}
            personaje={personaje}
            seleccionado={seleccionado?.id === personaje.id}
            onSeleccionar={onSeleccionar}
          />
        ))}
      </Box>
      <Card component="section" sx={styles.selectionSummaryCard}>
        <CardContent sx={styles.selectionSummaryContent}>
          <Typography component="h2" variant="h4">{texts.selection.summaryTitle}</Typography>
          {seleccionado ? (
            <ResumenHermanoMayor personaje={seleccionado} />
          ) : (
            <Alert severity="info">{texts.selection.summaryEmpty}</Alert>
          )}
          <Button
            variant="contained"
            color="secondary"
            size="large"
            startIcon={<HowToRegIcon />}
            disabled={!seleccionado}
            onClick={handleConfirmarSeleccion}
            sx={styles.confirmButton}
          >
            {texts.selection.confirm}
          </Button>
        </CardContent>
      </Card>
    </Box>
  );
};

export default SeleccionHermanoMayor;
