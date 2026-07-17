import { useEffect, useMemo, useState } from 'react';
import {
  Alert, Avatar, Box, Button, Card, CardContent, CircularProgress, FormControl,
  FormControlLabel, FormLabel, MenuItem, Radio, RadioGroup, Skeleton, Stack,
  Step, StepLabel, Stepper, TextField, Typography,
} from '@mui/material';
import PersonSearchIcon from '@mui/icons-material/PersonSearch';
import WorkspacePremiumIcon from '@mui/icons-material/WorkspacePremium';
import { appStyles } from '../../styles/appStyles';
import { characterOnboardingTexts as texts } from './characterOnboardingTexts';
import {
  createMainCharacter, getRequestErrorMessage, loadCharacterOnboardingCatalogs,
} from './characterOnboardingFunctions';

const initialForm = {
  genero: '', nombre: '', apellidos: '', edad: '', profesion: '', biografia: '',
  motivacion: '', arquetipoPerfilId: '',
};

const CharacterOnboardingPage = () => {
  const styles = appStyles.characterOnboarding;
  const [form, setForm] = useState(initialForm);
  const [archetypes, setArchetypes] = useState([]);
  const [characters, setCharacters] = useState([]);
  const [catalogErrors, setCatalogErrors] = useState({});
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);
  const [query, setQuery] = useState('');
  const [selected, setSelected] = useState(null);
  const [confirmation, setConfirmation] = useState(null);
  const [submitError, setSubmitError] = useState('');

  const applyCatalogs = (result) => {
    setArchetypes(result.archetypes);
    setCharacters(result.characters);
    setCatalogErrors({ archetypes: result.archetypesError, characters: result.charactersError });
    setLoading(false);
  };

  const retryCatalogs = () => {
    setLoading(true);
    loadCharacterOnboardingCatalogs().then(applyCatalogs);
  };

  useEffect(() => {
    let active = true;
    loadCharacterOnboardingCatalogs().then((result) => {
      if (active) applyCatalogs(result);
    });
    return () => { active = false; };
  }, []);

  const filteredCharacters = useMemo(() => {
    const normalized = query.trim().toLocaleLowerCase('es');
    return characters.filter((character) =>
      `${character.nombre ?? ''} ${character.apellidos ?? ''}`.toLocaleLowerCase('es').includes(normalized));
  }, [characters, query]);

  const valid = form.nombre.trim() && form.apellidos.trim() && Number.isInteger(Number(form.edad)) &&
    Number(form.edad) >= 18 && Number(form.edad) <= 100 && form.genero && form.arquetipoPerfilId;

  const updateForm = (event) => setForm((current) => ({ ...current, [event.target.name]: event.target.value }));

  const handleCreate = async (event) => {
    event.preventDefault();
    if (!valid || submitting) return;
    setSubmitting(true);
    setSubmitError('');
    try {
      setConfirmation(await createMainCharacter(form));
    } catch (error) {
      setSubmitError(getRequestErrorMessage(error, 'No se ha podido crear el personaje. Revisa los datos e inténtalo de nuevo.'));
    } finally {
      setSubmitting(false);
    }
  };

  if (confirmation) {
    const archetype = archetypes.find((item) => item.id === (confirmation.arquetipoPerfilId || form.arquetipoPerfilId));
    return <Box sx={styles.page}><Card sx={styles.confirmation}><CardContent sx={styles.panelContent}>
      <WorkspacePremiumIcon sx={styles.confirmationIcon} />
      <Typography variant="h4">Confirma tu personaje principal</Typography>
      <Avatar src={confirmation.urlAvatar || undefined} alt={`Avatar de ${confirmation.nombre}`} sx={styles.largeAvatar} />
      <Typography variant="h5">{confirmation.nombre} {confirmation.apellidos}</Typography>
      <Typography color="text.secondary">{confirmation.genero === 'FEMENINO' ? 'Hermana Mayor' : 'Hermano Mayor'} · {archetype?.nombre || confirmation.perfil?.arquetipoNombre}</Typography>
      <Alert severity="info">El colectivo será Junta de Gobierno y el rol será Hermano Mayor.</Alert>
      <Button variant="outlined" onClick={() => setConfirmation(null)}>Volver y revisar</Button>
    </CardContent></Card></Box>;
  }

  return <Box sx={styles.page}><Box sx={styles.content}>
    <Box sx={styles.header}><WorkspacePremiumIcon sx={styles.brandIcon} /><Box>
      <Typography variant="h5">Hermandad Project</Typography>
      <Typography variant="caption" color="secondary.main">El juego de gestión de cofradías</Typography>
    </Box></Box>
    <Box sx={styles.welcome}>
      <Typography variant="h3">{texts.title}</Typography><Typography color="text.secondary">{texts.description}</Typography>
      <Alert severity="info"><strong>Importante:</strong> Tu personaje principal siempre representará al Hermano Mayor o Hermana Mayor de tu hermandad.</Alert>
    </Box>
    <Stepper activeStep={0} sx={styles.stepper}>{['Crear o seleccionar', 'Confirmar', 'Comenzar tu hermandad'].map((label) => <Step key={label}><StepLabel>{label}</StepLabel></Step>)}</Stepper>
    <Box sx={styles.columns}>
      <Card component="section"><CardContent sx={styles.panelContent}>
        <Typography variant="h4">{texts.creationTitle}</Typography><Typography color="text.secondary">{texts.creationDescription}</Typography>
        <Alert severity="info">El colectivo y el rol se asignarán automáticamente.</Alert>
        <Box component="form" onSubmit={handleCreate} sx={styles.form} noValidate>
          <FormControl required><FormLabel>Género</FormLabel><RadioGroup row name="genero" value={form.genero} onChange={updateForm}>
            <FormControlLabel value="MASCULINO" control={<Radio />} label="Masculino" /><FormControlLabel value="FEMENINO" control={<Radio />} label="Femenino" />
          </RadioGroup></FormControl>
          <Box sx={styles.formGrid}>
            <TextField required label="Nombre" name="nombre" value={form.nombre} onChange={updateForm} inputProps={{ maxLength: 100 }} />
            <TextField required label="Apellidos" name="apellidos" value={form.apellidos} onChange={updateForm} inputProps={{ maxLength: 150 }} />
            <TextField required label="Edad" name="edad" type="number" value={form.edad} onChange={updateForm} inputProps={{ min: 18, max: 100, step: 1 }} />
            <TextField label="Profesión" name="profesion" value={form.profesion} onChange={updateForm} inputProps={{ maxLength: 150 }} />
          </Box>
          <TextField label="Biografía" name="biografia" value={form.biografia} onChange={updateForm} multiline minRows={3} />
          <TextField label="Motivación" name="motivacion" value={form.motivacion} onChange={updateForm} multiline minRows={2} />
          {catalogErrors.archetypes && <Alert severity="error" action={<Button color="inherit" onClick={retryCatalogs}>Reintentar</Button>}>No se han podido cargar los arquetipos.</Alert>}
          <TextField required select label="Arquetipo" name="arquetipoPerfilId" value={form.arquetipoPerfilId} onChange={updateForm} disabled={loading || catalogErrors.archetypes} helperText="Define los atributos iniciales y el estilo de gestión; podrás desarrollar sus habilidades durante la partida.">
            {archetypes.map((item) => <MenuItem key={item.id} value={item.id}><Box><Typography>{item.nombre}</Typography><Typography variant="caption" color="text.secondary">{item.descripcion}</Typography></Box></MenuItem>)}
          </TextField>
          {submitError && <Alert severity="error" aria-live="polite">{submitError}</Alert>}
          <Button type="submit" variant="contained" disabled={!valid || submitting}>{submitting ? <CircularProgress size={24} color="inherit" /> : 'Crear mi Hermano Mayor'}</Button>
        </Box>
      </CardContent></Card>
      <Card component="section"><CardContent sx={styles.panelContent}>
        <Typography variant="h4">{texts.presetTitle}</Typography><Typography color="text.secondary">{texts.presetDescription}</Typography>
        <Alert severity="info"><strong>Personajes preestablecidos del juego:</strong> no pueden modificarse desde esta pantalla.</Alert>
        <TextField fullWidth label="Buscar por nombre o apellidos" value={query} onChange={(event) => setQuery(event.target.value)} InputProps={{ startAdornment: <PersonSearchIcon sx={styles.searchIcon} /> }} />
        {catalogErrors.characters && <Alert severity="error" action={<Button color="inherit" onClick={retryCatalogs}>Reintentar</Button>}>No se han podido cargar los personajes preestablecidos.</Alert>}
        {loading ? <Stack gap={2}>{[1, 2, 3].map((item) => <Skeleton key={item} variant="rounded" height={180} />)}</Stack> :
          filteredCharacters.length === 0 ? <Alert severity="info">No hay personajes preestablecidos disponibles en este momento. Puedes crear tu propio Hermano Mayor para comenzar.</Alert> :
          <Stack gap={2}>{filteredCharacters.map((character) => {
            const isSelected = selected?.id === character.id;
            return <Card key={character.id} variant="outlined" sx={isSelected ? styles.selectedCharacter : styles.characterCard}><CardContent sx={styles.characterContent}>
              <Avatar src={character.urlAvatar || undefined} alt={`Avatar de ${character.nombre}`} sx={styles.avatar} />
              <Box sx={styles.characterInfo}><Typography variant="h6">{character.nombre} {character.apellidos}</Typography><Typography color="secondary.main">{character.genero === 'FEMENINO' ? 'Hermana Mayor' : 'Hermano Mayor'} · Junta de Gobierno</Typography><Typography variant="body2" color="text.secondary">{character.biografia || character.descripcion || 'Personaje preestablecido de Hermandad Project.'}</Typography></Box>
              <Button variant={isSelected ? 'contained' : 'outlined'} aria-pressed={isSelected} onClick={() => setSelected(character)}>{isSelected ? 'Seleccionado' : 'Seleccionar'}</Button>
            </CardContent></Card>;
          })}</Stack>}
        <Button variant="contained" disabled={!selected} onClick={() => setConfirmation(selected)}>Continuar a confirmación</Button>
      </CardContent></Card>
    </Box>
    <Typography sx={styles.footerNote}>Cuando crees una nueva partida, podrás elegir entre tu personaje personalizado y los personajes preestablecidos del juego.</Typography>
  </Box></Box>;
};

export default CharacterOnboardingPage;
