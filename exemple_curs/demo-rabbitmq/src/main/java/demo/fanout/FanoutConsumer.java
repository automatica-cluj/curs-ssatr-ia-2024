package demo.fanout;
import com.rabbitmq.client.*;

public class FanoutConsumer {
    private static final String EXCHANGE_NAME = "fanout_exchange";

    public static void main(String[] argv) throws Exception {
        if (argv.length < 1) {
            System.err.println("Usage: FanoutConsumer <queue_name>");
            System.exit(1);
        }

        String queueName = argv[0]; // Queue name is passed as a parameter

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // Declare the exchange
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        // Declare a persistent queue with the given name
        channel.queueDeclare(queueName, true, false, false, null);

        // Bind the queue to the exchange
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println(" [*] Waiting for messages on queue: " + queueName);

        // Set up the delivery callback
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received in " + queueName + ": '" + message + "'");
        };

        // Start consuming messages
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }
}

