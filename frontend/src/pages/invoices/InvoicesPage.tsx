import React from 'react';
import {
  Typography,
  Box,
  Card,
  CardContent,
  Button,
  Grid,
} from '@mui/material';
import { Add, Receipt } from '@mui/icons-material';

const InvoicesPage: React.FC = () => {
  return (
    <Box>
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={3}>
        <Typography variant="h4" component="h1" fontWeight="bold">
          Gestion des Factures
        </Typography>
        <Button
          variant="contained"
          startIcon={<Add />}
          color="primary"
        >
          Nouvelle Facture
        </Button>
      </Box>

      <Grid container spacing={3}>
        <Grid item xs={12}>
          <Card elevation={3}>
            <CardContent>
              <Box display="flex" alignItems="center" mb={2}>
                <Receipt sx={{ mr: 2, color: 'primary.main' }} />
                <Typography variant="h6">
                  Facturation et Devis
                </Typography>
              </Box>
              <Typography color="textSecondary">
                Créez et gérez vos factures, devis et avoirs avec conformité marocaine.
              </Typography>
              <Typography color="textSecondary" sx={{ mt: 2 }}>
                Système de facturation complet:
              </Typography>
              <Typography component="ul" color="textSecondary" sx={{ mt: 1 }}>
                <li>Création de factures et devis</li>
                <li>Calcul automatique de la TVA marocaine</li>
                <li>Numérotation automatique conforme</li>
                <li>Suivi des paiements et relances</li>
                <li>Export PDF et envoi par email</li>
              </Typography>
            </CardContent>
          </Card>
        </Grid>
      </Grid>
    </Box>
  );
};

export default InvoicesPage;
