export const formatCurrency = (value) => {
  if (value === null || value === undefined) {
    return '0 €';
  }

  return new Intl.NumberFormat('es-ES', {
    style: 'currency',
    currency: 'EUR',
    maximumFractionDigits: 0,
  }).format(value);
};

export const getStatColor = (value) => {
  if (value >= 75) {
    return 'success';
  }

  if (value >= 50) {
    return 'warning';
  }

  return 'error';
};

export const handleCrearNuevaPartida = () => {
  console.log('Crear nueva partida');

  // Aquí más adelante navegarás a la creación de partida:
  // navigate('/nueva-partida');
};
