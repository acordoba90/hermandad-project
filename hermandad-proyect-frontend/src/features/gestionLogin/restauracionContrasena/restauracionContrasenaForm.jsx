import { useEffect, useRef, useState } from 'react';
import {
  Alert,
  Box,
  Button,
  CircularProgress,
  IconButton,
  InputAdornment,
  Link,
  TextField,
  Typography,
} from '@mui/material';
import VisibilityIcon from '@mui/icons-material/Visibility';
import VisibilityOffIcon from '@mui/icons-material/VisibilityOff';
import { Link as RouterLink } from 'react-router-dom';
import {
  PASSWORD_MAX_LENGTH,
  PASSWORD_MIN_LENGTH,
  passwordRequirements,
  validatePassword,
} from '../passwordValidation';
import { restauracionContrasenaData } from './restauracionContrasenaData';
import { restablecerContrasena } from './restauracionContrasenaFunction';
import { restauracionContrasenaStyles } from './restauracionContrasenaStyle';

const RestauracionContrasenaForm = ({ token, onSuccess }) => {
  const passwordRef = useRef(null);
  const requestInProgress = useRef(false);
  const [password, setPassword] = useState('');
  const [confirmation, setConfirmation] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [errors, setErrors] = useState({});
  const [isLoading, setIsLoading] = useState(false);
  const [result, setResult] = useState(null);

  useEffect(() => passwordRef.current?.focus(), []);

  const validate = () => {
    const nextErrors = {};
    const passwordError = validatePassword(password);
    if (passwordError) nextErrors.password = passwordError;
    if (!confirmation) nextErrors.confirmation = restauracionContrasenaData.messages.requiredConfirmation;
    else if (password !== confirmation) nextErrors.confirmation = restauracionContrasenaData.messages.mismatch;
    setErrors(nextErrors);
    return Object.keys(nextErrors).length === 0;
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    if (requestInProgress.current || result?.success || !validate()) return;

    requestInProgress.current = true;
    setIsLoading(true);
    setResult(null);
    try {
      const response = await restablecerContrasena({
        token,
        nuevaContrasena: password,
        confirmacionContrasena: confirmation,
      });
      setResult(response);
      if (response.success) {
        setPassword('');
        setConfirmation('');
        onSuccess();
      }
    } finally {
      requestInProgress.current = false;
      setIsLoading(false);
    }
  };

  const passwordAdornment = (
    <InputAdornment position="end">
      <IconButton
        edge="end"
        type="button"
        disabled={isLoading || result?.success}
        onClick={() => setShowPassword((visible) => !visible)}
        aria-label={showPassword ? 'Ocultar contraseña' : 'Mostrar contraseña'}
        sx={restauracionContrasenaStyles.passwordToggle}
      >
        {showPassword ? <VisibilityOffIcon /> : <VisibilityIcon />}
      </IconButton>
    </InputAdornment>
  );

  return (
    <>
      <Box component="form" onSubmit={handleSubmit} noValidate sx={restauracionContrasenaStyles.form}>
        <TextField
          inputRef={passwordRef}
          name="newPassword"
          label={restauracionContrasenaData.fields.password}
          type={showPassword ? 'text' : 'password'}
          fullWidth
          required
          margin="normal"
          value={password}
          disabled={isLoading || result?.success}
          error={Boolean(errors.password)}
          helperText={errors.password}
          autoComplete="new-password"
          slotProps={{ htmlInput: { minLength: PASSWORD_MIN_LENGTH, maxLength: PASSWORD_MAX_LENGTH }, input: { endAdornment: passwordAdornment } }}
          onChange={(event) => {
            setPassword(event.target.value);
            if (errors.password) setErrors((current) => ({ ...current, password: '' }));
          }}
        />
        <Box component="ul" sx={restauracionContrasenaStyles.requirements} aria-label="Requisitos de contraseña">
          {passwordRequirements.map((requirement) => (
            <Typography component="li" variant="caption" key={requirement}>{requirement}</Typography>
          ))}
        </Box>
        <TextField
          name="passwordConfirmation"
          label={restauracionContrasenaData.fields.confirmation}
          type={showPassword ? 'text' : 'password'}
          fullWidth
          required
          margin="normal"
          value={confirmation}
          disabled={isLoading || result?.success}
          error={Boolean(errors.confirmation)}
          helperText={errors.confirmation}
          autoComplete="new-password"
          slotProps={{ htmlInput: { minLength: PASSWORD_MIN_LENGTH, maxLength: PASSWORD_MAX_LENGTH }, input: { endAdornment: passwordAdornment } }}
          onChange={(event) => {
            setConfirmation(event.target.value);
            if (errors.confirmation) setErrors((current) => ({ ...current, confirmation: '' }));
          }}
        />
        <Button
          type="submit"
          variant="contained"
          fullWidth
          size="large"
          disabled={isLoading || result?.success}
          startIcon={isLoading ? <CircularProgress size={20} aria-label="Actualización en curso" sx={restauracionContrasenaStyles.progress} /> : undefined}
          sx={restauracionContrasenaStyles.submitButton}
        >
          {isLoading ? restauracionContrasenaData.buttons.submitting : restauracionContrasenaData.buttons.submit}
        </Button>
      </Box>
      {result && <Alert severity={result.success ? 'success' : 'error'} role="alert" sx={restauracionContrasenaStyles.alert}>{result.message}</Alert>}
      <Box sx={restauracionContrasenaStyles.backLogin}>
        <Link component={RouterLink} to={restauracionContrasenaData.routes.login} replace underline="hover" color="secondary">
          {restauracionContrasenaData.buttons.backToLogin}
        </Link>
      </Box>
    </>
  );
};

export default RestauracionContrasenaForm;
