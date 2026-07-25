import { useMemo, useState } from 'react';
import {
  Alert, Box, Button, Card, CardContent, Step, StepLabel, Stepper, TextField,
  Typography, useMediaQuery,
} from '@mui/material';
import { useTheme } from '@mui/material/styles';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import ArrowForwardIcon from '@mui/icons-material/ArrowForward';
import SaveOutlinedIcon from '@mui/icons-material/SaveOutlined';
import SelectorAvatar from './SelectorAvatar';
import SelectorTipoPersonaje from './SelectorTipoPersonaje';
import ResumenHermanoMayor from './ResumenHermanoMayor';
import {
  CHARACTER_FIELD_LIMITS, CREATION_STEPS, INITIAL_CHARACTER_FORM, MAX_TRAITS,
} from '../hermanoMayorConstants';
import {
  buildCustomCharacter, validateCustomCharacter, validateIdentity,
} from '../characterOnboardingFunctions';
import { characterOnboardingTexts as texts } from '../characterOnboardingTexts';
import { appStyles } from '../../../styles/appStyles';

/** Formulario progresivo que prepara un Hermano Mayor personalizado solo en memoria. */
const CreacionHermanoMayor = ({ onConfirmar, onVolver, onNotify }) => {
  const styles = appStyles.characterOnboarding;
  const theme = useTheme();
  const mobile = useMediaQuery(theme.breakpoints.down('sm'));
  const [activeStep, setActiveStep] = useState(CREATION_STEPS.IDENTITY);
  const [form, setForm] = useState(INITIAL_CHARACTER_FORM);
  const [errors, setErrors] = useState({});
  const personaje = useMemo(() => buildCustomCharacter(form), [form]);

  const updateField = (event) => {
    const { name, value } = event.target;
    setForm((current) => ({ ...current, [name]: value }));
    setErrors((current) => ({ ...current, [name]: undefined }));
  };

  const updateValue = (name, value) => {
    setForm((current) => ({ ...current, [name]: value }));
    setErrors((current) => ({ ...current, [name]: undefined }));
  };

  const toggleTrait = (trait) => {
    setForm((current) => {
      const selected = current.rasgos.includes(trait);
      if (!selected && current.rasgos.length >= MAX_TRAITS) return current;
      return {
        ...current,
        rasgos: selected
          ? current.rasgos.filter((item) => item !== trait)
          : [...current.rasgos, trait],
      };
    });
  };

  const validateCurrentStep = () => {
    let nextErrors = {};
    if (activeStep === CREATION_STEPS.IDENTITY) nextErrors = validateIdentity(form);
    if (activeStep === CREATION_STEPS.APPEARANCE && !form.avatarId) {
      nextErrors.avatarId = 'Selecciona una apariencia.';
    }
    if (activeStep === CREATION_STEPS.PERSONALITY && !form.tipoPersonaje) {
      nextErrors.tipoPersonaje = 'Selecciona un tipo de liderazgo.';
    }
    setErrors(nextErrors);
    return Object.keys(nextErrors).length === 0;
  };

  const handleNext = () => {
    if (!validateCurrentStep()) return;
    setActiveStep((current) => Math.min(current + 1, CREATION_STEPS.REVIEW));
  };

  const handlePrevious = () => {
    setErrors({});
    setActiveStep((current) => Math.max(current - 1, CREATION_STEPS.IDENTITY));
  };

  const handleCrearPersonajePersonalizado = () => {
    const validationErrors = validateCustomCharacter(form);
    if (Object.keys(validationErrors).length > 0) {
      setErrors(validationErrors);
      setActiveStep(CREATION_STEPS.IDENTITY);
      return;
    }
    onConfirmar(personaje);
    // TODO: crear el personaje mediante gestion-personajes cuando se cierre el contrato REST definitivo.
    onNotify(texts.creation.ready);
  };

  const renderStep = () => {
    if (activeStep === CREATION_STEPS.IDENTITY) {
      return (
        <Box sx={styles.formGrid}>
          <TextField
            required
            autoFocus
            name="nombre"
            label="Nombre"
            value={form.nombre}
            onChange={updateField}
            error={Boolean(errors.nombre)}
            helperText={errors.nombre || 'El nombre con el que será conocido en la hermandad.'}
            inputProps={{ maxLength: CHARACTER_FIELD_LIMITS.nombre }}
          />
          <TextField
            required
            name="apellidos"
            label="Apellidos"
            value={form.apellidos}
            onChange={updateField}
            error={Boolean(errors.apellidos)}
            helperText={errors.apellidos || 'Completa su identidad pública.'}
            inputProps={{ maxLength: CHARACTER_FIELD_LIMITS.apellidos }}
          />
          <TextField
            required
            name="edad"
            label="Edad"
            type="number"
            value={form.edad}
            onChange={updateField}
            error={Boolean(errors.edad)}
            helperText={errors.edad || 'La edad influirá en la experiencia inicial y en la percepción de los hermanos.'}
            inputProps={{ min: 18, max: 90, step: 1 }}
          />
          <TextField
            required
            name="profesion"
            label="Profesión"
            value={form.profesion}
            onChange={updateField}
            error={Boolean(errors.profesion)}
            helperText={errors.profesion || 'Su trayectoria profesional aportará contexto a su liderazgo.'}
            inputProps={{ maxLength: CHARACTER_FIELD_LIMITS.profesion }}
          />
        </Box>
      );
    }

    if (activeStep === CREATION_STEPS.APPEARANCE) {
      return (
        <SelectorAvatar
          value={form.avatarId}
          onChange={(value) => updateValue('avatarId', value)}
          error={errors.avatarId}
        />
      );
    }

    if (activeStep === CREATION_STEPS.BACKGROUND) {
      return (
        <Box sx={styles.formStack}>
          <TextField
            name="biografia"
            label="Biografía"
            value={form.biografia}
            onChange={updateField}
            multiline
            minRows={5}
            helperText="Describe brevemente su relación con la hermandad, su experiencia y su recorrido personal."
            inputProps={{ maxLength: CHARACTER_FIELD_LIMITS.biografia }}
          />
          <Typography variant="caption" sx={styles.characterCounter}>
            {form.biografia.length}/{CHARACTER_FIELD_LIMITS.biografia}
          </Typography>
          <TextField
            name="motivacion"
            label="Motivación"
            value={form.motivacion}
            onChange={updateField}
            multiline
            minRows={4}
            helperText="Explica qué le impulsa a asumir la responsabilidad de Hermano Mayor."
            inputProps={{ maxLength: CHARACTER_FIELD_LIMITS.motivacion }}
          />
          <Typography variant="caption" sx={styles.characterCounter}>
            {form.motivacion.length}/{CHARACTER_FIELD_LIMITS.motivacion}
          </Typography>
        </Box>
      );
    }

    if (activeStep === CREATION_STEPS.PERSONALITY) {
      return (
        <SelectorTipoPersonaje
          tipo={form.tipoPersonaje}
          rasgos={form.rasgos}
          onTipoChange={(value) => updateValue('tipoPersonaje', value)}
          onRasgoToggle={toggleTrait}
          error={errors.tipoPersonaje}
        />
      );
    }

    return (
      <Box sx={styles.reviewPanel}>
        <Alert severity="info">
          Revisa la identidad antes de dejarla preparada para su futura creación en el backend.
        </Alert>
        <ResumenHermanoMayor personaje={personaje} />
      </Box>
    );
  };

  return (
    <Box sx={styles.view}>
      <Button startIcon={<ArrowBackIcon />} onClick={onVolver} sx={styles.backButton}>
        {texts.common.back}
      </Button>
      <Box sx={styles.viewHeader}>
        <Typography component="h1" variant="h3">{texts.creation.title}</Typography>
        <Typography color="text.secondary">{texts.creation.subtitle}</Typography>
      </Box>
      <Card sx={styles.creationCard}>
        <CardContent sx={styles.creationContent}>
          <Stepper
            activeStep={activeStep}
            alternativeLabel={!mobile}
            orientation={mobile ? 'vertical' : 'horizontal'}
            sx={styles.creationStepper}
          >
            {texts.creation.steps.map((label) => (
              <Step key={label}><StepLabel>{label}</StepLabel></Step>
            ))}
          </Stepper>
          <Box component="section" aria-labelledby={`creation-step-${activeStep}`} sx={styles.stepPanel}>
            <Typography id={`creation-step-${activeStep}`} component="h2" variant="h4" sx={styles.stepTitle}>
              {texts.creation.steps[activeStep]}
            </Typography>
            {renderStep()}
          </Box>
          <Box sx={styles.stepActions}>
            {activeStep > CREATION_STEPS.IDENTITY && (
              <Button variant="outlined" onClick={handlePrevious}>{texts.common.previous}</Button>
            )}
            {activeStep < CREATION_STEPS.REVIEW ? (
              <Button variant="contained" endIcon={<ArrowForwardIcon />} onClick={handleNext}>
                {texts.common.next}
              </Button>
            ) : (
              <Box sx={styles.reviewActions}>
                <Button variant="outlined" onClick={() => setActiveStep(CREATION_STEPS.IDENTITY)}>
                  {texts.common.edit}
                </Button>
                <Button
                  variant="contained"
                  color="secondary"
                  startIcon={<SaveOutlinedIcon />}
                  onClick={handleCrearPersonajePersonalizado}
                >
                  {texts.common.create}
                </Button>
              </Box>
            )}
          </Box>
        </CardContent>
      </Card>
    </Box>
  );
};

export default CreacionHermanoMayor;
