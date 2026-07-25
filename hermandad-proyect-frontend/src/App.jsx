import { Routes, Route } from 'react-router-dom';

import LoginPage from './features/gestionLogin/login/login';
import AltaUsuario from './features/gestionLogin/altaUsuario/altaUsuario';
import SeleccionPartidaPage from './features/partida/SeleccionPartidaPage';
import AccountActivation from './features/gestionLogin/accountActivation/accountActivation';
import RestauracionContrasena from './features/gestionLogin/restauracionContrasena/restauracionContrasena';
import CharacterOnboardingPage from './features/gestionPersonajes/CharacterOnboardingPage';

function App() {
  return (
    <Routes>
      <Route path="/" element={<LoginPage />} />
      <Route path="/alta-usuario" element={<AltaUsuario />} />
      <Route path="/partida" element={<SeleccionPartidaPage />} />
      <Route path="/activar-cuenta" element={<AccountActivation />} />
      <Route path="/restaurar-contrasena" element={<RestauracionContrasena />} />
      <Route path="/personaje" element={<CharacterOnboardingPage />} />
      <Route path="/crear-personaje" element={<CharacterOnboardingPage />} />
    </Routes>
  );
}

export default App;
