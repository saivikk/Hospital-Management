@echo off
title Hospital Management System
color 0B
echo.
echo ========================================
echo    Hospital Management System v2.0
echo         Enhanced GUI Edition
echo ========================================
echo.
echo Compiling application...
javac src/models/*.java src/database/*.java src/gui/*.java src/*.java

if %errorlevel% equ 0 (
    echo ✓ Compilation successful!
    echo.
    echo Starting Hospital Management System...
    echo Please wait while the GUI loads...
    echo.
    java -cp src HospitalManagementApp
) else (
    echo ✗ Compilation failed!
    echo Please check for syntax errors.
    pause
)