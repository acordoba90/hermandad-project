import { Box } from '@mui/material';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import CustomSnackbar from '../../../components/snackbar/snackbar';
import LoginForm from './loginForm';
import { validarCredenciales } from './loginFunction';
import { loginPageStyles } from './loginStyle';

const LoginPage = () => {
  const navigate = useNavigate();

  const [snackbar, setSnackbar] = useState({
    open: false,
    message: '',
    severity: 'info',
  });

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
      <LoginForm onSubmit={handleSubmit} />
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
