# API Testing Guide for Moroccan Accounting Platform

## 🚀 Quick Start

### Prerequisites
- Java 17+ installed
- Maven 3.6+ installed

### Start the Application
```bash
# Windows
./start-test.bat

# Linux/Mac
chmod +x start-test.sh
./start-test.sh
```

The application will start with an H2 in-memory database on **http://localhost:8080**

## 📋 Available Endpoints

### 🌐 Web Interfaces
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **H2 Database Console**: http://localhost:8080/h2-console
- **API Documentation**: http://localhost:8080/api-docs

### 🔐 Authentication Endpoints
Base URL: `http://localhost:8080/api/auth`

#### Register User
```bash
POST /api/auth/register
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "password": "password123",
  "roleName": "USER"
}
```

#### Login
```bash
POST /api/auth/login
Content-Type: application/json

{
  "email": "admin@comptamaroc.com",
  "password": "admin123"
}
```

#### Get Current User
```bash
GET /api/auth/me
Authorization: Bearer {your-jwt-token}
```

### 👥 Client Management
Base URL: `http://localhost:8080/api/clients`

#### Create Client
```bash
POST /api/clients
Authorization: Bearer {your-jwt-token}
Content-Type: application/json

{
  "companyName": "ABC Corporation",
  "contactPerson": "Jane Smith",
  "email": "contact@abc.com",
  "phone": "+212 6 12 34 56 78",
  "address": "123 Business St",
  "city": "Casablanca",
  "country": "Morocco",
  "taxId": "TAX123456"
}
```

#### Get All Clients
```bash
GET /api/clients
Authorization: Bearer {your-jwt-token}
```

#### Search Clients
```bash
GET /api/clients/search?keyword=ABC
Authorization: Bearer {your-jwt-token}
```

### 🏭 Supplier Management
Base URL: `http://localhost:8080/api/suppliers`

#### Create Supplier
```bash
POST /api/suppliers
Authorization: Bearer {your-jwt-token}
Content-Type: application/json

{
  "companyName": "Supplier XYZ",
  "contactPerson": "Ahmed Ben Ali",
  "email": "contact@supplier.ma",
  "phone": "+212 5 22 33 44 55",
  "address": "456 Industrial Zone",
  "city": "Rabat",
  "country": "Morocco",
  "taxId": "SUP789012"
}
```

### 📦 Product Management
Base URL: `http://localhost:8080/api/products`

#### Create Product
```bash
POST /api/products
Authorization: Bearer {your-jwt-token}
Content-Type: application/json

{
  "name": "Laptop Dell",
  "description": "High-performance business laptop",
  "sku": "DELL001",
  "category": "Electronics",
  "unitPrice": 8500.00,
  "costPrice": 6800.00,
  "stockQuantity": 10,
  "minStockLevel": 2
}
```

### 🧾 Invoice Management
Base URL: `http://localhost:8080/api/invoices`

#### Create Invoice
```bash
POST /api/invoices
Authorization: Bearer {your-jwt-token}
Content-Type: application/json

{
  "clientId": "{client-uuid}",
  "invoiceDate": "2024-01-15",
  "dueDate": "2024-02-14",
  "items": [
    {
      "productId": "{product-uuid}",
      "description": "Laptop Dell",
      "quantity": 2,
      "unitPrice": 8500.00
    }
  ],
  "notes": "Payment terms: 30 days"
}
```

### 🧮 VAT Calculations
Base URL: `http://localhost:8080/api/vat`

#### Calculate VAT
```bash
POST /api/vat/calculate?amount=1000&businessType=retail&productType=electronics
Authorization: Bearer {your-jwt-token}
```

#### Simple VAT Calculation
```bash
POST /api/vat/calculate-simple?amount=1000&vatRate=20
Authorization: Bearer {your-jwt-token}
```

### 📊 Dashboard Analytics
Base URL: `http://localhost:8080/api/dashboard`

#### Get Business KPIs
```bash
GET /api/dashboard/kpis
Authorization: Bearer {your-jwt-token}
```

## 🧪 Testing Steps

### 1. Authentication Flow
1. Start the application
2. Open Swagger UI at http://localhost:8080/swagger-ui.html
3. Try the login endpoint with default credentials:
   - Email: `admin@comptamaroc.com`
   - Password: `admin123`
4. Copy the JWT token from the response
5. Use the token in subsequent API calls

### 2. CRUD Operations
1. **Create**: Test creating clients, suppliers, and products
2. **Read**: Test fetching lists and individual records
3. **Update**: Test updating existing records
4. **Delete**: Test soft delete functionality

### 3. Business Logic
1. **VAT Calculations**: Test Moroccan VAT rates and calculations
2. **Invoice Generation**: Create invoices with multiple items
3. **Dashboard Analytics**: Check KPI calculations

### 4. Search and Filtering
1. Test search functionality for clients and suppliers
2. Test pagination with different page sizes
3. Test sorting by different fields

## 🔍 Using Postman

### Import Collection
You can create a Postman collection with these endpoints:

1. Create a new collection "Moroccan Accounting Platform"
2. Add environment variables:
   - `baseUrl`: `http://localhost:8080`
   - `token`: `{{token}}` (will be set after login)
3. Create requests for each endpoint listed above

### Authentication Setup
1. Create a login request
2. In the Tests tab, add:
```javascript
if (pm.response.code === 200) {
    const response = pm.response.json();
    pm.environment.set("token", response.data.accessToken);
}
```

### Request Headers
For authenticated endpoints, add:
```
Authorization: Bearer {{token}}
```

## 🛠️ Troubleshooting

### Common Issues

1. **Application won't start**
   - Check Java 17+ is installed: `java -version`
   - Ensure port 8080 is available

2. **Database connection errors**
   - The test profile uses H2 in-memory database, no setup required
   - For production, ensure PostgreSQL is running

3. **JWT token expired**
   - Login again to get a new token
   - Tokens expire after 24 hours by default

4. **CORS issues**
   - Application is configured to allow CORS for development
   - Access from http://localhost:3000 (for React frontend)

### Logs
Check application logs for detailed error information. Debug mode is enabled in test profile.

## 🎯 Ready for Frontend

Once you verify these endpoints work correctly, you can proceed with frontend development knowing that:

1. ✅ Authentication system is working
2. ✅ CRUD operations are functional
3. ✅ Business logic (VAT, invoicing) is implemented
4. ✅ API responses are properly formatted
5. ✅ Error handling is in place

The API is designed to be consumed by a modern frontend framework like React, Vue.js, or Angular.
