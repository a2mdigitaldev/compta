import React from 'react';
import {
  Typography,
  Box,
  Card,
  CardContent,
  Button,
  Grid,
} from '@mui/material';
import { Add, Business } from '@mui/icons-material';

const SuppliersPage: React.FC = () => {
  return (
    <Box>
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={3}>
        <Typography variant="h4" component="h1" fontWeight="bold">
          Gestion des Fournisseurs
        </Typography>
        <Button
          variant="contained"
          startIcon={<Add />}
          color="primary"
        >
          Nouveau Fournisseur
        </Button>
      </Box>

      <Grid container spacing={3}>
        <Grid item xs={12}>
          <Card elevation={3}>
            <CardContent>
              <Box display="flex" alignItems="center" mb={2}>
                <Business sx={{ mr: 2, color: 'primary.main' }} />
                <Typography variant="h6">
                  Liste des Fournisseurs
                </Typography>
              </Box>
              <Typography color="textSecondary">
                Gérez vos fournisseurs et suivez vos achats et paiements.
              </Typography>
              <Typography color="textSecondary" sx={{ mt: 2 }}>
                Fonctionnalités à développer:
              </Typography>
              <Typography component="ul" color="textSecondary" sx={{ mt: 1 }}>
                <li>Liste des fournisseurs avec pagination</li>
                <li>Ajout/modification de fournisseurs</li>
                <li>Gestion des contacts et adresses</li>
                <li>Suivi des commandes et livraisons</li>
                <li>Historique des paiements</li>
              </Typography>
            </CardContent>
          </Card>
        </Grid>
      </Grid>
    </Box>
  );
};

export default SuppliersPage;
