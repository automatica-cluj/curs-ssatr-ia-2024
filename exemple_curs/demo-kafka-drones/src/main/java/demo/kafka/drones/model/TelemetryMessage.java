package demo.kafka.drones.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

/**
 * Represents telemetry data sent by a drone to the coordination station.
 * This includes position, battery level, altitude, and operational status.
 */
public class TelemetryMessage {

    @JsonProperty("droneId")
    private String droneId;

    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("latitude")
    private double latitude;

    @JsonProperty("longitude")
    private double longitude;

    @JsonProperty("altitude")
    private double altitude; // in meters

    @JsonProperty("batteryLevel")
    private int batteryLevel; // percentage 0-100

    @JsonProperty("status")
    private DroneStatus status;

    @JsonProperty("missionId")
    private String missionId;

    public TelemetryMessage() {
        // Default constructor for Jackson
    }

    public TelemetryMessage(String droneId, double latitude, double longitude,
                           double altitude, int batteryLevel, DroneStatus status, String missionId) {
        this.droneId = droneId;
        this.timestamp = LocalDateTime.now();
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.batteryLevel = batteryLevel;
        this.status = status;
        this.missionId = missionId;
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

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(int batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public DroneStatus getStatus() {
        return status;
    }

    public void setStatus(DroneStatus status) {
        this.status = status;
    }

    public String getMissionId() {
        return missionId;
    }

    public void setMissionId(String missionId) {
        this.missionId = missionId;
    }

    @Override
    public String toString() {
        return String.format("Telemetry[drone=%s, pos=(%.4f,%.4f), alt=%.1fm, battery=%d%%, status=%s, mission=%s]",
                droneId, latitude, longitude, altitude, batteryLevel, status, missionId);
    }
}
