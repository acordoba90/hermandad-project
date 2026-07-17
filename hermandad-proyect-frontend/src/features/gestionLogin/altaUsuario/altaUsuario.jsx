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

import { Link as RouterLink, useNavigate } from 'react-router-dom';

import PersonAddIcon from '@mui/icons-material/PersonAdd';

import { altaUsuarioData } from './altaUsuarioData';
import { appStyles } from '../../../styles/appStyles';
import { useState } from 'react';
import CustomSnackbar from '../../../components/snackbar/snackbar';
import { handleAltaUsuarioSubmit } from './altaUsuarioFunction';

const AltaUsuario = () => {
  const altaUsuarioStyles = appStyles.gestionLogin.registration;

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

  return (
    <Box sx={altaUsuarioStyles.page}>
      <Card sx={altaUsuarioStyles.card}>
        <CardContent sx={altaUsuarioStyles.cardContent}>
          <Box sx={altaUsuarioStyles.header}>
            <Box
              component="img"
              src="/escudo_hermandad_project.svg"
              alt="Escudo de Hermandad Project"
              sx={altaUsuarioStyles.logoIcon}
            />

            <Typography variant="h4" fontWeight="bold">
              {altaUsuarioData.title}
            </Typography>

            <Typography
              variant="body2"
              color="text.secondary"
              sx={altaUsuarioStyles.subtitle}
            >
              {altaUsuarioData.subtitle}
            </Typography>
          </Box>

          <Divider />

          <Box
            component="form"
            sx={altaUsuarioStyles.form}
            onSubmit={async (event) => {
              const result = await handleAltaUsuarioSubmit(event);

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
            }}
          >
            <TextField
              name="nombreUsuario"
              label={altaUsuarioData.fields.nombreUsuario}
              type="text"
              fullWidth
              required
              margin="normal"
              slotProps={{
                htmlInput: {
                  maxLength: 100
                },
              }}
            />

            <TextField
              name="email"
              label={altaUsuarioData.fields.email}
              type="email"
              fullWidth
              required
              margin="normal"
              slotProps={{
                htmlInput: {
                  maxLength: 150
                },
              }}
            />

            <TextField
              name="password"
              label={altaUsuarioData.fields.password}
              type="password"
              fullWidth
              required
              margin="normal"
              slotProps={{
                htmlInput: {
                  maxLength: 72,
                  minLength: 8,
                },
              }}
            />

            <TextField
              name="confirmPassword"
              label={altaUsuarioData.fields.confirmPassword}
              type="password"
              fullWidth
              required
              margin="normal"
              slotProps={{
                htmlInput: {
                  maxLength: 72,
                  minLength: 8,
                },
              }}
            />

            <Button
              type="submit"
              variant="contained"
              fullWidth
              size="large"
              endIcon={<PersonAddIcon />}
              sx={altaUsuarioStyles.submitButton}
            >
              {altaUsuarioData.buttons.submit}
            </Button>
          </Box>

          <Box sx={altaUsuarioStyles.backLogin}>
            <Link
              component={RouterLink}
              to="/"
              underline="hover"
              color="secondary"
            >
              {altaUsuarioData.buttons.backToLogin}
            </Link>
          </Box>
        </CardContent>
      </Card>

      <Typography
        variant="caption"
        sx={altaUsuarioStyles.footerLegend}
      >
        {altaUsuarioData.footerLegend}
      </Typography>
      <CustomSnackbar
        open={snackbar.open}
        message={snackbar.message}
        severity={snackbar.severity}
        onClose={handleCloseSnackbar}
      />
    </Box>
  );
};

export default AltaUsuario;
