/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ro.utcluj.rabbitmq.sample;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQSender {

    private final static String QUEUE_NAME = "bookingrequests";
    
    public static void sendMessage(String msg) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost"); // Change this if your RabbitMQ is not running locally
        try (Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            System.out.println(" [x] Sent '" + msg + "'");
        }
    }

    public static void main(String[] argv) throws Exception {
        for(int i=0;i<10;i++){
            Thread.sleep(1000);
            sendMessage("Hello msg #2 "+i);
        }
    }
}

