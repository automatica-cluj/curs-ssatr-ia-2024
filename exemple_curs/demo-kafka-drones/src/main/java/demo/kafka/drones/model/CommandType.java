package demo.kafka.drones.model;

/**
 * Types of commands that can be sent to drones.
 */
public enum CommandType {
    START_MISSION,
    ABORT_MISSION,
    RETURN_TO_BASE,
    HOVER,
    LAND,
    CHANGE_ALTITUDE,
    GO_TO_WAYPOINT
}
