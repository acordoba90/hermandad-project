import { useRef, useState } from 'react';
import {
  Alert,
  Button,
  CircularProgress,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  TextField,
  Typography,
} from '@mui/material';
import { solicitudRestauracionData } from './solicitudRestauracionData';
import {
  solicitarRestauracionContrasena,
  validateResetEmail,
} from './solicitudRestauracionFunction';
import { appStyles } from '../../../styles/appStyles';

const SolicitudRestauracionDialog = ({ open, onClose }) => {
  const solicitudRestauracionStyles = appStyles.gestionLogin.passwordResetRequest;
  const requestInProgress = useRef(false);
  const [email, setEmail] = useState('');
  const [emailError, setEmailError] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const [result, setResult] = useState(null);

  const handleClose = () => {
    if (requestInProgress.current) return;
    setEmail('');
    setEmailError('');
    setResult(null);
    onClose();
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    if (requestInProgress.current || result?.success) return;

    const validationError = validateResetEmail(email);
    setEmailError(validationError);
    if (validationError) return;

    requestInProgress.current = true;
    setIsLoading(true);
    setResult(null);
    try {
      const response = await solicitarRestauracionContrasena(email);
      setResult(response);
      if (response.success) setEmail('');
    } finally {
      requestInProgress.current = false;
      setIsLoading(false);
    }
  };

  return (
    <Dialog
      open={open}
      onClose={handleClose}
      fullWidth
      slotProps={{ paper: { sx: solicitudRestauracionStyles.paper } }}
      aria-labelledby="password-reset-dialog-title"
      aria-describedby="password-reset-dialog-description"
    >
      <DialogTitle id="password-reset-dialog-title" sx={solicitudRestauracionStyles.title}>
        {solicitudRestauracionData.title}
      </DialogTitle>
      <DialogContent>
        <Typography id="password-reset-dialog-description" sx={solicitudRestauracionStyles.description}>
          {solicitudRestauracionData.description}
        </Typography>
        <form id="password-reset-request-form" onSubmit={handleSubmit} noValidate>
          <TextField
            autoFocus
            fullWidth
            required
            type="email"
            name="resetEmail"
            label={solicitudRestauracionData.emailLabel}
            value={email}
            disabled={isLoading || result?.success}
            error={Boolean(emailError)}
            helperText={emailError}
            autoComplete="email"
            slotProps={{ htmlInput: { maxLength: 150, 'aria-describedby': emailError ? 'reset-email-error' : undefined } }}
            FormHelperTextProps={{ id: 'reset-email-error', role: 'alert' }}
            onChange={(event) => {
              setEmail(event.target.value);
              if (emailError) setEmailError('');
            }}
          />
        </form>
        {result && (
          <Alert severity={result.success ? 'success' : 'error'} role="alert" sx={solicitudRestauracionStyles.alert}>
            {result.message}
          </Alert>
        )}
      </DialogContent>
      <DialogActions sx={solicitudRestauracionStyles.actions}>
        <Button type="button" color="secondary" onClick={handleClose} disabled={isLoading}>
          {result?.success ? solicitudRestauracionData.buttons.close : solicitudRestauracionData.buttons.cancel}
        </Button>
        {!result?.success && (
          <Button
            type="submit"
            form="password-reset-request-form"
            variant="contained"
            disabled={isLoading}
            startIcon={isLoading ? <CircularProgress size={20} aria-label="Envío en curso" sx={solicitudRestauracionStyles.progress} /> : undefined}
          >
            {isLoading ? solicitudRestauracionData.buttons.submitting : solicitudRestauracionData.buttons.submit}
          </Button>
        )}
      </DialogActions>
    </Dialog>
  );
};

export default SolicitudRestauracionDialog;
