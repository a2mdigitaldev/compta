#!/bin/bash
echo "Starting Moroccan Accounting Platform for Testing..."
echo ""
echo "Starting with H2 in-memory database..."
echo "Web UI will be available at: http://localhost:8080"
echo "Swagger UI will be available at: http://localhost:8080/swagger-ui.html"
echo "H2 Console will be available at: http://localhost:8080/h2-console"
echo ""
echo "Default credentials:"
echo "Email: admin@comptamaroc.com"
echo "Password: admin123"
echo ""
echo "Press Ctrl+C to stop the application"
echo ""

java -jar backend/app/target/app-0.1.0-SNAPSHOT.jar --spring.profiles.active=test
