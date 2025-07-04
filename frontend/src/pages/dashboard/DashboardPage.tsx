import React, { useEffect, useState } from 'react';
import {
  Grid,
  Card,
  CardContent,
  Typography,
  Box,
  Paper,
  CircularProgress,
  Alert,
} from '@mui/material';
import {
  TrendingUp,
  People,
  Receipt,
  AccountBalance,
  Business,
} from '@mui/icons-material';
import { useAuth } from '../../context/AuthContext';

interface DashboardStats {
  totalClients: number;
  totalSuppliers: number;
  totalInvoices: number;
  totalRevenue: number;
  monthlyRevenue: number;
  pendingInvoices: number;
}

interface StatCardProps {
  title: string;
  value: string | number;
  icon: React.ReactElement;
  color: string;
  subtitle?: string;
}

const StatCard: React.FC<StatCardProps> = ({ title, value, icon, color, subtitle }) => (
  <Card elevation={3}>
    <CardContent>
      <Box display="flex" alignItems="center" justifyContent="space-between">
        <Box>
          <Typography color="textSecondary" gutterBottom variant="body2">
            {title}
          </Typography>
          <Typography variant="h4" component="h2" fontWeight="bold">
            {value}
          </Typography>
          {subtitle && (
            <Typography color="textSecondary" variant="body2">
              {subtitle}
            </Typography>
          )}
        </Box>
        <Box
          sx={{
            backgroundColor: color,
            borderRadius: '50%',
            p: 2,
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
          }}
        >
          {React.cloneElement(icon, { sx: { color: 'white', fontSize: 30 } })}
        </Box>
      </Box>
    </CardContent>
  </Card>
);

const DashboardPage: React.FC = () => {
  const { user } = useAuth();
  const [stats, setStats] = useState<DashboardStats | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchDashboardStats = async () => {
      try {
        // Simulate API call - replace with actual API call
        setTimeout(() => {
          setStats({
            totalClients: 127,
            totalSuppliers: 48,
            totalInvoices: 234,
            totalRevenue: 1250000,
            monthlyRevenue: 125000,
            pendingInvoices: 12,
          });
          setLoading(false);
        }, 1000);
      } catch (error: any) {
        setError('Erreur lors du chargement des statistiques');
        setLoading(false);
      }
    };

    fetchDashboardStats();
  }, []);

  const formatCurrency = (amount: number) => {
    return new Intl.NumberFormat('fr-MA', {
      style: 'currency',
      currency: 'MAD',
    }).format(amount);
  };

  if (loading) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" minHeight="400px">
        <CircularProgress />
      </Box>
    );
  }

  if (error) {
    return (
      <Alert severity="error" sx={{ mt: 2 }}>
        {error}
      </Alert>
    );
  }

  return (
    <Box>
      {/* Welcome Section */}
      <Paper sx={{ p: 3, mb: 3, background: 'linear-gradient(135deg, #C41E3A 0%, #E14A63 100%)' }}>
        <Typography variant="h4" gutterBottom sx={{ color: 'white', fontWeight: 'bold' }}>
          Bienvenue, {user?.firstName} {user?.lastName}
        </Typography>
        <Typography variant="h6" sx={{ color: 'white', opacity: 0.9 }}>
          Tableau de bord - Plateforme Comptable Marocaine
        </Typography>
        <Typography variant="body1" sx={{ color: 'white', opacity: 0.8, mt: 1 }}>
          Voici un aperçu de vos données commerciales
        </Typography>
      </Paper>

      {/* Statistics Cards */}
      <Grid container spacing={3} sx={{ mb: 3 }}>
        <Grid item xs={12} sm={6} md={3}>
          <StatCard
            title="Total Clients"
            value={stats?.totalClients || 0}
            icon={<People />}
            color="#2E7D32"
          />
        </Grid>
        <Grid item xs={12} sm={6} md={3}>
          <StatCard
            title="Fournisseurs"
            value={stats?.totalSuppliers || 0}
            icon={<Business />}
            color="#1976D2"
          />
        </Grid>
        <Grid item xs={12} sm={6} md={3}>
          <StatCard
            title="Factures"
            value={stats?.totalInvoices || 0}
            icon={<Receipt />}
            color="#F57C00"
            subtitle={`${stats?.pendingInvoices || 0} en attente`}
          />
        </Grid>
        <Grid item xs={12} sm={6} md={3}>
          <StatCard
            title="Chiffre d'Affaires"
            value={formatCurrency(stats?.totalRevenue || 0)}
            icon={<TrendingUp />}
            color="#C41E3A"
            subtitle={`${formatCurrency(stats?.monthlyRevenue || 0)} ce mois`}
          />
        </Grid>
      </Grid>

      {/* Quick Actions & Recent Activity */}
      <Grid container spacing={3}>
        <Grid item xs={12} md={6}>
          <Card elevation={3}>
            <CardContent>
              <Typography variant="h6" gutterBottom>
                Actions Rapides
              </Typography>
              <Box sx={{ mt: 2 }}>
                <Typography variant="body2" color="textSecondary">
                  • Créer une nouvelle facture
                </Typography>
                <Typography variant="body2" color="textSecondary">
                  • Ajouter un nouveau client
                </Typography>
                <Typography variant="body2" color="textSecondary">
                  • Consulter les rapports
                </Typography>
                <Typography variant="body2" color="textSecondary">
                  • Gérer la comptabilité
                </Typography>
              </Box>
            </CardContent>
          </Card>
        </Grid>
        <Grid item xs={12} md={6}>
          <Card elevation={3}>
            <CardContent>
              <Typography variant="h6" gutterBottom>
                Activité Récente
              </Typography>
              <Box sx={{ mt: 2 }}>
                <Typography variant="body2" color="textSecondary">
                  • Facture #2024-001 créée
                </Typography>
                <Typography variant="body2" color="textSecondary">
                  • Client "Entreprise ABC" ajouté
                </Typography>
                <Typography variant="body2" color="textSecondary">
                  • Rapport mensuel généré
                </Typography>
                <Typography variant="body2" color="textSecondary">
                  • Paiement reçu - 15,000 MAD
                </Typography>
              </Box>
            </CardContent>
          </Card>
        </Grid>
      </Grid>
    </Box>
  );
};

export default DashboardPage;
