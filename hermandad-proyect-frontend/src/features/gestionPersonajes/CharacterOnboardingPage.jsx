import { useState } from 'react';
import { Box, Typography } from '@mui/material';
import GroupsIcon from '@mui/icons-material/Groups';
import PersonAddAltIcon from '@mui/icons-material/PersonAddAlt';
import CustomSnackbar from '../../components/snackbar/snackbar';
import TarjetaOpcionInicial from './components/TarjetaOpcionInicial';
import SeleccionHermanoMayor from './components/SeleccionHermanoMayor';
import CreacionHermanoMayor from './components/CreacionHermanoMayor';
import { ONBOARDING_VIEWS } from './hermanoMayorConstants';
import { characterOnboardingTexts as texts } from './characterOnboardingTexts';
import { appStyles } from '../../styles/appStyles';
import { useAppSession } from '../../context/useAppSession';

/** Coordina la navegación interna del primer acceso sin persistir su estado. */
const CharacterOnboardingPage = () => {
  const styles = appStyles.characterOnboarding;
  const { establecerPersonajeActivo } = useAppSession();
  const [currentView, setCurrentView] = useState(ONBOARDING_VIEWS.INITIAL);
  const [selectedCharacter, setSelectedCharacter] = useState(null);
  const [snackbar, setSnackbar] = useState({ open: false, message: '', severity: 'info' });

  const showReadyMessage = (message) => {
    setSnackbar({ open: true, message, severity: 'info' });
  };

  const goToInitialView = () => {
    setSelectedCharacter(null);
    setCurrentView(ONBOARDING_VIEWS.INITIAL);
  };

  const confirmarPersonaje = (personaje) => {
    establecerPersonajeActivo(personaje);
  };

  const renderCurrentView = () => {
    if (currentView === ONBOARDING_VIEWS.SELECTION) {
      return (
        <SeleccionHermanoMayor
          seleccionado={selectedCharacter}
          onSeleccionar={setSelectedCharacter}
          onConfirmar={confirmarPersonaje}
          onVolver={goToInitialView}
          onNotify={showReadyMessage}
        />
      );
    }

    if (currentView === ONBOARDING_VIEWS.CREATION) {
      return (
        <CreacionHermanoMayor
          onConfirmar={confirmarPersonaje}
          onVolver={goToInitialView}
          onNotify={showReadyMessage}
        />
      );
    }

    return (
      <Box sx={styles.initialView}>
        <Box sx={styles.heroCopy}>
          <Typography variant="overline" sx={styles.eyebrow}>{texts.initial.eyebrow}</Typography>
          <Typography component="h1" variant="h2" sx={styles.heroTitle}>{texts.initial.title}</Typography>
          <Typography variant="h6" color="text.primary">{texts.initial.subtitle}</Typography>
          <Typography color="text.secondary" sx={styles.heroDescription}>{texts.initial.description}</Typography>
        </Box>
        <Box sx={styles.optionsGrid}>
          <TarjetaOpcionInicial
            {...texts.initial.options.select}
            icon={<GroupsIcon sx={styles.optionIconGlyph} />}
            onSelect={() => setCurrentView(ONBOARDING_VIEWS.SELECTION)}
          />
          <TarjetaOpcionInicial
            {...texts.initial.options.create}
            icon={<PersonAddAltIcon sx={styles.optionIconGlyph} />}
            onSelect={() => setCurrentView(ONBOARDING_VIEWS.CREATION)}
          />
        </Box>
        <Box sx={styles.footerMessage}>
          <GroupsIcon fontSize="small" />
          <Typography color="text.secondary">{texts.initial.footer}</Typography>
        </Box>
      </Box>
    );
  };

  return (
    <Box sx={styles.page}>
      <Box sx={styles.ambientLightOne} aria-hidden="true" />
      <Box sx={styles.ambientLightTwo} aria-hidden="true" />
      <Box sx={styles.content}>
        <Box component="header" sx={styles.brandHeader}>
          <Box
            component="img"
            src="/escudo_hermandad_project.svg"
            alt="Escudo de Hermandad Project"
            sx={styles.brandLogo}
          />
          <Box>
            <Typography variant="h5" sx={styles.brandName}>{texts.brand.name}</Typography>
            <Typography variant="caption" sx={styles.brandClaim}>{texts.brand.claim}</Typography>
          </Box>
        </Box>
        <Box component="main">{renderCurrentView()}</Box>
      </Box>
      <CustomSnackbar
        open={snackbar.open}
        message={snackbar.message}
        severity={snackbar.severity}
        onClose={() => setSnackbar((current) => ({ ...current, open: false }))}
      />
    </Box>
  );
};

export default CharacterOnboardingPage;
