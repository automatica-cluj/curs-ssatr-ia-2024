package demo.kafka.drones;

import demo.kafka.drones.command.CommandCenter;
import demo.kafka.drones.drone.DroneSimulator;
import demo.kafka.drones.station.CoordinationStation;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main application for the Drone Fleet Management System.
 * Demonstrates Apache Kafka with multi-threading:
 * - Multiple drone simulators running in separate threads (Kafka Producers)
 * - Coordination station monitoring all drones (Kafka Consumer)
 * - Command center for sending commands to drones (Kafka Producer)
 *
 * Educational Topics:
 * - Kafka producers and consumers
 * - Multi-threading and thread management
 * - Thread-safe data structures (ConcurrentHashMap, AtomicBoolean)
 * - Graceful shutdown handling
 * - JSON serialization/deserialization
 */
public class DroneFleetApplication {

    private static final Logger logger = LoggerFactory.getLogger(DroneFleetApplication.class);

    // Kafka topics
    private static final String TELEMETRY_TOPIC = "drone-telemetry";
    private static final String ALERT_TOPIC = "drone-alerts";
    private static final String COMMAND_TOPIC = "drone-commands";

    // Configuration
    private static final int NUM_DRONES = 3;
    private static final int TELEMETRY_INTERVAL_MS = 3000; // 3 seconds

    private final List<Thread> droneThreads = new ArrayList<>();
    private final List<DroneSimulator> drones = new ArrayList<>();
    private Thread stationThread;
    private CoordinationStation station;
    private CommandCenter commandCenter;

    public static void main(String[] args) {
        DroneFleetApplication app = new DroneFleetApplication();

        // Add shutdown hook for graceful termination
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Shutdown hook triggered");
            app.shutdown();
        }));

        try {
            app.start();
        } catch (Exception e) {
            logger.error("Application error", e);
            app.shutdown();
            System.exit(1);
        }
    }

    public void start() throws InterruptedException {
        printWelcomeBanner();

        // Wait for user to ensure Kafka is running
        System.out.println("\n⚠️  Make sure Kafka is running!");
        System.out.println("Run: docker-compose up -d");
        System.out.print("\nPress ENTER when Kafka is ready...");
        new Scanner(System.in).nextLine();

        logger.info("Starting Drone Fleet Management System");

        // Initialize Kafka producers for drones
        KafkaProducer<String, String> droneProducer =
                new KafkaProducer<>(KafkaConfigUtil.createProducerConfig());

        // Initialize Kafka consumer for coordination station
        KafkaConsumer<String, String> stationConsumer =
                new KafkaConsumer<>(KafkaConfigUtil.createConsumerConfig("coordination-station"));

        // Initialize command center producer
        KafkaProducer<String, String> commandProducer =
                new KafkaProducer<>(KafkaConfigUtil.createProducerConfig());
        commandCenter = new CommandCenter(commandProducer, COMMAND_TOPIC);

        // Start Coordination Station in a separate thread
        station = new CoordinationStation(stationConsumer, TELEMETRY_TOPIC, ALERT_TOPIC);
        stationThread = new Thread(station, "CoordinationStation");
        stationThread.start();
        logger.info("Coordination Station started");

        // Give the station time to start
        Thread.sleep(2000);

        // Start multiple drone simulators, each in its own thread
        double[][] startPositions = {
                {46.7712, 23.6236},  // Cluj-Napoca area
                {46.7800, 23.6100},
                {46.7650, 23.6350}
        };

        for (int i = 0; i < NUM_DRONES; i++) {
            String droneId = String.format("DRONE-%03d", i + 1);
            DroneSimulator drone = new DroneSimulator(
                    droneId,
                    droneProducer,
                    TELEMETRY_TOPIC,
                    ALERT_TOPIC,
                    TELEMETRY_INTERVAL_MS,
                    startPositions[i][0],
                    startPositions[i][1]
            );

            drones.add(drone);
            Thread droneThread = new Thread(drone, droneId);
            droneThreads.add(droneThread);
            droneThread.start();

            logger.info("Started drone: {}", droneId);
            Thread.sleep(500); // Stagger drone starts
        }

        System.out.println("\n✅ All systems operational!");
        System.out.println("📡 " + NUM_DRONES + " drones active and transmitting");
        System.out.println("🎮 Command Center ready");

        // Demo: Send some commands after a delay
        Thread.sleep(5000);
        demonstrateCommands();

        // Wait for all drone threads to complete
        for (Thread thread : droneThreads) {
            thread.join();
        }

        logger.info("All drones have completed their missions");

        // Keep station running for a bit to receive final messages
        Thread.sleep(3000);

        shutdown();
    }

    private void demonstrateCommands() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("         DEMONSTRATING COMMAND CENTER CAPABILITIES");
        System.out.println("=".repeat(80));

        try {
            Thread.sleep(2000);

            // Send a hover command to first drone
            if (!drones.isEmpty()) {
                commandCenter.hover(drones.get(0).getDroneId());
            }

            Thread.sleep(2000);

            // Send altitude change to second drone
            if (drones.size() > 1) {
                commandCenter.changeAltitude(drones.get(1).getDroneId(), 120.0);
            }

            System.out.println("\n💡 Note: In a complete implementation, drones would listen to the");
            System.out.println("   command topic and react to these commands.\n");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void shutdown() {
        logger.info("Shutting down Drone Fleet Management System");

        // Shutdown drones
        drones.forEach(DroneSimulator::shutdown);

        // Shutdown coordination station
        if (station != null) {
            station.shutdown();
        }

        // Close command center
        if (commandCenter != null) {
            commandCenter.close();
        }

        // Wait for threads to finish
        try {
            if (stationThread != null) {
                stationThread.join(5000);
            }

            for (Thread thread : droneThreads) {
                thread.join(5000);
            }
        } catch (InterruptedException e) {
            logger.warn("Interrupted while waiting for threads to finish");
            Thread.currentThread().interrupt();
        }

        logger.info("Shutdown complete");
        System.out.println("\n👋 Drone Fleet Management System terminated gracefully");
    }

    private void printWelcomeBanner() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("    🚁 DRONE FLEET MANAGEMENT SYSTEM - Educational Demo");
        System.out.println("=".repeat(80));
        System.out.println("This application demonstrates:");
        System.out.println("  ✓ Apache Kafka producer/consumer pattern");
        System.out.println("  ✓ Multi-threaded drone simulators");
        System.out.println("  ✓ Real-time telemetry data streaming");
        System.out.println("  ✓ Alert/event-driven architecture");
        System.out.println("  ✓ Thread-safe data structures");
        System.out.println("  ✓ Graceful shutdown handling");
        System.out.println("=".repeat(80));
    }
}
