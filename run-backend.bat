@echo off
title Moroccan Accounting Platform - Backend Server

echo =========================================
echo  Moroccan Accounting Platform Backend
echo =========================================
echo.

echo Checking Java installation...
java -version
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java 17 or later
    pause
    exit /b 1
)

echo.
echo Checking JAR file...
if not exist "backend\app\target\app-0.1.0-SNAPSHOT.jar" (
    echo ERROR: Application JAR not found
    echo Please run: mvn clean package
    pause
    exit /b 1
)

echo.
echo Starting Moroccan Accounting Platform...
echo Profile: TEST (H2 Database)
echo Port: 8080
echo.
echo Available URLs:
echo   Application: http://localhost:8080
echo   Swagger UI:  http://localhost:8080/swagger-ui.html
echo   H2 Console:  http://localhost:8080/h2-console
echo.
echo Default Test Users:
echo   Admin: admin@comptamaroc.com / admin123
echo   Accountant: accountant@comptamaroc.com / accountant123
echo.
echo Press Ctrl+C to stop the server
echo =========================================
echo.

java -jar "backend\app\target\app-0.1.0-SNAPSHOT.jar" --spring.profiles.active=test --server.port=8080

echo.
echo =========================================
echo Server stopped.
echo =========================================
pause
