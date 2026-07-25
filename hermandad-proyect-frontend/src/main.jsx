import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App.jsx';

import { BrowserRouter } from 'react-router-dom';

import { ThemeProvider } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import GlobalStyles from '@mui/material/GlobalStyles';

import { appGlobalStyles, appTheme } from './styles/appStyles';
import { AppSessionProvider } from './context/AppSessionContext.jsx';

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <BrowserRouter>
      <AppSessionProvider>
        <ThemeProvider theme={appTheme}>
          <CssBaseline />
          <GlobalStyles styles={appGlobalStyles} />
          <App />
        </ThemeProvider>
      </AppSessionProvider>
    </BrowserRouter>
  </React.StrictMode>
);
