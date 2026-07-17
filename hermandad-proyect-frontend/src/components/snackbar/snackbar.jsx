import Snackbar from '@mui/material/Snackbar';
import Alert from '@mui/material/Alert';
import { appStyles } from '../../styles/appStyles';

const CustomSnackbar = ({
  open,
  message,
  severity,
  onClose,
}) => {

  return (
    <Snackbar
      open={open}
      autoHideDuration={5000}
      onClose={onClose}
      anchorOrigin={{
        vertical: 'bottom',
        horizontal: 'center',
      }}
    >
      <Alert
        onClose={onClose}
        severity={severity}
        variant="filled"
        sx={appStyles.feedback.snackbarAlert}
      >
        {message}
      </Alert>
    </Snackbar>
  );
};

export default CustomSnackbar;
