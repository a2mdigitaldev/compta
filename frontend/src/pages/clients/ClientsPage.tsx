import React from 'react';
import {
  Typography,
  Box,
  Card,
  CardContent,
  Button,
  Grid,
} from '@mui/material';
import { Add, People } from '@mui/icons-material';

const ClientsPage: React.FC = () => {
  return (
    <Box>
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={3}>
        <Typography variant="h4" component="h1" fontWeight="bold">
          Gestion des Clients
        </Typography>
        <Button
          variant="contained"
          startIcon={<Add />}
          color="primary"
        >
          Nouveau Client
        </Button>
      </Box>

      <Grid container spacing={3}>
        <Grid item xs={12}>
          <Card elevation={3}>
            <CardContent>
              <Box display="flex" alignItems="center" mb={2}>
                <People sx={{ mr: 2, color: 'primary.main' }} />
                <Typography variant="h6">
                  Liste des Clients
                </Typography>
              </Box>
              <Typography color="textSecondary">
                Gérez vos clients, consultez leurs informations et suivez leurs transactions.
              </Typography>
              <Typography color="textSecondary" sx={{ mt: 2 }}>
                Cette page sera développée avec:
              </Typography>
              <Typography component="ul" color="textSecondary" sx={{ mt: 1 }}>
                <li>Liste complète des clients</li>
                <li>Formulaire d'ajout/modification</li>
                <li>Recherche et filtres</li>
                <li>Historique des transactions</li>
                <li>Export des données</li>
              </Typography>
            </CardContent>
          </Card>
        </Grid>
      </Grid>
    </Box>
  );
};

export default ClientsPage;
