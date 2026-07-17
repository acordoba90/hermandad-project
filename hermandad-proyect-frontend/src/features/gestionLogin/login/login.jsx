import { Box } from '@mui/material';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import CustomSnackbar from '../../../components/snackbar/snackbar';
import LoginForm from './loginForm';
import { validarCredenciales } from './loginFunction';
import { appStyles } from '../../../styles/appStyles';
import SolicitudRestauracionDialog from '../solicitudRestauracion/solicitudRestauracionDialog';

const LoginPage = () => {
  const loginPageStyles = appStyles.gestionLogin.login;
  const navigate = useNavigate();

  const [snackbar, setSnackbar] = useState({
    open: false,
    message: '',
    severity: 'info',
  });
  const [isPasswordResetOpen, setIsPasswordResetOpen] = useState(false);

  const handleCloseSnackbar = () => {
    setSnackbar((prev) => ({
      ...prev,
      open: false,
    }));
  };

  const handleSubmit = async (credentials) => {
    const result = await validarCredenciales(credentials);

    setSnackbar({
      open: true,
      message: result.message,
      severity: result.severity,
    });

    if (result.success) {
      setTimeout(() => {
        navigate('/partida');
      }, 1000);
    }
  };

  return (
    <Box sx={loginPageStyles.page}>
      <LoginForm
        onSubmit={handleSubmit}
        onOpenPasswordReset={() => setIsPasswordResetOpen(true)}
      />
      <SolicitudRestauracionDialog
        open={isPasswordResetOpen}
        onClose={() => setIsPasswordResetOpen(false)}
      />
      <CustomSnackbar
        open={snackbar.open}
        message={snackbar.message}
        severity={snackbar.severity}
        onClose={handleCloseSnackbar}
      />
    </Box>
  );
};

export default LoginPage;
