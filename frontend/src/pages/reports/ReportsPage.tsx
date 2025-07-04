import React from 'react';
import {
  Typography,
  Box,
  Card,
  CardContent,
  Button,
  Grid,
} from '@mui/material';
import { Assessment, PictureAsPdf, Download } from '@mui/icons-material';

const ReportsPage: React.FC = () => {
  return (
    <Box>
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={3}>
        <Typography variant="h4" component="h1" fontWeight="bold">
          Rapports et Analyses
        </Typography>
        <Button
          variant="contained"
          startIcon={<Download />}
          color="primary"
        >
          Exporter Rapport
        </Button>
      </Box>

      <Grid container spacing={3}>
        <Grid item xs={12} md={6}>
          <Card elevation={3}>
            <CardContent>
              <Box display="flex" alignItems="center" mb={2}>
                <Assessment sx={{ mr: 2, color: 'primary.main' }} />
                <Typography variant="h6">
                  Rapports Financiers
                </Typography>
              </Box>
              <Typography color="textSecondary" gutterBottom>
                États financiers et rapports de gestion.
              </Typography>
              <Typography component="ul" color="textSecondary" sx={{ mt: 1 }}>
                <li>Bilan comptable</li>
                <li>Compte de résultat</li>
                <li>Tableau de flux de trésorerie</li>
                <li>État des soldes de gestion</li>
              </Typography>
            </CardContent>
          </Card>
        </Grid>
        
        <Grid item xs={12} md={6}>
          <Card elevation={3}>
            <CardContent>
              <Box display="flex" alignItems="center" mb={2}>
                <PictureAsPdf sx={{ mr: 2, color: 'primary.main' }} />
                <Typography variant="h6">
                  Déclarations Fiscales
                </Typography>
              </Box>
              <Typography color="textSecondary" gutterBottom>
                Déclarations conformes à la réglementation marocaine.
              </Typography>
              <Typography component="ul" color="textSecondary" sx={{ mt: 1 }}>
                <li>Déclaration TVA</li>
                <li>Déclaration IS/IR</li>
                <li>CNSS et charges sociales</li>
                <li>Taxe professionnelle</li>
              </Typography>
            </CardContent>
          </Card>
        </Grid>

        <Grid item xs={12}>
          <Card elevation={3}>
            <CardContent>
              <Typography variant="h6" gutterBottom>
                Tableaux de Bord et KPIs
              </Typography>
              <Typography color="textSecondary" gutterBottom>
                Indicateurs de performance et analyses prédictives.
              </Typography>
              <Grid container spacing={2} sx={{ mt: 1 }}>
                <Grid item xs={12} sm={6} md={4}>
                  <Typography variant="body2" color="textSecondary">
                    • Évolution du CA
                  </Typography>
                </Grid>
                <Grid item xs={12} sm={6} md={4}>
                  <Typography variant="body2" color="textSecondary">
                    • Rentabilité par produit
                  </Typography>
                </Grid>
                <Grid item xs={12} sm={6} md={4}>
                  <Typography variant="body2" color="textSecondary">
                    • Analyse des créances
                  </Typography>
                </Grid>
                <Grid item xs={12} sm={6} md={4}>
                  <Typography variant="body2" color="textSecondary">
                    • Suivi de trésorerie
                  </Typography>
                </Grid>
                <Grid item xs={12} sm={6} md={4}>
                  <Typography variant="body2" color="textSecondary">
                    • Marges et coûts
                  </Typography>
                </Grid>
                <Grid item xs={12} sm={6} md={4}>
                  <Typography variant="body2" color="textSecondary">
                    • Prévisions financières
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

export default ReportsPage;
