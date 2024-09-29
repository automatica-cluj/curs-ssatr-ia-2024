/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.exemple.curs.iot.mqtt;

import java.util.ArrayList;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author mihai
 */
public class RunAll {
    public static void main(String[] args) throws MqttException {
        SensorUI s = new SensorUI( "tcp://localhost:1883","temperature",-30,30,"C");
        s.setVisible(true);
        
        SensorUI s2 = new SensorUI( "tcp://localhost:1883","humidity",-30,30,"%");
        s2.setVisible(true);
        
        ArrayList<String> list = new ArrayList<>();
        list.add("temperature");
        GenericController gc = new GenericController("tcp://localhost:1883",list);
        
        ActuatorUI a = new ActuatorUI("tcp://localhost:1883","actuator");
        a.setVisible(true);
        
    }
}
