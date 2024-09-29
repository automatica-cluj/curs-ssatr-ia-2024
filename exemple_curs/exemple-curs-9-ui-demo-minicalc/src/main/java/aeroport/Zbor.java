/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aeroport;

/**
 *
 * @author mihai
 */
public class Zbor {
    private String id;
    private String plecare;
    private String sosire;
    private String oraPlecare;
    private String status;

    public Zbor(String id, String plecare, String sosire, String oraPlecare, String status) {
        this.id = id;
        this.plecare = plecare;
        this.sosire = sosire;
        this.oraPlecare = oraPlecare;
        this.status = status;
    }

    
    public String getId() {
        return id;
    }

    public String getPlecare() {
        return plecare;
    }

    public String getSosire() {
        return sosire;
    }

    public String getOraPlecare() {
        return oraPlecare;
    }

    public String getStatus() {
        return status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPlecare(String plecare) {
        this.plecare = plecare;
    }

    public void setSosire(String sosire) {
        this.sosire = sosire;
    }

    public void setOraPlecare(String oraPlecare) {
        this.oraPlecare = oraPlecare;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    
    
    
    

}
