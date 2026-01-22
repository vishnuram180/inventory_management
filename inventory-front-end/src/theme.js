import { createTheme } from '@mui/material/styles'

const theme = createTheme({
  palette: {
    primary: {
      main: '#1E88E5',
    },
    secondary: {
      main: '#546E7A',
    },
    background: {
      default: '#F5F7FA',
      paper: '#FFFFFF',
    },
    text: {
      primary: '#263238',
    },
    error: {
      main: '#C62828',
    },
    warning: {
      main: '#F9A825',
    },
    success: {
      main: '#2E7D32',
    },
  },
  components: {
    // Optionally add global component overrides here
  },
})

export default theme
