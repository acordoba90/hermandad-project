import {
  Box,
  Button,
  Card,
  CardContent,
  TextField,
  Typography,
  Divider,
  Link,
} from '@mui/material';

import LoginIcon from '@mui/icons-material/Login';

import { loginPageStyles } from './loginStyle';
import { loginData } from './loginData';
import { Link as RouterLink } from 'react-router-dom';

const LoginForm = ({ onSubmit, onOpenPasswordReset }) => {
  const handleSubmit = async (event) => {
    event.preventDefault();

    const formData = new FormData(event.currentTarget);

    const credentials = {
      email: formData.get('email'),
      password: formData.get('password'),
    };

    await onSubmit(credentials);
  };

  return (
    <Card sx={loginPageStyles.card}>
      <CardContent sx={loginPageStyles.cardContent}>
        <Box sx={loginPageStyles.header}>
          <Box
            component="img"
            src="/escudo_hermandad_project.svg"
            alt="Escudo de Hermandad Project"
            sx={loginPageStyles.logoIcon}
          />

          <Typography variant="h4" fontWeight="bold">
            {loginData.title}
          </Typography>

          <Typography
            variant="body2"
            color="text.secondary"
            sx={loginPageStyles.subtitle}
          >
            {loginData.subtitle}
          </Typography>
        </Box>

        <Divider sx={loginPageStyles.divider} />

        <Box component="form" onSubmit={handleSubmit}>
          <TextField
            name="email"
            label={loginData.emailLabel}
            type="email"
            fullWidth
            required
            margin="normal"
            autoComplete="email"
          />

          <TextField
            name="password"
            label={loginData.passwordLabel}
            type="password"
            fullWidth
            required
            margin="normal"
            autoComplete="current-password"
          />

          <Button
            type="submit"
            variant="contained"
            fullWidth
            size="large"
            endIcon={<LoginIcon />}
            sx={loginPageStyles.submitButton}
          >
            {loginData.submitButton}
          </Button>
        </Box>

        <Box sx={loginPageStyles.forgotPassword}>
          <Link
            component="button"
            type="button"
            underline="hover"
            color="secondary"
            onClick={onOpenPasswordReset}
            sx={loginPageStyles.forgotPasswordLink}
          >
            {loginData.forgotPassword}
          </Link>
        </Box>

        <Typography
          variant="body2"
          color="text.secondary"
          textAlign="center"
          sx={loginPageStyles.registerText}
        >
          {loginData.registerText}{' '}
          <Link
            component={RouterLink}
            to="/alta-usuario"
            underline="hover"
            color="secondary"
          >
            {loginData.registerLink}
          </Link>
        </Typography>
      </CardContent>
    </Card>
  );
};

export default LoginForm;
