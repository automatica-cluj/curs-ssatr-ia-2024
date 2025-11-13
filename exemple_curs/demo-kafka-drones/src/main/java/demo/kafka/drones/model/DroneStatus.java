package demo.kafka.drones.model;

/**
 * Represents the operational status of a drone.
 */
public enum DroneStatus {
    IDLE,           // Drone is on the ground, ready
    TAKEOFF,        // Drone is taking off
    IN_FLIGHT,      // Drone is flying normally
    HOVERING,       // Drone is hovering at a position
    RETURNING,      // Drone is returning to base
    LANDING,        // Drone is landing
    EMERGENCY,      // Drone is in emergency mode
    MAINTENANCE     // Drone requires maintenance
}
