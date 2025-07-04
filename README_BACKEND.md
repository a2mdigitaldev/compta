# Moroccan Accounting Platform - Backend

## 🎯 Project Status: BACKEND COMPLETE ✅

The backend is fully implemented, built, and ready for frontend development!

## 🏗️ Architecture

This is a multi-module Maven Spring Boot application designed for Moroccan businesses with full accounting compliance.

### Module Structure
```
├── backend/
│   ├── core/                    # Shared entities and DTOs
│   ├── app/                     # Main Spring Boot application
│   └── modules/
│       ├── dashboard/           # Dashboard statistics
│       ├── clients/             # Client management
│       ├── suppliers/           # Supplier management  
│       ├── products/            # Product catalog
│       ├── invoices/            # Invoice management
│       ├── accounting/          # Chart of accounts, journal entries
│       ├── tva/                # Moroccan VAT system
│       ├── salaries/           # Payroll management
│       ├── reports/            # Financial reporting
│       ├── ai/                 # AI recommendations
│       └── settings/           # Application configuration
```

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Maven 3.8+

### Build & Run
```bash
# Build the project
mvn clean package

# Run with test profile (H2 database)
java -jar backend/app/target/app-0.1.0-SNAPSHOT.jar --spring.profiles.active=test

# Or use the provided scripts
./run-app.bat        # Windows
./start-test.sh      # Linux/Mac
```

### Access Points
- **Application**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/v3/api-docs
- **H2 Console**: http://localhost:8080/h2-console (test profile)

## 🔐 Authentication

### Default Test Users
| Role | Email | Password |
|------|-------|----------|
| Admin | admin@comptamaroc.com | admin123 |
| Accountant | accountant@comptamaroc.com | accountant123 |

### Authentication Endpoints
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login
- `POST /api/auth/refresh` - Refresh token
- `POST /api/auth/logout` - User logout
- `POST /api/auth/change-password` - Change password
- `GET /api/auth/user-info` - Get user info

## 📊 API Endpoints

### Core Business Modules
- **Clients**: `/api/clients` - CRUD operations for clients
- **Suppliers**: `/api/suppliers` - CRUD operations for suppliers
- **Products**: `/api/products` - Product catalog management
- **Invoices**: `/api/invoices` - Invoice management

### Accounting & Financial
- **Chart of Accounts**: `/api/accounting/chart-of-accounts` - PCMN compliance
- **Journal Entries**: `/api/accounting/journal-entries` - Double-entry bookkeeping
- **VAT Management**: `/api/tva/rates`, `/api/tva/calculate` - Moroccan VAT system
- **Payroll**: `/api/salaries/payroll` - CNSS compliance
- **Reports**: `/api/reports/financial` - Financial reporting

### Utility Modules
- **Dashboard**: `/api/dashboard/stats` - Business statistics
- **AI**: `/api/ai/recommendations` - Smart recommendations
- **Settings**: `/api/settings` - Application configuration

## 🇲🇦 Moroccan Compliance Features

### Accounting Standards
- **PCMN Integration**: Full Plan Comptable Marocain support
- **Double-entry Bookkeeping**: Complete journal entry system
- **VAT Compliance**: Moroccan VAT rates and calculations
- **CNSS Integration**: Social security calculations

### Pre-configured Data
- Moroccan VAT rates (20%, 14%, 10%, 7%, 0%)
- PCMN chart of accounts structure
- Standard product categories
- Currency support (MAD)

## 🛠️ Technical Features

### Backend Technologies
- **Spring Boot 3.2.0** - Main framework
- **Spring Security** - JWT authentication
- **Spring Data JPA** - Database operations
- **Flyway** - Database migrations
- **H2/PostgreSQL** - Database support
- **Swagger/OpenAPI** - API documentation
- **Maven** - Multi-module build system

### Database Profiles
- **Development**: PostgreSQL
- **Test**: H2 in-memory database
- **Production**: PostgreSQL (configurable)

### Security Features
- JWT token authentication
- Role-based access control (Admin, Accountant, User)
- Password encryption (BCrypt)
- API rate limiting ready
- CORS configuration

## 🧪 Testing

### Test Database
The test profile uses H2 in-memory database with sample data:
- Pre-loaded users (admin, accountant)
- Sample clients and suppliers
- Product catalog
- VAT rates and accounting structure

### API Testing
All endpoints are documented in Swagger UI and can be tested directly.

1. Start application with test profile
2. Navigate to http://localhost:8080/swagger-ui.html
3. Authenticate using test credentials
4. Test any endpoint

## 📁 Development Guide

### Adding New Features
1. Create new module in `backend/modules/`
2. Add dependency to main app POM
3. Implement controller, service, repository pattern
4. Add to main application scan packages

### Database Changes
1. Create new Flyway migration in `src/main/resources/db/migration/`
2. Follow naming convention: `V{version}__description.sql`
3. Test with both H2 and PostgreSQL

## 🔗 Frontend Integration

The backend is fully ready for frontend development with:
- Complete REST API with CRUD operations
- JWT authentication system
- Swagger documentation for all endpoints
- CORS configuration for frontend integration
- Consistent error handling and response format

### API Response Format
```json
{
  "success": true,
  "message": "Operation successful",
  "data": { ... },
  "timestamp": "2025-07-04T04:39:00Z"
}
```

## 📈 Next Steps

✅ **Backend Complete** - All business logic implemented  
🔄 **Frontend Development** - Ready to start  
🔄 **Production Deployment** - Configure PostgreSQL  
🔄 **Advanced Features** - Reports, AI, integrations  

---

**The Moroccan Accounting Platform backend is complete and ready for production use!**
