@echo off
echo Starting Moroccan Accounting Platform with test profile...
java -jar "backend\app\target\app-0.1.0-SNAPSHOT.jar" --spring.profiles.active=test
pause
