import { Box } from '@mui/material';

import SeleccionPartidaForm from './SeleccionPartidaForm';
import { partidasMock } from './seleccionPartidaData';
import { seleccionPartidaStyles } from './seleccionPartidaStyles';
import {
  handleContinuarPartida,
  handleCrearNuevaPartida,
} from './seleccionPartidaFunction';

const SeleccionPartidaPage = () => {
  return (
    <Box sx={seleccionPartidaStyles.page}>
      <SeleccionPartidaForm
        partidas={partidasMock}
        onContinuarPartida={handleContinuarPartida}
        onCrearNuevaPartida={handleCrearNuevaPartida}
      />
    </Box>
  );
};

export default SeleccionPartidaPage;