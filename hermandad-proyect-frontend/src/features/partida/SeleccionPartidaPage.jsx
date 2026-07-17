import { Box } from '@mui/material';

import SeleccionPartidaForm from './SeleccionPartidaForm';
import { partidasMock } from './seleccionPartidaData';
import { appStyles } from '../../styles/appStyles';
import {
  handleContinuarPartida,
  handleCrearNuevaPartida,
} from './seleccionPartidaFunction';

const SeleccionPartidaPage = () => {
  const seleccionPartidaStyles = appStyles.seleccionPartida;
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
