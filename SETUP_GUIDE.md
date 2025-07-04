# Compta Maroc - Complete Setup Guide

This guide will help you set up the Compta Maroc accounting application on your local machine.

## System Requirements

- **Operating System**: Windows 10/11, macOS 10.15+, or Linux (Ubuntu 18.04+)
- **Java**: Version 17 or higher
- **Maven**: Version 3.6 or higher
- **Node.js**: Version 18 or higher
- **PostgreSQL**: Version 12 or higher
- **RAM**: Minimum 4GB, recommended 8GB
- **Storage**: At least 2GB free space

## Step-by-Step Installation

### Step 1: Install Prerequisites

#### Java 17
- **Windows**: Download from [Oracle](https://www.oracle.com/java/technologies/downloads/) or use OpenJDK
- **macOS**: `brew install openjdk@17`
- **Linux**: `sudo apt install openjdk-17-jdk`

Verify installation: `java -version`

#### Maven
- **Windows**: Download from [Apache Maven](https://maven.apache.org/download.cgi)
- **macOS**: `brew install maven`
- **Linux**: `sudo apt install maven`

Verify installation: `mvn -version`

#### Node.js and npm
- Download from [nodejs.org](https://nodejs.org/)
- Or use package managers:
  - **macOS**: `brew install node`
  - **Linux**: `sudo apt install nodejs npm`

Verify installation: `node -v && npm -v`

#### PostgreSQL
- **Windows**: Download from [postgresql.org](https://www.postgresql.org/download/)
- **macOS**: `brew install postgresql`
- **Linux**: `sudo apt install postgresql postgresql-contrib`

### Step 2: Database Configuration

1. Start PostgreSQL service:
   - **Windows**: Use Services or pgAdmin
   - **macOS**: `brew services start postgresql`
   - **Linux**: `sudo systemctl start postgresql`

2. Create database and user:
   ```bash
   sudo -u postgres psql
   ```
   
   In PostgreSQL console:
   ```sql
   CREATE DATABASE comptamaroc;
   CREATE USER comptamaroc WITH PASSWORD 'password';
   GRANT ALL PRIVILEGES ON DATABASE comptamaroc TO comptamaroc;
   \q
   ```

### Step 3: Backend Setup

1. Open terminal/command prompt
2. Navigate to the project directory:
   ```bash
   cd path/to/accounting-app
   ```

3. Build the backend:
   ```bash
   mvn clean install
   ```
   
   This may take 5-10 minutes for the first build as it downloads dependencies.

4. Start the backend:
   ```bash
   cd backend/app
   java -jar target/app-0.1.0-SNAPSHOT.jar
   ```

5. Verify backend is running:
   - Open browser and go to `http://localhost:8080/api/v1/auth/login`
   - You should see a 403 error (this is expected for GET request)

### Step 4: Frontend Setup

1. Open a new terminal/command prompt
2. Navigate to frontend directory:
   ```bash
   cd path/to/accounting-app/frontend
   ```

3. Install dependencies:
   ```bash
   npm install
   ```
   
   This may take 3-5 minutes.

4. Start the frontend:
   ```bash
   npm run dev
   ```

5. Open browser and go to `http://localhost:3000`

### Step 5: Initial Login

Use these default credentials:
- **Email**: admin@comptamaroc.com
- **Password**: admin123

## Troubleshooting

### Backend Issues

**Error: "Port 8080 already in use"**
- Solution: Kill the process using port 8080 or change the port in `application.yml`

**Error: "Could not connect to database"**
- Check if PostgreSQL is running
- Verify database credentials in `application.yml`
- Ensure database `comptamaroc` exists

**Error: "Java version not supported"**
- Ensure Java 17+ is installed and set as default
- Check with `java -version`

### Frontend Issues

**Error: "npm command not found"**
- Install Node.js from nodejs.org
- Restart terminal after installation

**Error: "Port 3000 already in use"**
- Kill the process using port 3000
- Or start with different port: `npm run dev -- --port 3001`

**Error: "Failed to fetch" in browser console**
- Ensure backend is running on port 8080
- Check if CORS is properly configured

### Database Issues

**Error: "PostgreSQL service not running"**
- **Windows**: Start via Services or `net start postgresql-x64-13`
- **macOS**: `brew services start postgresql`
- **Linux**: `sudo systemctl start postgresql`

**Error: "Authentication failed"**
- Check username/password in database configuration
- Verify user has proper permissions

## Development Tips

1. **Hot Reload**: Frontend supports hot reload - changes appear automatically
2. **Backend Restart**: Backend needs manual restart after code changes
3. **Database Reset**: To reset database, stop backend and restart with fresh DB
4. **Logs**: Check console output for both frontend and backend for debugging

## Production Deployment

For production deployment, additional steps are required:
1. Build frontend for production: `npm run build`
2. Configure production database
3. Set up reverse proxy (nginx/Apache)
4. Configure SSL certificates
5. Set environment variables for production

## Support

If you encounter issues not covered in this guide:
1. Check the main README.md file
2. Review application logs for specific error messages
3. Ensure all prerequisites are properly installed
4. Verify network connectivity and firewall settings

