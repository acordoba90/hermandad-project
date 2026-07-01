import { Routes, Route } from 'react-router-dom';

import LoginPage from './features/gestionLogin/login/login';
import AltaUsuario from './features/gestionLogin/altaUsuario/altaUsuario';
import SeleccionPartidaPage from './features/partida/SeleccionPartidaPage';

function App() {
  return (
    <Routes>
      <Route path="/" element={<LoginPage />} />
      <Route path="/alta-usuario" element={<AltaUsuario />} />
      <Route path="/partida" element={<SeleccionPartidaPage/>} />
    </Routes>
  );
}

export default App;