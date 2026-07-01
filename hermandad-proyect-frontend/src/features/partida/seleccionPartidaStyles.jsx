export const seleccionPartidaStyles = {
  page: {
    minHeight: '100vh',
    background:
      'radial-gradient(circle at top, rgba(75,29,92,0.35) 0%, #0E0A12 45%, #09060C 100%)',
    padding: {
      xs: 2,
      md: 4,
    },
  },

  content: {
    maxWidth: '1600px',
    margin: '0 auto',
  },

  topBar: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    mb: 5,
    gap: 2,
    flexWrap: 'wrap',
  },

  brand: {
    display: 'flex',
    alignItems: 'center',
    gap: 2,
  },

  brandIcon: {
    fontSize: 52,
    color: 'secondary.main',
    filter: 'drop-shadow(0 0 10px rgba(212,175,55,0.35))',
  },

  brandTitle: {
    fontWeight: 700,
    lineHeight: 1,
    color: 'text.primary',
  },

  brandSubtitle: {
    color: 'secondary.main',
    letterSpacing: '0.15em',
    textTransform: 'uppercase',
  },

  userBox: {
    display: 'flex',
    alignItems: 'center',
    gap: 2,
    padding: '12px 18px',
    borderRadius: '18px',
    background: 'rgba(255,255,255,0.04)',
    border: '1px solid rgba(212,175,55,0.18)',
    backdropFilter: 'blur(12px)',
  },

  userIcon: {
    color: 'secondary.main',
  },

  header: {
    mb: 4,
  },

  pageTitle: {
    fontWeight: 700,
    mb: 1,
    color: 'text.primary',
  },

  grid: {
    display: 'grid',
    gridTemplateColumns: {
      xs: '1fr',
      md: 'repeat(2, 1fr)',
      xl: 'repeat(4, 1fr)',
    },
    gap: 3,
    mb: 5,
  },

  gameCard: {
    position: 'relative',
    overflow: 'hidden',
    borderRadius: '28px',
    background:
      'linear-gradient(180deg, rgba(31,20,39,0.98) 0%, rgba(18,12,24,0.98) 100%)',
    border: '1px solid rgba(212,175,55,0.15)',
    transition: 'all 0.25s ease',
    minHeight: '520px',
    display: 'flex',
    flexDirection: 'column',

    '&:hover': {
      transform: 'translateY(-6px)',
      border: '1px solid rgba(212,175,55,0.45)',
      boxShadow: '0 25px 50px rgba(0,0,0,0.45)',
    },
  },

  lastGameCard: {
    border: '1px solid rgba(212,175,55,0.35)',
    boxShadow: '0 0 25px rgba(212,175,55,0.12)',
  },

  lastGameChip: {
    position: 'absolute',
    top: 18,
    right: 18,
    background:
      'linear-gradient(135deg, rgba(212,175,55,1) 0%, rgba(155,122,31,1) 100%)',
    color: '#120C18',
    fontWeight: 700,
    zIndex: 2,
  },

  gameCardContent: {
    display: 'flex',
    flexDirection: 'column',
    flex: 1,
    padding: 4,
  },

  shieldWrapper: {
    width: 92,
    height: 92,
    borderRadius: '50%',
    margin: '0 auto 24px auto',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    background:
      'radial-gradient(circle, rgba(212,175,55,0.22) 0%, rgba(212,175,55,0.06) 70%)',
    border: '1px solid rgba(212,175,55,0.25)',
  },

  shieldIcon: {
    fontSize: 48,
    color: 'secondary.main',
  },

  gameTitle: {
    textAlign: 'center',
    fontWeight: 700,
    mb: 1,
    color: 'text.primary',
  },

  city: {
    textAlign: 'center',
    color: 'secondary.main',
    fontWeight: 600,
    mb: 0.5,
  },

  statsBox: {
    mt: 4,
    display: 'flex',
    flexDirection: 'column',
    gap: 2.2,
  },

  statRow: {
    display: 'flex',
    flexDirection: 'column',
    gap: 0.8,
  },

  statLabel: {
    display: 'flex',
    alignItems: 'center',
    gap: 1,
    color: 'text.secondary',
  },

  statProgressWrapper: {
    display: 'flex',
    alignItems: 'center',
    gap: 1.5,
  },

  statProgress: {
    flex: 1,
    height: 10,
    borderRadius: 999,
    backgroundColor: 'rgba(255,255,255,0.08)',
  },

  statValue: {
    minWidth: '30px',
    textAlign: 'right',
    fontWeight: 700,
    color: 'text.primary',
  },

  moneyRow: {
    mt: 1,
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    padding: '10px 14px',
    borderRadius: '14px',
    background: 'rgba(255,255,255,0.03)',
    border: '1px solid rgba(212,175,55,0.08)',
  },

  money: {
    fontWeight: 700,
    color: 'secondary.main',
  },

  lastAccess: {
    display: 'flex',
    alignItems: 'center',
    gap: 1,
    mt: 'auto',
    mb: 3,
    color: 'text.secondary',
  },

  continueButton: {
    py: 1.4,
    borderRadius: '14px',
    fontWeight: 700,
    background:
      'linear-gradient(135deg, #4B1D5C 0%, #6D2F83 100%)',

    '&:hover': {
      background:
        'linear-gradient(135deg, #5A2370 0%, #7C3695 100%)',
    },
  },

  newGameCard: {
    minHeight: '520px',
    borderRadius: '28px',
    border: '2px dashed rgba(212,175,55,0.25)',
    background:
      'linear-gradient(180deg, rgba(26,18,32,0.75) 0%, rgba(15,10,20,0.9) 100%)',
    transition: 'all 0.25s ease',

    '&:hover': {
      transform: 'translateY(-6px)',
      border: '2px dashed rgba(212,175,55,0.55)',
      boxShadow: '0 20px 40px rgba(0,0,0,0.35)',
    },
  },

  newGameContent: {
    height: '100%',
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center',
    padding: 4,
    textAlign: 'center',
  },

  newGameIconWrapper: {
    width: 100,
    height: 100,
    borderRadius: '50%',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    mb: 3,
    background:
      'radial-gradient(circle, rgba(212,175,55,0.18) 0%, rgba(212,175,55,0.03) 70%)',
    border: '1px solid rgba(212,175,55,0.22)',
  },

  newGameIcon: {
    fontSize: 54,
    color: 'secondary.main',
  },

  newGameTitle: {
    fontWeight: 700,
    mb: 2,
  },

  newGameText: {
    maxWidth: '260px',
    mb: 4,
    lineHeight: 1.7,
  },

  newGameButton: {
    borderRadius: '14px',
    px: 3,
    py: 1.2,
    fontWeight: 700,
    borderColor: 'rgba(212,175,55,0.45)',

    '&:hover': {
      borderColor: 'secondary.main',
      background: 'rgba(212,175,55,0.08)',
    },
  },

  adviceCard: {
    borderRadius: '24px',
    background:
      'linear-gradient(135deg, rgba(44,24,58,0.95) 0%, rgba(18,12,24,0.98) 100%)',
    border: '1px solid rgba(212,175,55,0.15)',
  },

  adviceContent: {
    display: 'flex',
    alignItems: 'center',
    gap: 3,
    padding: 3,
  },

  adviceIcon: {
    fontSize: 42,
    color: 'secondary.main',
  },

  adviceArrow: {
    marginLeft: 'auto',
    color: 'secondary.main',
  },
};