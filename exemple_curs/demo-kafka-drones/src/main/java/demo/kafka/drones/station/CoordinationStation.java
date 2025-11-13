package demo.kafka.drones.station;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import demo.kafka.drones.model.AlertMessage;
import demo.kafka.drones.model.TelemetryMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Coordination Station that monitors all drones in the fleet.
 * Consumes telemetry and alert messages from Kafka topics.
 * Demonstrates Kafka consumer with thread-safe state management.
 */
public class CoordinationStation implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(CoordinationStation.class);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final KafkaConsumer<String, String> consumer;
    private final ObjectMapper objectMapper;
    private final AtomicBoolean running = new AtomicBoolean(true);

    // Thread-safe map to track drone status
    private final Map<String, DroneInfo> droneFleet = new ConcurrentHashMap<>();

    private final String telemetryTopic;
    private final String alertTopic;

    public CoordinationStation(KafkaConsumer<String, String> consumer,
                              String telemetryTopic, String alertTopic) {
        this.consumer = consumer;
        this.telemetryTopic = telemetryTopic;
        this.alertTopic = alertTopic;

        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());

        // Subscribe to topics
        consumer.subscribe(Arrays.asList(telemetryTopic, alertTopic));
    }

    @Override
    public void run() {
        logger.info("Coordination Station starting...");
        printHeader();

        try {
            while (running.get()) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));

                for (ConsumerRecord<String, String> record : records) {
                    processMessage(record);
                }

                // Periodically print fleet status
                if (records.count() > 0) {
                    printFleetStatus();
                }
            }
        } catch (Exception e) {
            logger.error("Error in Coordination Station", e);
        } finally {
            consumer.close();
            logger.info("Coordination Station shut down");
        }
    }

    private void processMessage(ConsumerRecord<String, String> record) {
        try {
            String topic = record.topic();

            if (topic.equals(telemetryTopic)) {
                processTelemetry(record.value());
            } else if (topic.equals(alertTopic)) {
                processAlert(record.value());
            }

        } catch (Exception e) {
            logger.error("Error processing message", e);
        }
    }

    private void processTelemetry(String json) throws Exception {
        TelemetryMessage telemetry = objectMapper.readValue(json, TelemetryMessage.class);

        // Update drone info
        DroneInfo info = droneFleet.computeIfAbsent(
                telemetry.getDroneId(),
                k -> new DroneInfo(telemetry.getDroneId())
        );

        info.updateFromTelemetry(telemetry);

        // Log telemetry
        logger.debug("Telemetry: {}", telemetry);

        // Check for critical conditions
        if (telemetry.getBatteryLevel() <= 20) {
            System.out.printf("[%s] ⚠️  LOW BATTERY WARNING: %s at %d%%%n",
                    TIME_FORMATTER.format(LocalDateTime.now()),
                    telemetry.getDroneId(),
                    telemetry.getBatteryLevel());
        }
    }

    private void processAlert(String json) throws Exception {
        AlertMessage alert = objectMapper.readValue(json, AlertMessage.class);

        // Update alert count
        DroneInfo info = droneFleet.get(alert.getDroneId());
        if (info != null) {
            info.incrementAlertCount();
        }

        // Display alert with color coding
        String icon = getAlertIcon(alert.getSeverity());
        System.out.printf("[%s] %s ALERT from %s: %s - %s%n",
                TIME_FORMATTER.format(alert.getTimestamp()),
                icon,
                alert.getDroneId(),
                alert.getAlertType(),
                alert.getMessage());
    }

    private String getAlertIcon(demo.kafka.drones.model.AlertSeverity severity) {
        return switch (severity) {
            case INFO -> "ℹ️";
            case WARNING -> "⚠️";
            case CRITICAL -> "🚨";
        };
    }

    private void printHeader() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("         DRONE FLEET COORDINATION STATION - MONITORING ACTIVE");
        System.out.println("=".repeat(80));
    }

    private void printFleetStatus() {
        System.out.println("\n--- Fleet Status Dashboard ---");
        System.out.printf("Total Drones: %d | Active: %d%n",
                droneFleet.size(),
                droneFleet.values().stream()
                        .filter(d -> d.lastTelemetry != null)
                        .count());

        droneFleet.values().forEach(info -> {
            if (info.lastTelemetry != null) {
                TelemetryMessage t = info.lastTelemetry;
                System.out.printf("  [%s] Status: %-12s | Battery: %3d%% | Alt: %6.1fm | Pos: (%.4f, %.4f) | Alerts: %d%n",
                        info.droneId,
                        t.getStatus(),
                        t.getBatteryLevel(),
                        t.getAltitude(),
                        t.getLatitude(),
                        t.getLongitude(),
                        info.alertCount);
            }
        });
        System.out.println();
    }

    public void shutdown() {
        logger.info("Shutdown requested for Coordination Station");
        running.set(false);
    }

    /**
     * Thread-safe class to track individual drone information
     */
    private static class DroneInfo {
        private final String droneId;
        private TelemetryMessage lastTelemetry;
        private int alertCount = 0;

        DroneInfo(String droneId) {
            this.droneId = droneId;
        }

        synchronized void updateFromTelemetry(TelemetryMessage telemetry) {
            this.lastTelemetry = telemetry;
        }

        synchronized void incrementAlertCount() {
            this.alertCount++;
        }
    }
}
