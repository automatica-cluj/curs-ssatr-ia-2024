package demo.kafka.drones.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

/**
 * Represents an alert message sent by a drone when critical events occur.
 */
public class AlertMessage {

    @JsonProperty("droneId")
    private String droneId;

    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("alertType")
    private AlertType alertType;

    @JsonProperty("severity")
    private AlertSeverity severity;

    @JsonProperty("message")
    private String message;

    @JsonProperty("latitude")
    private double latitude;

    @JsonProperty("longitude")
    private double longitude;

    public AlertMessage() {
        // Default constructor for Jackson
    }

    public AlertMessage(String droneId, AlertType alertType, AlertSeverity severity,
                       String message, double latitude, double longitude) {
        this.droneId = droneId;
        this.timestamp = LocalDateTime.now();
        this.alertType = alertType;
        this.severity = severity;
        this.message = message;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and Setters
    public String getDroneId() {
        return droneId;
    }

    public void setDroneId(String droneId) {
        this.droneId = droneId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public AlertType getAlertType() {
        return alertType;
    }

    public void setAlertType(AlertType alertType) {
        this.alertType = alertType;
    }

    public AlertSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(AlertSeverity severity) {
        this.severity = severity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return String.format("Alert[%s] drone=%s, type=%s, msg='%s', pos=(%.4f,%.4f)",
                severity, droneId, alertType, message, latitude, longitude);
    }
}
