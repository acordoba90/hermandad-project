import { useEffect, useRef, useState } from 'react';
import {
  Box,
  Button,
  Card,
  CardContent,
  CircularProgress,
  Divider,
  Link,
  Typography,
} from '@mui/material';
import { Link as RouterLink, useNavigate, useSearchParams } from 'react-router-dom';

import CustomSnackbar from '../../../components/snackbar/snackbar';
import { accountActivationData } from './accountActivationData';
import {
  activateAccount,
  isValidActivationToken,
} from './accountActivationFunction';
import { accountActivationStyles } from './accountActivationStyle';

/**
 * Pantalla pública que obtiene el token de `?token=` y permite activar la cuenta
 * únicamente tras una acción explícita del usuario.
 *
 * @returns {JSX.Element} Tarjeta de activación centrada y responsive.
 */
const AccountActivation = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const token = searchParams.get('token');
  const hasValidToken = isValidActivationToken(token);
  const requestInProgress = useRef(false);
  const redirectTimer = useRef(null);

  const [isLoading, setIsLoading] = useState(false);
  const [isActivated, setIsActivated] = useState(false);
  const [statusMessage, setStatusMessage] = useState(
    hasValidToken ? '' : accountActivationData.messages.invalidLink,
  );
  const [snackbar, setSnackbar] = useState({
    open: false,
    message: '',
    severity: 'info',
  });

  useEffect(
    () => () => {
      if (redirectTimer.current) {
        clearTimeout(redirectTimer.current);
      }
    },
    [],
  );

  const handleCloseSnackbar = () => {
    setSnackbar((previous) => ({ ...previous, open: false }));
  };

  /**
   * Valida el token, bloquea pulsaciones concurrentes y procesa el resultado
   * normalizado. La redirección se programa solo después de una activación válida.
   */
  const handleActivation = async () => {
    if (!hasValidToken || requestInProgress.current || isActivated) {
      return;
    }

    requestInProgress.current = true;
    setIsLoading(true);
    setStatusMessage('');

    const result = await activateAccount(token);

    requestInProgress.current = false;
    setIsLoading(false);
    setStatusMessage(result.message);
    setSnackbar({
      open: true,
      message: result.message,
      severity: result.severity,
    });

    if (result.success) {
      setIsActivated(true);
      redirectTimer.current = setTimeout(() => {
        navigate(accountActivationData.routes.success);
      }, accountActivationData.redirectDelay);
    }
  };

  return (
    <Box sx={accountActivationStyles.page}>
      <Card sx={accountActivationStyles.card}>
        <CardContent sx={accountActivationStyles.cardContent}>
          <Box sx={accountActivationStyles.header}>
            <Box
              component="img"
              src="/escudo_hermandad_project.svg"
              alt={accountActivationData.accessibility.activationIcon}
              sx={accountActivationStyles.logoIcon}
            />

            <Typography variant="h4" sx={accountActivationStyles.title}>
              {accountActivationData.title}
            </Typography>

            <Typography
              variant="body2"
              color="text.secondary"
              sx={accountActivationStyles.description}
            >
              {accountActivationData.description}
            </Typography>
          </Box>

          <Divider />

          <Box sx={accountActivationStyles.content}>
            {(statusMessage || isLoading) && (
              <Box
                role="status"
                aria-live="polite"
                sx={accountActivationStyles.statusMessage}
              >
                {isLoading ? (
                  <Typography variant="body2">
                    {accountActivationData.buttons.activating}
                  </Typography>
                ) : (
                  <Typography
                    variant="body2"
                    sx={
                      isActivated
                        ? accountActivationStyles.successMessage
                        : accountActivationStyles.errorMessage
                    }
                  >
                    {statusMessage}
                  </Typography>
                )}
              </Box>
            )}

            {!isActivated && (
              <Button
                type="button"
                variant="contained"
                fullWidth
                size="large"
                disabled={!hasValidToken || isLoading}
                onClick={handleActivation}
                startIcon={
                  isLoading ? (
                    <CircularProgress
                      size={20}
                      aria-label={accountActivationData.accessibility.loading}
                      sx={accountActivationStyles.buttonProgress}
                    />
                  ) : undefined
                }
                sx={accountActivationStyles.submitButton}
              >
                {isLoading
                  ? accountActivationData.buttons.activating
                  : accountActivationData.buttons.activate}
              </Button>
            )}

            <Typography variant="caption" sx={accountActivationStyles.personalLink}>
              {accountActivationData.personalLink}
            </Typography>

            <Box sx={accountActivationStyles.backLogin}>
              <Link
                component={RouterLink}
                to={accountActivationData.routes.login}
                underline="hover"
                color="secondary"
              >
                {accountActivationData.buttons.backToLogin}
              </Link>
            </Box>
          </Box>
        </CardContent>
      </Card>

      <CustomSnackbar
        open={snackbar.open}
        message={snackbar.message}
        severity={snackbar.severity}
        onClose={handleCloseSnackbar}
      />
    </Box>
  );
};

export default AccountActivation;
