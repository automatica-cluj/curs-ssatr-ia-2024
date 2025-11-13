package demo.kafka.drones.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

/**
 * Represents a command sent from the coordination station to a specific drone.
 */
public class CommandMessage {

    @JsonProperty("targetDroneId")
    private String targetDroneId;

    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("commandType")
    private CommandType commandType;

    @JsonProperty("parameters")
    private String parameters; // Additional command parameters in JSON format

    public CommandMessage() {
        // Default constructor for Jackson
    }

    public CommandMessage(String targetDroneId, CommandType commandType, String parameters) {
        this.targetDroneId = targetDroneId;
        this.timestamp = LocalDateTime.now();
        this.commandType = commandType;
        this.parameters = parameters;
    }

    // Getters and Setters
    public String getTargetDroneId() {
        return targetDroneId;
    }

    public void setTargetDroneId(String targetDroneId) {
        this.targetDroneId = targetDroneId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return String.format("Command[to=%s, type=%s, params=%s]",
                targetDroneId, commandType, parameters);
    }
}
