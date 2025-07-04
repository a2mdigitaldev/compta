@echo off
echo ========================================
echo  Compta Maroc - Frontend Development
echo ========================================
echo.

echo Checking Node.js installation...
node --version
if %errorlevel% neq 0 (
    echo ERROR: Node.js is not installed
    pause
    exit /b 1
)

echo.
echo Starting development server...
echo Frontend: http://localhost:3000
echo Backend API: http://localhost:8080/api
echo.
echo Press Ctrl+C to stop the server
echo ========================================
echo.

npm run dev
