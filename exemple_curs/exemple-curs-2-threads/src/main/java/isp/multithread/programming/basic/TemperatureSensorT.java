/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isp.multithread.programming.basic;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mihai.hulea
 */
public class TemperatureSensorT extends Thread {
    private boolean active = true;
    private int value; 
    private String sensorId;

    public TemperatureSensorT(String id) {
        this.sensorId = id;
        this.setName("Thread-Sensor-id-"+sensorId); //optional putem seta numele unui thread
        //setDaemon(true);
    }
    
    public void run(){
        
        Random r = new Random();
        int k = 0;
        while(k<10000){
            try {
                k++;
                value = r.nextInt(100);
                System.out.printf("Sensor %s value is %d \n", sensorId, value);
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(TemperatureSensorT.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void stopSensor(){
        active = false;
    }

    public int getValue() {
        return value;
    }

    public String getSensorId() {
        return sensorId;
    }
    
    public void stopActivity(){
        active = false;
    }
}
