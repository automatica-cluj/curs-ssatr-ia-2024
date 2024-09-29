/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.exemple.curs.iot.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 *
 * @author mihai
 */
public class DummyDataLogger implements MqttCallback {

    private MqttClient client;

    public DummyDataLogger(String broker, String topic) throws MqttException{
        this.client = new MqttClient(broker, MqttClient.generateClientId(), new MemoryPersistence());
        this.client.connect();
        this.client.setCallback(this);
        this.client.subscribe(topic);
    }

    @Override
    public void connectionLost(Throwable thrwbl) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {
        System.out.println("Console logger:"+new String(mm.getPayload()));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public static void main(String[] args) throws MqttException {
        DummyDataLogger ddl = new DummyDataLogger("tcp://localhost:1883","temperature");
    }
    
}
