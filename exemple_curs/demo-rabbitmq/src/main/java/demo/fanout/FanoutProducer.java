package demo.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class FanoutProducer {

    public static void main(String[] args) throws Exception {

        // Get the exchange name from the arguments
        String exchangeName = "fanout_exchange";

        // Set up connection and channel
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {

            // Declare the fanout exchange with the provided exchange name
            channel.exchangeDeclare(exchangeName, "fanout");

            // Read the JSON file and update the timestamp
            String jsonFilePath = "data.json";
            byte[] jsonData = Files.readAllBytes(Paths.get(jsonFilePath));

            // Parse JSON and update the timestamp
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> jsonMap = objectMapper.readValue(jsonData, HashMap.class);
            jsonMap.put("timestamp", Instant.now().toString());

            // Convert the map back to JSON string
            String updatedJsonContent = objectMapper.writeValueAsString(jsonMap);

            // Publish the updated JSON content to the fanout exchange
            channel.basicPublish(exchangeName, "", MessageProperties.PERSISTENT_TEXT_PLAIN, updatedJsonContent.getBytes("UTF-8"));

            System.out.println(" [x] Sent updated JSON to exchange: " + updatedJsonContent);
        }
    }
}
