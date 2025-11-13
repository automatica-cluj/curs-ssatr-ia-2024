package demo.kafka.drones.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import demo.kafka.drones.model.CommandMessage;
import demo.kafka.drones.model.CommandType;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Command Center for sending commands to drones via Kafka.
 * Demonstrates Kafka producer for sending targeted messages.
 */
public class CommandCenter {

    private static final Logger logger = LoggerFactory.getLogger(CommandCenter.class);

    private final KafkaProducer<String, String> producer;
    private final ObjectMapper objectMapper;
    private final String commandTopic;

    public CommandCenter(KafkaProducer<String, String> producer, String commandTopic) {
        this.producer = producer;
        this.commandTopic = commandTopic;

        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    /**
     * Send a command to a specific drone
     */
    public void sendCommand(String targetDroneId, CommandType commandType, String parameters) {
        try {
            CommandMessage command = new CommandMessage(targetDroneId, commandType, parameters);
            String json = objectMapper.writeValueAsString(command);

            ProducerRecord<String, String> record =
                    new ProducerRecord<>(commandTopic, targetDroneId, json);

            producer.send(record, (metadata, exception) -> {
                if (exception != null) {
                    logger.error("Failed to send command to {}", targetDroneId, exception);
                } else {
                    logger.info("Command sent to {}: {} - {}", targetDroneId, commandType, parameters);
                }
            });

        } catch (Exception e) {
            logger.error("Error creating command message", e);
        }
    }

    /**
     * Convenience methods for common commands
     */
    public void returnToBase(String droneId) {
        sendCommand(droneId, CommandType.RETURN_TO_BASE, "{}");
        System.out.printf("✈️  Sent RETURN_TO_BASE command to %s%n", droneId);
    }

    public void emergencyLand(String droneId) {
        sendCommand(droneId, CommandType.LAND, "{\"emergency\": true}");
        System.out.printf("🚨 Sent EMERGENCY LAND command to %s%n", droneId);
    }

    public void startMission(String droneId, String missionId) {
        sendCommand(droneId, CommandType.START_MISSION, "{\"missionId\": \"" + missionId + "\"}");
        System.out.printf("🚀 Sent START_MISSION command to %s (mission: %s)%n", droneId, missionId);
    }

    public void hover(String droneId) {
        sendCommand(droneId, CommandType.HOVER, "{}");
        System.out.printf("⏸️  Sent HOVER command to %s%n", droneId);
    }

    public void changeAltitude(String droneId, double newAltitude) {
        sendCommand(droneId, CommandType.CHANGE_ALTITUDE,
                "{\"altitude\": " + newAltitude + "}");
        System.out.printf("📐 Sent CHANGE_ALTITUDE command to %s (new altitude: %.1fm)%n",
                droneId, newAltitude);
    }

    public void close() {
        producer.close();
    }
}
