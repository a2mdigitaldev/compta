import React from 'react';
import {
  Typography,
  Box,
  Card,
  Button,
  Tabs,
  Tab,
} from '@mui/material';
import { Settings, Save, Business, Person, Security } from '@mui/icons-material';

interface TabPanelProps {
  children?: React.ReactNode;
  index: number;
  value: number;
}

function TabPanel({ children, value, index }: TabPanelProps) {
  return (
    <div hidden={value !== index}>
      {value === index && <Box sx={{ p: 3 }}>{children}</Box>}
    </div>
  );
}

const SettingsPage: React.FC = () => {
  const [tabValue, setTabValue] = React.useState(0);

  const handleTabChange = (_event: React.SyntheticEvent, newValue: number) => {
    setTabValue(newValue);
  };

  return (
    <Box>
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={3}>
        <Typography variant="h4" component="h1" fontWeight="bold">
          Paramètres
        </Typography>
        <Button
          variant="contained"
          startIcon={<Save />}
          color="primary"
        >
          Sauvegarder
        </Button>
      </Box>

      <Card elevation={3}>
        <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
          <Tabs value={tabValue} onChange={handleTabChange}>
            <Tab 
              icon={<Business />} 
              label="Entreprise" 
              iconPosition="start"
            />
            <Tab 
              icon={<Person />} 
              label="Utilisateur" 
              iconPosition="start"
            />
            <Tab 
              icon={<Security />} 
              label="Sécurité" 
              iconPosition="start"
            />
            <Tab 
              icon={<Settings />} 
              label="Système" 
              iconPosition="start"
            />
          </Tabs>
        </Box>

        <TabPanel value={tabValue} index={0}>
          <Typography variant="h6" gutterBottom>
            Informations de l'Entreprise
          </Typography>
          <Typography color="textSecondary" gutterBottom>
            Configurez les informations de votre entreprise.
          </Typography>
          <Typography component="ul" color="textSecondary" sx={{ mt: 2 }}>
            <li>Raison sociale et coordonnées</li>
            <li>Numéros d'identification (ICE, RC, IF, CNSS)</li>
            <li>Logo et signature électronique</li>
            <li>Paramètres de facturation</li>
            <li>Comptes bancaires</li>
          </Typography>
        </TabPanel>

        <TabPanel value={tabValue} index={1}>
          <Typography variant="h6" gutterBottom>
            Profil Utilisateur
          </Typography>
          <Typography color="textSecondary" gutterBottom>
            Gérez votre profil et vos préférences.
          </Typography>
          <Typography component="ul" color="textSecondary" sx={{ mt: 2 }}>
            <li>Informations personnelles</li>
            <li>Préférences de langue</li>
            <li>Notifications et alertes</li>
            <li>Thème et affichage</li>
            <li>Raccourcis personnalisés</li>
          </Typography>
        </TabPanel>

        <TabPanel value={tabValue} index={2}>
          <Typography variant="h6" gutterBottom>
            Sécurité et Accès
          </Typography>
          <Typography color="textSecondary" gutterBottom>
            Configurez la sécurité de votre compte.
          </Typography>
          <Typography component="ul" color="textSecondary" sx={{ mt: 2 }}>
            <li>Changement de mot de passe</li>
            <li>Authentification à deux facteurs</li>
            <li>Sessions actives</li>
            <li>Logs de connexion</li>
            <li>Permissions et rôles</li>
          </Typography>
        </TabPanel>

        <TabPanel value={tabValue} index={3}>
          <Typography variant="h6" gutterBottom>
            Paramètres Système
          </Typography>
          <Typography color="textSecondary" gutterBottom>
            Configuration générale du système.
          </Typography>
          <Typography component="ul" color="textSecondary" sx={{ mt: 2 }}>
            <li>Formats de dates et devises</li>
            <li>Exercice comptable</li>
            <li>Sauvegarde automatique</li>
            <li>Intégrations API</li>
            <li>Import/Export de données</li>
          </Typography>
        </TabPanel>
      </Card>
    </Box>
  );
};

export default SettingsPage;
