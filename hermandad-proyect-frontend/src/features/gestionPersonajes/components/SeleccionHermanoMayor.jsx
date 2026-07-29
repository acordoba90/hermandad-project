import { useEffect, useMemo, useState } from 'react';
import { Alert, Box, Button, Card, CardContent, CircularProgress, Typography } from '@mui/material';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import HowToRegIcon from '@mui/icons-material/HowToReg';
import TarjetaHermanoMayor from './TarjetaHermanoMayor';
import ResumenHermanoMayor from './ResumenHermanoMayor';
import { characterOnboardingTexts as texts } from '../characterOnboardingTexts';
import { appStyles } from '../../../styles/appStyles';
import { useAppSession } from '../../../context/useAppSession';
import { PREDEFINED_AVATAR_TONES } from '../hermanoMayorConstants';

/** Pantalla de selección local de un candidato predefinido. */
const SeleccionHermanoMayor = ({
  seleccionado,
  onSeleccionar,
  onConfirmar,
  onVolver,
  onNotify,
}) => {
  const styles = appStyles.characterOnboarding;
  const { catalogos, cargarPersonajesPredefinidos } = useAppSession();
  const [loading, setLoading] = useState(catalogos.personajesPredefinidos === null);
  const [loadError, setLoadError] = useState('');
  const personajesConColor = useMemo(
    () => (catalogos.personajesPredefinidos || []).map((personaje, index) => ({
      ...personaje,
      avatarTone: PREDEFINED_AVATAR_TONES[index % PREDEFINED_AVATAR_TONES.length],
    })),
    [catalogos.personajesPredefinidos],
  );

  useEffect(() => {
    let active = true;
    cargarPersonajesPredefinidos()
      .catch(() => {
        if (active) setLoadError(texts.selection.loadError);
      })
      .finally(() => {
        if (active) setLoading(false);
      });
    return () => {
      active = false;
    };
  }, [cargarPersonajesPredefinidos]);

  const retryLoad = () => {
    setLoading(true);
    setLoadError('');
    cargarPersonajesPredefinidos({ forzar: true })
      .catch(() => setLoadError(texts.selection.loadError))
      .finally(() => setLoading(false));
  };

  const handleConfirmarSeleccion = () => {
    if (!seleccionado) return;
    onConfirmar(seleccionado);
    onNotify(texts.selection.ready);
  };

  const renderCandidates = () => {
    if (loading) {
      return (
        <Box sx={styles.catalogFeedback} role="status">
          <CircularProgress color="secondary" />
          <Typography color="text.secondary">Cargando Hermanos Mayores...</Typography>
        </Box>
      );
    }

    if (loadError) {
      return (
        <Alert
          severity="error"
          action={<Button color="inherit" onClick={retryLoad}>Reintentar</Button>}
        >
          {loadError}
        </Alert>
      );
    }

    if (personajesConColor.length === 0) {
      return <Alert severity="warning">No hay Hermanos Mayores predefinidos disponibles.</Alert>;
    }

    return (
      <Box sx={styles.candidatesGrid}>
        {personajesConColor.map((personaje) => (
          <TarjetaHermanoMayor
            key={personaje.id}
            personaje={personaje}
            seleccionado={seleccionado?.id === personaje.id}
            onSeleccionar={onSeleccionar}
          />
        ))}
      </Box>
    );
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
      {renderCandidates()}
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
