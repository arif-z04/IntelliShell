#!/bin/bash

# IntelliShell - Run Script for Linux/Mac
# This script automatically runs IntelliShell with the compiled jar

# Get the directory where this script is located
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# Path to the jar file
JAR_FILE="$SCRIPT_DIR/target/intellishell-0.1.0-jar-with-dependencies.jar"

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Error: Java is not installed or not in PATH"
    echo "Please install Java 17 or higher and try again."
    exit 1
fi

# Check if jar file exists
if [ ! -f "$JAR_FILE" ]; then
    echo "❌ Error: JAR file not found at $JAR_FILE"
    echo ""
    echo "Please build the project first:"
    echo "  cd $SCRIPT_DIR"
    echo "  mvn clean package"
    exit 1
fi

# Get Java version
JAVA_VERSION=$(java -version 2>&1 | grep -oP '(?<=")\d+' | head -1)

if [ -z "$JAVA_VERSION" ] || [ "$JAVA_VERSION" -lt 17 ]; then
    echo "⚠️  Warning: Java 17+ is recommended. Current version: $JAVA_VERSION"
fi

# Run IntelliShell
echo "🚀 Starting IntelliShell..."
echo ""
java -jar "$JAR_FILE"
