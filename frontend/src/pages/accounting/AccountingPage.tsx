import React from 'react';
import {
  Typography,
  Box,
  Card,
  CardContent,
  Button,
  Grid,
} from '@mui/material';
import { Add, AccountBalance } from '@mui/icons-material';

const AccountingPage: React.FC = () => {
  return (
    <Box>
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={3}>
        <Typography variant="h4" component="h1" fontWeight="bold">
          Comptabilité Générale
        </Typography>
        <Button
          variant="contained"
          startIcon={<Add />}
          color="primary"
        >
          Nouvelle Écriture
        </Button>
      </Box>

      <Grid container spacing={3}>
        <Grid item xs={12} md={6}>
          <Card elevation={3}>
            <CardContent>
              <Box display="flex" alignItems="center" mb={2}>
                <AccountBalance sx={{ mr: 2, color: 'primary.main' }} />
                <Typography variant="h6">
                  Plan Comptable (PCMN)
                </Typography>
              </Box>
              <Typography color="textSecondary">
                Plan Comptable Marocain Normalisé conforme aux standards.
              </Typography>
              <Typography component="ul" color="textSecondary" sx={{ mt: 1 }}>
                <li>Comptes de bilan et de gestion</li>
                <li>Structure hiérarchique conforme</li>
                <li>Codes comptables normalisés</li>
              </Typography>
            </CardContent>
          </Card>
        </Grid>
        
        <Grid item xs={12} md={6}>
          <Card elevation={3}>
            <CardContent>
              <Typography variant="h6" gutterBottom>
                Journal des Écritures
              </Typography>
              <Typography color="textSecondary">
                Saisie et consultation des écritures comptables.
              </Typography>
              <Typography component="ul" color="textSecondary" sx={{ mt: 1 }}>
                <li>Partie double automatisée</li>
                <li>Validation des équilibres</li>
                <li>Historique complet</li>
              </Typography>
            </CardContent>
          </Card>
        </Grid>

        <Grid item xs={12}>
          <Card elevation={3}>
            <CardContent>
              <Typography variant="h6" gutterBottom>
                Documents Comptables
              </Typography>
              <Typography color="textSecondary" gutterBottom>
                Génération automatique des documents comptables obligatoires.
              </Typography>
              <Grid container spacing={2} sx={{ mt: 1 }}>
                <Grid item xs={12} sm={6} md={3}>
                  <Typography variant="body2" color="textSecondary">
                    • Balance des comptes
                  </Typography>
                </Grid>
                <Grid item xs={12} sm={6} md={3}>
                  <Typography variant="body2" color="textSecondary">
                    • Grand livre
                  </Typography>
                </Grid>
                <Grid item xs={12} sm={6} md={3}>
                  <Typography variant="body2" color="textSecondary">
                    • Journal général
                  </Typography>
                </Grid>
                <Grid item xs={12} sm={6} md={3}>
                  <Typography variant="body2" color="textSecondary">
                    • Balances auxiliaires
                  </Typography>
                </Grid>
              </Grid>
            </CardContent>
          </Card>
        </Grid>
      </Grid>
    </Box>
  );
};

export default AccountingPage;
