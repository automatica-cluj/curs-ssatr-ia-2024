package demo.kafka.drones.drone;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import demo.kafka.drones.model.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Simulates a drone that sends telemetry and alert messages to Kafka.
 * Each drone runs in its own thread, demonstrating multi-threading with Kafka.
 */
public class DroneSimulator implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(DroneSimulator.class);

    private final String droneId;
    private final KafkaProducer<String, String> producer;
    private final ObjectMapper objectMapper;
    private final AtomicBoolean running = new AtomicBoolean(true);
    private final Random random = new Random();

    // Telemetry configuration
    private final String telemetryTopic;
    private final String alertTopic;
    private final int telemetryIntervalMs;

    // Drone state
    private double latitude;
    private double longitude;
    private double altitude;
    private int batteryLevel;
    private DroneStatus status;
    private String missionId;

    public DroneSimulator(String droneId, KafkaProducer<String, String> producer,
                         String telemetryTopic, String alertTopic,
                         int telemetryIntervalMs, double startLat, double startLon) {
        this.droneId = droneId;
        this.producer = producer;
        this.telemetryTopic = telemetryTopic;
        this.alertTopic = alertTopic;
        this.telemetryIntervalMs = telemetryIntervalMs;

        // Initialize drone state
        this.latitude = startLat;
        this.longitude = startLon;
        this.altitude = 0.0;
        this.batteryLevel = 100;
        this.status = DroneStatus.IDLE;
        this.missionId = "MISSION-" + droneId + "-" + System.currentTimeMillis();

        // Configure ObjectMapper for JSON serialization
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    public void run() {
        logger.info("Drone {} starting simulation", droneId);

        try {
            // Simulate drone lifecycle
            simulateTakeoff();

            while (running.get() && batteryLevel > 15) {
                simulateFlight();
                sendTelemetry();
                Thread.sleep(telemetryIntervalMs);
            }

            // Low battery alert
            if (batteryLevel <= 15) {
                sendAlert(AlertType.LOW_BATTERY, AlertSeverity.CRITICAL,
                        "Battery critically low at " + batteryLevel + "%");
                simulateReturn();
            }

            simulateLanding();

        } catch (InterruptedException e) {
            logger.info("Drone {} interrupted", droneId);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            logger.error("Error in drone {} simulation", droneId, e);
        } finally {
            logger.info("Drone {} shutting down", droneId);
        }
    }

    private void simulateTakeoff() throws Exception {
        status = DroneStatus.TAKEOFF;
        logger.info("Drone {} taking off", droneId);

        for (int i = 0; i < 5; i++) {
            altitude += 20.0; // Climb 20 meters
            batteryLevel -= 1;
            sendTelemetry();
            Thread.sleep(1000);
        }

        status = DroneStatus.IN_FLIGHT;
        sendAlert(AlertType.MISSION_COMPLETE, AlertSeverity.INFO,
                "Takeoff complete, starting mission");
    }

    private void simulateFlight() {
        // Simulate movement
        latitude += (random.nextDouble() - 0.5) * 0.001; // Small random movement
        longitude += (random.nextDouble() - 0.5) * 0.001;

        // Vary altitude slightly
        altitude += (random.nextDouble() - 0.5) * 5.0;
        altitude = Math.max(50.0, Math.min(150.0, altitude)); // Keep between 50-150m

        // Drain battery
        batteryLevel -= random.nextInt(2) + 1; // Drain 1-2% per interval

        // Random events
        if (random.nextInt(100) < 5) { // 5% chance
            status = DroneStatus.HOVERING;
        } else {
            status = DroneStatus.IN_FLIGHT;
        }

        // Check for alerts
        if (batteryLevel <= 30 && batteryLevel > 15) {
            sendAlert(AlertType.LOW_BATTERY, AlertSeverity.WARNING,
                    "Battery level at " + batteryLevel + "%");
        }
    }

    private void simulateReturn() throws Exception {
        status = DroneStatus.RETURNING;
        logger.info("Drone {} returning to base", droneId);

        for (int i = 0; i < 3; i++) {
            sendTelemetry();
            Thread.sleep(2000);
        }
    }

    private void simulateLanding() throws Exception {
        status = DroneStatus.LANDING;
        logger.info("Drone {} landing", droneId);

        while (altitude > 0) {
            altitude = Math.max(0, altitude - 20.0);
            sendTelemetry();
            Thread.sleep(1000);
        }

        status = DroneStatus.IDLE;
        sendAlert(AlertType.MISSION_COMPLETE, AlertSeverity.INFO,
                "Mission completed, drone landed safely");
        sendTelemetry();
    }

    private void sendTelemetry() {
        try {
            TelemetryMessage telemetry = new TelemetryMessage(
                    droneId, latitude, longitude, altitude,
                    batteryLevel, status, missionId
            );

            String json = objectMapper.writeValueAsString(telemetry);
            ProducerRecord<String, String> record =
                    new ProducerRecord<>(telemetryTopic, droneId, json);

            producer.send(record, (metadata, exception) -> {
                if (exception != null) {
                    logger.error("Error sending telemetry from {}", droneId, exception);
                }
            });

        } catch (Exception e) {
            logger.error("Error creating telemetry message for {}", droneId, e);
        }
    }

    private void sendAlert(AlertType alertType, AlertSeverity severity, String message) {
        try {
            AlertMessage alert = new AlertMessage(
                    droneId, alertType, severity, message, latitude, longitude
            );

            String json = objectMapper.writeValueAsString(alert);
            ProducerRecord<String, String> record =
                    new ProducerRecord<>(alertTopic, droneId, json);

            producer.send(record, (metadata, exception) -> {
                if (exception != null) {
                    logger.error("Error sending alert from {}", droneId, exception);
                } else {
                    logger.info("Alert sent from {}: {}", droneId, message);
                }
            });

        } catch (Exception e) {
            logger.error("Error creating alert message for {}", droneId, e);
        }
    }

    public void shutdown() {
        logger.info("Shutdown requested for drone {}", droneId);
        running.set(false);
    }

    public String getDroneId() {
        return droneId;
    }
}
