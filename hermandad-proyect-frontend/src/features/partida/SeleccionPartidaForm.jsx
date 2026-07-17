import {
  Box,
  Button,
  Card,
  CardContent,
  Chip,
  LinearProgress,
  Typography,
} from '@mui/material';

import ShieldIcon from '@mui/icons-material/Shield';
import AddIcon from '@mui/icons-material/Add';
import FavoriteBorderIcon from '@mui/icons-material/FavoriteBorder';
import EmojiEventsIcon from '@mui/icons-material/EmojiEvents';
import AccountBalanceWalletIcon from '@mui/icons-material/AccountBalanceWallet';
import AccessTimeIcon from '@mui/icons-material/AccessTime';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import LocalFireDepartmentIcon from '@mui/icons-material/LocalFireDepartment';
import TipsAndUpdatesIcon from '@mui/icons-material/TipsAndUpdates';

import { appStyles } from '../../styles/appStyles';
import { formatCurrency, getStatColor } from './seleccionPartidaFunction';

const seleccionPartidaStyles = appStyles.seleccionPartida;

const StatRow = ({ icon, label, value }) => (
  <Box sx={seleccionPartidaStyles.statRow}>
    <Box sx={seleccionPartidaStyles.statLabel}>
      {icon}
      <Typography variant="body2">{label}</Typography>
    </Box>

    <Box sx={seleccionPartidaStyles.statProgressWrapper}>
      <LinearProgress
        variant="determinate"
        value={value}
        color={getStatColor(value)}
        sx={seleccionPartidaStyles.statProgress}
      />
      <Typography variant="body2" sx={seleccionPartidaStyles.statValue}>
        {value}
      </Typography>
    </Box>
  </Box>
);

const SeleccionPartidaForm = ({
  partidas,
  onContinuarPartida,
  onCrearNuevaPartida,
}) => {
  return (
    <Box sx={seleccionPartidaStyles.content}>
      <Box sx={seleccionPartidaStyles.topBar}>
        <Box sx={seleccionPartidaStyles.brand}>
          <Box
            component="img"
            src="/escudo_hermandad_project.svg"
            alt="Escudo de Hermandad Project"
            sx={seleccionPartidaStyles.logoIcon}
          />

          <Box>
            <Typography variant="h3" sx={seleccionPartidaStyles.brandTitle}>
              Hermandad
            </Typography>
            <Typography variant="h6" sx={seleccionPartidaStyles.brandSubtitle}>
              Project
            </Typography>
          </Box>
        </Box>

        <Box sx={seleccionPartidaStyles.userBox}>
          <ShieldIcon sx={seleccionPartidaStyles.userIcon} />
          <Box>
            <Typography variant="body1" fontWeight={700}>
              Juan de la Cruz
            </Typography>
            <Typography variant="body2" color="text.secondary">
              Bienvenido de nuevo
            </Typography>
          </Box>
        </Box>
      </Box>

      <Box sx={seleccionPartidaStyles.header}>
        <Typography variant="h4" sx={seleccionPartidaStyles.pageTitle}>
          Tus Hermandades
        </Typography>

        <Typography variant="body1" color="text.secondary">
          Selecciona con qué partida deseas continuar
        </Typography>
      </Box>

      <Box sx={seleccionPartidaStyles.grid}>
        {partidas.map((partida) => (
          <Card
            key={partida.id}
            sx={[
              seleccionPartidaStyles.gameCard,
              partida.ultimaPartida && seleccionPartidaStyles.lastGameCard,
            ]}
          >
            {partida.ultimaPartida && (
              <Chip
                label="Última partida"
                sx={seleccionPartidaStyles.lastGameChip}
              />
            )}

            <CardContent sx={seleccionPartidaStyles.gameCardContent}>
              <Box sx={seleccionPartidaStyles.shieldWrapper}>
                <ShieldIcon sx={seleccionPartidaStyles.shieldIcon} />
              </Box>

              <Typography variant="h5" sx={seleccionPartidaStyles.gameTitle}>
                {partida.nombre}
              </Typography>

              <Typography variant="subtitle1" sx={seleccionPartidaStyles.city}>
                {partida.ciudad}
              </Typography>

              <Typography variant="body2" color="text.secondary">
                {partida.tipo}
              </Typography>

              <Box sx={seleccionPartidaStyles.statsBox}>
                <StatRow
                  icon={<FavoriteBorderIcon fontSize="small" />}
                  label="Devoción"
                  value={partida.devocion}
                />

                <StatRow
                  icon={<LocalFireDepartmentIcon fontSize="small" />}
                  label="Solemnidad"
                  value={partida.solemnidad}
                />

                <StatRow
                  icon={<EmojiEventsIcon fontSize="small" />}
                  label="Prestigio"
                  value={partida.prestigio}
                />

                <Box sx={seleccionPartidaStyles.moneyRow}>
                  <Box sx={seleccionPartidaStyles.statLabel}>
                    <AccountBalanceWalletIcon fontSize="small" />
                    <Typography variant="body2">Presupuesto</Typography>
                  </Box>

                  <Typography variant="body2" sx={seleccionPartidaStyles.money}>
                    {formatCurrency(partida.presupuesto)}
                  </Typography>
                </Box>
              </Box>

              <Box sx={seleccionPartidaStyles.lastAccess}>
                <AccessTimeIcon fontSize="small" />
                <Typography variant="body2">
                  Último acceso: {partida.ultimoAcceso}
                </Typography>
              </Box>

              <Button
                fullWidth
                variant="contained"
                color="primary"
                endIcon={<ChevronRightIcon />}
                sx={seleccionPartidaStyles.continueButton}
                onClick={() => onContinuarPartida(partida)}
              >
                Continuar
              </Button>
            </CardContent>
          </Card>
        ))}

        <Card sx={seleccionPartidaStyles.newGameCard}>
          <CardContent sx={seleccionPartidaStyles.newGameContent}>
            <Box sx={seleccionPartidaStyles.newGameIconWrapper}>
              <AddIcon sx={seleccionPartidaStyles.newGameIcon} />
            </Box>

            <Typography variant="h5" sx={seleccionPartidaStyles.newGameTitle}>
              Nueva Hermandad
            </Typography>

            <Typography
              variant="body2"
              color="text.secondary"
              textAlign="center"
              sx={seleccionPartidaStyles.newGameText}
            >
              Comienza una nueva historia y escribe tu propia leyenda.
            </Typography>

            <Button
              variant="outlined"
              color="secondary"
              sx={seleccionPartidaStyles.newGameButton}
              onClick={onCrearNuevaPartida}
            >
              Crear nueva partida
            </Button>
          </CardContent>
        </Card>
      </Box>

      <Card sx={seleccionPartidaStyles.adviceCard}>
        <CardContent sx={seleccionPartidaStyles.adviceContent}>
          <TipsAndUpdatesIcon sx={seleccionPartidaStyles.adviceIcon} />

          <Box>
            <Typography variant="subtitle1" color="secondary" fontWeight={700}>
              Consejo del mayordomo
            </Typography>

            <Typography variant="body2" color="text.secondary">
              Cuida de tus hermanos, mantén viva la fe del pueblo y engrandece
              el legado de tu hermandad.
            </Typography>
          </Box>

          <ChevronRightIcon sx={seleccionPartidaStyles.adviceArrow} />
        </CardContent>
      </Card>
    </Box>
  );
};

export default SeleccionPartidaForm;
