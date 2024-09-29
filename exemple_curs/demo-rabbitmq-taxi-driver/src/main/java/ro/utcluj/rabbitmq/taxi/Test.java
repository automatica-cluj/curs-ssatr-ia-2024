/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ro.utcluj.rabbitmq.taxi;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author mihai
 */
public class Test {
    
    /**
     * This is needed to configure fanout queue which will be used to broadcast orders to all dirvers. 
     * Each driver will have a queue bind to this fanout queue (see TaxiDriverApp code). 
     * 
     * @throws IOException
     * @throws TimeoutException 
     */
    public static void DeclareExchange() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost"); // Change if your RabbitMQ server is not on localhost
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        
        //Declare fanout exchange
        channel.exchangeDeclare("booking-requests", BuiltinExchangeType.FANOUT, true);
        channel.close();
    }
    
    public static void main(String[] args) throws IOException, TimeoutException {
        
        DeclareExchange();
        
        TaxiClientApp c1 = new TaxiClientApp("Alin");
        c1.setVisible(true);
        TaxiClientApp c2 = new TaxiClientApp("Dan");
        c2.setVisible(true);
        
        TaxiDriverApp d1 = new TaxiDriverApp("Driver1");
        d1.setVisible(true);
        TaxiDriverApp d2 = new TaxiDriverApp("Driver2");
        d2.setVisible(true);
    }
}
