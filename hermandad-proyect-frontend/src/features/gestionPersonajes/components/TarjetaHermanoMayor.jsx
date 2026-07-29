import { useState } from 'react';
import { Box, Card } from '@mui/material';
import HistoriaTarjetaPersonaje from './HistoriaTarjetaPersonaje';
import PerfilTarjetaPersonaje from './PerfilTarjetaPersonaje';
import PresentacionTarjetaPersonaje from './PresentacionTarjetaPersonaje';
import { CHARACTER_CARD_FACES } from '../hermanoMayorConstants';
import { appStyles } from '../../../styles/appStyles';

const FACE_ORDER = [
  CHARACTER_CARD_FACES.PRESENTATION,
  CHARACTER_CARD_FACES.STORY,
  CHARACTER_CARD_FACES.PROFILE,
];

/** Tarjeta navegable con presentación, historia y habilidades del Hermano Mayor. */
const TarjetaHermanoMayor = ({ personaje, seleccionado, onSeleccionar }) => {
  const styles = appStyles.characterOnboarding;
  const [activeFace, setActiveFace] = useState(CHARACTER_CARD_FACES.PRESENTATION);
  const activeIndex = FACE_ORDER.indexOf(activeFace);

  const faceStyles = (face) => {
    const faceIndex = FACE_ORDER.indexOf(face);
    if (face === activeFace) return [styles.cardFace, styles.cardFaceActive];
    return [
      styles.cardFace,
      faceIndex < activeIndex ? styles.cardFaceBefore : styles.cardFaceAfter,
    ];
  };

  return (
    <Card
      component="article"
      aria-label={`Hermano Mayor ${personaje.nombre} ${personaje.apellidos || ''}`.trim()}
      sx={[styles.candidateCard, seleccionado && styles.candidateCardSelected]}
    >
      <Box sx={styles.cardStage}>
        <Box
          sx={faceStyles(CHARACTER_CARD_FACES.PRESENTATION)}
          aria-hidden={activeFace !== CHARACTER_CARD_FACES.PRESENTATION}
          inert={activeFace !== CHARACTER_CARD_FACES.PRESENTATION ? '' : undefined}
        >
          <PresentacionTarjetaPersonaje
            personaje={personaje}
            seleccionado={seleccionado}
            onShowStory={() => setActiveFace(CHARACTER_CARD_FACES.STORY)}
            onShowProfile={() => setActiveFace(CHARACTER_CARD_FACES.PROFILE)}
          />
        </Box>

        <Box
          sx={faceStyles(CHARACTER_CARD_FACES.STORY)}
          aria-hidden={activeFace !== CHARACTER_CARD_FACES.STORY}
          inert={activeFace !== CHARACTER_CARD_FACES.STORY ? '' : undefined}
        >
          <HistoriaTarjetaPersonaje
            personaje={personaje}
            onBack={() => setActiveFace(CHARACTER_CARD_FACES.PRESENTATION)}
            onShowProfile={() => setActiveFace(CHARACTER_CARD_FACES.PROFILE)}
          />
        </Box>

        <Box
          sx={faceStyles(CHARACTER_CARD_FACES.PROFILE)}
          aria-hidden={activeFace !== CHARACTER_CARD_FACES.PROFILE}
          inert={activeFace !== CHARACTER_CARD_FACES.PROFILE ? '' : undefined}
        >
          <PerfilTarjetaPersonaje
            personaje={personaje}
            seleccionado={seleccionado}
            onBack={() => setActiveFace(CHARACTER_CARD_FACES.STORY)}
            onSelect={() => onSeleccionar(personaje)}
          />
        </Box>
      </Box>
    </Card>
  );
};

export default TarjetaHermanoMayor;
