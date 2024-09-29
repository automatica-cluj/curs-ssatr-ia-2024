package demo.kafka;

public class ProcedureMessage {
    private int procedureId;
    private String procedureName;
    private String status;
    private String timestamp;

    // Getters and setters
    public int getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(int procedureId) {
        this.procedureId = procedureId;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String asJson() {
        return "{\"procedureId\":" + procedureId + ",\"procedureName\":\"" + procedureName + "\",\"status\":\"" + status + "\",\"timestamp\":\"" + timestamp + "\"}";
    }

}