#!/bin/bash

echo "=========================================="
echo "Drone Fleet Management System - Launcher"
echo "=========================================="

# Check if Kafka is running
if ! docker ps | grep -q "drone-kafka"; then
    echo ""
    echo "Kafka is not running. Starting Kafka..."
    docker-compose up -d
    echo "Waiting 30 seconds for Kafka to initialize..."
    sleep 30
fi

echo ""
echo "Building application..."
mvn clean package -q

echo ""
echo "Starting Drone Fleet Application..."
echo ""
java -jar target/demo-kafka-drones-1.0-SNAPSHOT.jar
