package demo.kafka.drones.model;

/**
 * Types of alerts that a drone can send.
 */
public enum AlertType {
    LOW_BATTERY,
    MISSION_COMPLETE,
    OBSTACLE_DETECTED,
    CONNECTION_LOST,
    SYSTEM_MALFUNCTION,
    WEATHER_WARNING
}
