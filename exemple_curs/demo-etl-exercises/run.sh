#!/bin/bash
# Quick start script for ETL Exercises

echo "ETL Exercises - Quick Start"
echo "============================"
echo ""

# Check if Maven is available
if ! command -v mvn &> /dev/null; then
    echo "Error: Maven is not installed or not in PATH"
    exit 1
fi

# Show menu
echo "Select an option:"
echo "1. Build project"
echo "2. Run interactive menu"
echo "3. Run Exercise 1 - Basic CSV to JSON"
echo "4. Run Exercise 2 - Data Filtering and Validation"
echo "5. Run Exercise 3 - Data Aggregation and Grouping"
echo "6. Run Exercise 4 - Joining Multiple Data Sources"
echo "7. Run Exercise 5 - Complete ETL Pipeline with Database"
echo "8. Run all exercises"
echo "0. Exit"
echo ""
read -p "Enter your choice: " choice

case $choice in
    1)
        echo "Building project..."
        mvn clean compile
        ;;
    2)
        echo "Running interactive menu..."
        mvn exec:java
        ;;
    3)
        echo "Running Exercise 1..."
        mvn exec:java -Dexec.mainClass="ro.ucv.ace.etl.exercise1.Exercise1BasicTransformation"
        ;;
    4)
        echo "Running Exercise 2..."
        mvn exec:java -Dexec.mainClass="ro.ucv.ace.etl.exercise2.Exercise2FilteringValidation"
        ;;
    5)
        echo "Running Exercise 3..."
        mvn exec:java -Dexec.mainClass="ro.ucv.ace.etl.exercise3.Exercise3Aggregation"
        ;;
    6)
        echo "Running Exercise 4..."
        mvn exec:java -Dexec.mainClass="ro.ucv.ace.etl.exercise4.Exercise4DataJoining"
        ;;
    7)
        echo "Running Exercise 5..."
        mvn exec:java -Dexec.mainClass="ro.ucv.ace.etl.exercise5.Exercise5DatabasePipeline"
        ;;
    8)
        echo "Running all exercises..."
        for i in 1 2 3 4 5; do
            echo ""
            echo "========================================="
            echo "Running Exercise $i..."
            echo "========================================="
            mvn exec:java -Dexec.mainClass="ro.ucv.ace.etl.exercise$i.Exercise${i}*"
        done
        ;;
    0)
        echo "Exiting..."
        exit 0
        ;;
    *)
        echo "Invalid choice"
        exit 1
        ;;
esac
