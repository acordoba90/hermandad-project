import { Box } from '@mui/material';

import SeleccionPartidaForm from './SeleccionPartidaForm';
import { partidasMock } from './seleccionPartidaData';
import { appStyles } from '../../styles/appStyles';
import { handleCrearNuevaPartida } from './seleccionPartidaFunction';
import { useAppSession } from '../../context/useAppSession';

const SeleccionPartidaPage = () => {
  const seleccionPartidaStyles = appStyles.seleccionPartida;
  const { establecerPartidaActiva, usuario } = useAppSession();
  return (
    <Box sx={seleccionPartidaStyles.page}>
      <SeleccionPartidaForm
        partidas={partidasMock}
        usuarioNombre={usuario?.nombreUsuario || 'Usuario'}
        onContinuarPartida={establecerPartidaActiva}
        onCrearNuevaPartida={handleCrearNuevaPartida}
      />
    </Box>
  );
};

export default SeleccionPartidaPage;
