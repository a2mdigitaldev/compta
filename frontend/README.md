# Compta Maroc - Frontend

Une application web moderne pour la gestion comptable des entreprises marocaines.

## 🚀 Technologies

- **React 18** - Framework frontend moderne
- **TypeScript** - Typage statique pour JavaScript
- **Material-UI (MUI)** - Composants UI avec thème marocain
- **Vite** - Outil de build rapide et moderne
- **React Router** - Navigation côté client
- **Axios** - Client HTTP pour les appels API
- **React Hook Form** - Gestion des formulaires
- **Yup** - Validation des schémas

## 🎨 Design

L'interface utilise les couleurs du drapeau marocain :
- **Rouge** (#C41E3A) - Couleur principale
- **Vert** (#228B22) - Couleur secondaire
- Interface en français adaptée au marché marocain

## 📁 Structure du Projet

```
src/
├── components/           # Composants réutilisables
│   ├── auth/            # Composants d'authentification
│   └── layout/          # Composants de mise en page
├── pages/               # Pages de l'application
│   ├── auth/            # Pages de connexion/inscription
│   ├── dashboard/       # Tableau de bord
│   ├── clients/         # Gestion des clients
│   ├── suppliers/       # Gestion des fournisseurs
│   ├── products/        # Catalogue produits
│   ├── invoices/        # Facturation
│   ├── accounting/      # Comptabilité
│   ├── reports/         # Rapports
│   └── settings/        # Paramètres
├── services/            # Services API
├── context/             # Contextes React
├── hooks/               # Hooks personnalisés
├── types/               # Types TypeScript
├── utils/               # Utilitaires
└── theme/               # Thème Material-UI
```

## 🛠️ Installation et Démarrage

### Prérequis
- Node.js 18+ 
- npm ou yarn

### Installation
```bash
# Installer les dépendances
npm install

# Démarrer en mode développement
npm run dev

# Construire pour la production
npm run build

# Prévisualiser la build
npm run preview
```

## 🔌 Configuration Backend

L'application se connecte au backend Spring Boot :
- **URL par défaut** : `http://localhost:8080/api`
- **Authentification** : JWT avec refresh tokens
- **Format des réponses** : `{ success: boolean, message: string, data: T }`

### Variables d'environnement
Créer un fichier `.env.local` :
```env
VITE_API_BASE_URL=http://localhost:8080/api
```

## 🔐 Authentification

### Comptes de test
- **Admin** : admin@comptamaroc.com / admin123
- **Comptable** : accountant@comptamaroc.com / accountant123

### Fonctionnalités
- Connexion/Déconnexion
- Inscription de nouveaux utilisateurs
- Gestion des rôles (Admin, Comptable, Utilisateur)
- Refresh automatique des tokens
- Protection des routes

## 📱 Fonctionnalités

### ✅ Implémentées
- 🔐 Système d'authentification complet
- 📊 Tableau de bord avec statistiques
- 🏗️ Structure de navigation complète
- 🎨 Thème marocain personnalisé
- 📱 Design responsive
- 🔒 Protection des routes

### 🚧 En développement
- 👥 Gestion des clients et fournisseurs
- 📦 Catalogue produits avec stocks
- 🧾 Système de facturation
- 📈 Comptabilité (PCMN)
- 📊 Rapports financiers
- ⚙️ Paramètres avancés

## 🇲🇦 Spécificités Marocaines

- **Plan Comptable** : Conforme au PCMN (Plan Comptable Marocain Normalisé)
- **TVA** : Taux marocains (20%, 14%, 10%, 7%, 0%)
- **Devise** : Formatage en Dirhams (MAD)
- **Langue** : Interface en français
- **Réglementation** : Conforme aux standards comptables marocains

## 🚀 Déploiement

### Build de production
```bash
npm run build
```

### Serveur de prévisualisation
```bash
npm run preview
```

Le dossier `dist/` contient les fichiers optimisés pour la production.

## 🤝 Développement

### Commandes utiles
```bash
# Linter
npm run lint

# Build avec vérification de types
npm run build

# Mode développement avec hot reload
npm run dev
```

### Conventions de code
- Composants fonctionnels avec hooks
- TypeScript pour tous les fichiers
- Utilisation des composants Material-UI
- Nommage sémantique en français
- Gestion d'erreurs et états de chargement

---

**Plateforme Comptable Marocaine** - Solution moderne pour la gestion comptable des entreprises marocaines.
