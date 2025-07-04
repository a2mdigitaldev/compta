@echo off
echo Testing CORS configuration for Moroccan Accounting Platform
echo.

echo Making a preflight OPTIONS request to test CORS...
curl -X OPTIONS ^
  -H "Origin: http://localhost:3000" ^
  -H "Access-Control-Request-Method: POST" ^
  -H "Access-Control-Request-Headers: Content-Type,Authorization" ^
  -v http://localhost:8080/api/auth/login

echo.
echo.
echo Testing actual login request...
curl -X POST ^
  -H "Content-Type: application/json" ^
  -H "Origin: http://localhost:3000" ^
  -d "{\"email\":\"admin@comptamaroc.com\",\"password\":\"admin123\"}" ^
  -v http://localhost:8080/api/auth/login

pause
