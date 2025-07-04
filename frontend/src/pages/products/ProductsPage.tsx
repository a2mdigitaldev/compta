import React from 'react';
import {
  Typography,
  Box,
  Card,
  CardContent,
  Button,
  Grid,
} from '@mui/material';
import { Add, Inventory } from '@mui/icons-material';

const ProductsPage: React.FC = () => {
  return (
    <Box>
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={3}>
        <Typography variant="h4" component="h1" fontWeight="bold">
          Catalogue Produits
        </Typography>
        <Button
          variant="contained"
          startIcon={<Add />}
          color="primary"
        >
          Nouveau Produit
        </Button>
      </Box>

      <Grid container spacing={3}>
        <Grid item xs={12}>
          <Card elevation={3}>
            <CardContent>
              <Box display="flex" alignItems="center" mb={2}>
                <Inventory sx={{ mr: 2, color: 'primary.main' }} />
                <Typography variant="h6">
                  Gestion du Catalogue
                </Typography>
              </Box>
              <Typography color="textSecondary">
                Gérez votre catalogue de produits et services avec gestion des stocks.
              </Typography>
              <Typography color="textSecondary" sx={{ mt: 2 }}>
                Fonctionnalités incluses:
              </Typography>
              <Typography component="ul" color="textSecondary" sx={{ mt: 1 }}>
                <li>Catalogue produits avec catégories</li>
                <li>Gestion des prix et TVA</li>
                <li>Suivi des stocks et alertes</li>
                <li>Images et descriptions détaillées</li>
                <li>Codes-barres et références</li>
              </Typography>
            </CardContent>
          </Card>
        </Grid>
      </Grid>
    </Box>
  );
};

export default ProductsPage;
