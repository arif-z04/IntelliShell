#!/bin/bash

# IntelliShell - Update Script
# Automatically runs tests and rebuilds the program
# Usage: ./update.sh

set -e  # Exit on error

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
PROJECT_NAME="IntelliShell"
BUILD_LOG="$SCRIPT_DIR/build.log"
TIMESTAMP=$(date '+%Y-%m-%d %H:%M:%S')

# Color codes
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Helper functions
print_header() {
    echo -e "\n${BLUE}╔════════════════════════════════════════════════════════════════╗${NC}"
    echo -e "${BLUE}║ $1${NC}"
    echo -e "${BLUE}╚════════════════════════════════════════════════════════════════╝${NC}\n"
}

print_success() {
    echo -e "${GREEN}✓ $1${NC}"
}

print_error() {
    echo -e "${RED}✗ $1${NC}"
}

print_warning() {
    echo -e "${YELLOW}⚠ $1${NC}"
}

print_info() {
    echo -e "${BLUE}ℹ $1${NC}"
}

# Check if Java is installed
check_java() {
    if ! command -v java &> /dev/null; then
        print_error "Java is not installed or not in PATH"
        exit 1
    fi
    
    JAVA_VERSION=$(java -version 2>&1 | grep -oP '(?<=")\d+' | head -1)
    print_success "Java found (version $JAVA_VERSION)"
}

# Check if Maven is installed
check_maven() {
    if ! command -v mvn &> /dev/null; then
        print_error "Maven is not installed or not in PATH"
        exit 1
    fi
    
    MAVEN_VERSION=$(mvn -v 2>&1 | grep "Apache Maven" | awk '{print $3}')
    print_success "Maven found (version $MAVEN_VERSION)"
}

# Run tests
run_tests() {
    print_header "Running Tests"
    
    if mvn test -q >> "$BUILD_LOG" 2>&1; then
        TEST_COUNT=$(grep -c "Tests run:" "$BUILD_LOG" || echo "0")
        print_success "All tests passed ✓"
        return 0
    else
        print_error "Tests failed!"
        echo -e "\n${YELLOW}Test Output:${NC}"
        grep -A 10 "FAILURE\|ERROR\|Tests run:" "$BUILD_LOG" | tail -20
        return 1
    fi
}

# Clean build
clean_build() {
    print_header "Building Project (Clean Build)"
    
    if mvn clean package -DskipTests -q >> "$BUILD_LOG" 2>&1; then
        print_success "Project built successfully ✓"
        
        # Check jar file
        if [ -f "$SCRIPT_DIR/target/intellishell-0.1.0-jar-with-dependencies.jar" ]; then
            JAR_SIZE=$(du -h "$SCRIPT_DIR/target/intellishell-0.1.0-jar-with-dependencies.jar" | cut -f1)
            print_success "JAR file ready ($JAR_SIZE)"
        fi
        return 0
    else
        print_error "Build failed!"
        echo -e "\n${YELLOW}Build Output:${NC}"
        tail -30 "$BUILD_LOG"
        return 1
    fi
}

# Verify jar is executable
verify_jar() {
    print_header "Verifying JAR File"
    
    if [ -f "$SCRIPT_DIR/target/intellishell-0.1.0-jar-with-dependencies.jar" ]; then
        print_success "JAR file exists"
        
        # Test if jar can be executed
        if java -jar "$SCRIPT_DIR/target/intellishell-0.1.0-jar-with-dependencies.jar" <<< "exit" &>/dev/null; then
            print_success "JAR is executable"
        else
            print_warning "Could not verify JAR execution (may require interactive terminal)"
        fi
        return 0
    else
        print_error "JAR file not found after build!"
        return 1
    fi
}

# Show summary
show_summary() {
    print_header "Update Complete - Summary"
    
    echo "Project:        $PROJECT_NAME"
    echo "Timestamp:      $TIMESTAMP"
    echo "Build Log:      $BUILD_LOG"
    echo ""
    echo -e "${GREEN}Status: ALL CHECKS PASSED ✓${NC}"
    echo ""
    echo "You can now run your application:"
    echo -e "  ${BLUE}./run.sh${NC}    (Linux/Mac)"
    echo -e "  ${BLUE}run.bat${NC}     (Windows)"
}

# Main execution
main() {
    print_header "IntelliShell Update & Build Process"
    
    print_info "Starting at: $TIMESTAMP"
    print_info "Project directory: $SCRIPT_DIR"
    
    # Clear log
    > "$BUILD_LOG"
    
    # Check prerequisites
    print_header "Checking Prerequisites"
    check_java
    check_maven
    
    # Run tests
    if ! run_tests; then
        print_error "Tests failed! Aborting build."
        echo -e "\n${YELLOW}Fix the failing tests and run ./update.sh again${NC}\n"
        exit 1
    fi
    
    # Clean build
    if ! clean_build; then
        print_error "Build failed! Check build.log for details."
        echo -e "\n${YELLOW}Build log: $BUILD_LOG${NC}\n"
        exit 1
    fi
    
    # Verify jar
    if ! verify_jar; then
        print_error "JAR verification failed!"
        exit 1
    fi
    
    # Success!
    show_summary
    
    print_info "Log file saved to: $BUILD_LOG"
    print_success "Update complete!"
    echo ""
}

# Handle errors
trap 'print_error "Script interrupted!"; exit 1' INT TERM

# Run main
main
