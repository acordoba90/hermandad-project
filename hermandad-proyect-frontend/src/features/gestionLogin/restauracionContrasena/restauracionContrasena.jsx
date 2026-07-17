import { Alert, Box, Button, Card, CardContent, Divider, Typography } from '@mui/material';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { useEffect, useRef, useState } from 'react';
import RestauracionContrasenaForm from './restauracionContrasenaForm';
import { restauracionContrasenaData } from './restauracionContrasenaData';
import { isValidResetToken } from './restauracionContrasenaFunction';
import { appStyles } from '../../../styles/appStyles';

/** Pantalla pública que conserva el token únicamente en memoria durante el envío. */
const RestauracionContrasena = () => {
  const restauracionContrasenaStyles = appStyles.gestionLogin.passwordReset;
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const token = searchParams.get('token');
  const hasToken = isValidResetToken(token);
  const [isCompleted, setIsCompleted] = useState(false);
  const redirectTimer = useRef(null);

  useEffect(
    () => () => {
      if (redirectTimer.current) clearTimeout(redirectTimer.current);
    },
    [],
  );

  const handleSuccess = () => {
    setIsCompleted(true);
    navigate(restauracionContrasenaData.routes.current, { replace: true });
    redirectTimer.current = setTimeout(() => {
      navigate(restauracionContrasenaData.routes.login, { replace: true });
    }, restauracionContrasenaData.successRedirectDelay);
  };

  return (
    <Box sx={restauracionContrasenaStyles.page}>
      <Card sx={restauracionContrasenaStyles.card}>
        <CardContent sx={restauracionContrasenaStyles.cardContent}>
          <Box sx={restauracionContrasenaStyles.header}>
            <Box component="img" src="/escudo_hermandad_project.svg" alt="Escudo de Hermandad Project" sx={restauracionContrasenaStyles.logoIcon} />
            <Typography variant="h4" sx={restauracionContrasenaStyles.title}>{restauracionContrasenaData.title}</Typography>
            <Typography variant="body2" color="text.secondary" sx={restauracionContrasenaStyles.description}>
              {restauracionContrasenaData.description}
            </Typography>
          </Box>
          <Divider />
          {hasToken || isCompleted ? (
            <RestauracionContrasenaForm token={token} onSuccess={handleSuccess} />
          ) : (
            <>
              <Alert severity="error" role="alert" sx={restauracionContrasenaStyles.alert}>
                {restauracionContrasenaData.messages.missingToken}
              </Alert>
              <Button fullWidth variant="contained" sx={restauracionContrasenaStyles.submitButton} onClick={() => navigate(restauracionContrasenaData.routes.login, { replace: true })}>
                {restauracionContrasenaData.buttons.backToLogin}
              </Button>
            </>
          )}
        </CardContent>
      </Card>
    </Box>
  );
};

export default RestauracionContrasena;
