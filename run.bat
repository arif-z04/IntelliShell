@echo off
REM IntelliShell - Run Script for Windows
REM This script automatically runs IntelliShell with the compiled jar

setlocal enabledelayedexpansion

REM Get the directory where this script is located
set "SCRIPT_DIR=%~dp0"

REM Path to the jar file
set "JAR_FILE=%SCRIPT_DIR%target\intellishell-0.1.0-jar-with-dependencies.jar"

REM Check if Java is installed
java -version >nul 2>&1
if errorlevel 1 (
    echo Error: Java is not installed or not in PATH
    echo Please install Java 17 or higher and try again.
    pause
    exit /b 1
)

REM Check if jar file exists
if not exist "%JAR_FILE%" (
    echo Error: JAR file not found at %JAR_FILE%
    echo.
    echo Please build the project first:
    echo   cd %SCRIPT_DIR%
    echo   mvn clean package
    echo.
    pause
    exit /b 1
)

REM Run IntelliShell
echo Starting IntelliShell...
echo.
java -jar "%JAR_FILE%"

if errorlevel 1 (
    echo.
    echo An error occurred while running IntelliShell.
    pause
)
