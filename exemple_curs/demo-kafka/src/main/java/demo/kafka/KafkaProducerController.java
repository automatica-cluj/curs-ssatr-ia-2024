package demo.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaProducerController {

    @Autowired
    private KafkaTemplate<String, ProcedureMessage> kafkaTemplate;

    @Value("${kafka.topic.name}")
    private String topicName;

    @GetMapping("/publish")
    public String publishMessage(@RequestParam(value = "key", required = false) String key,
                                 @RequestParam("procedureId") int procedureId,
                                 @RequestParam("procedureName") String procedureName,
                                 @RequestParam("status") String status,
                                 @RequestParam("timestamp") String timestamp) {
        ProcedureMessage message = new ProcedureMessage();
        message.setProcedureId(procedureId);
        message.setProcedureName(procedureName);
        message.setStatus(status);
        message.setTimestamp(timestamp);

        if (key != null && !key.isEmpty()) {
            kafkaTemplate.send(topicName, key, message);
            return "Message with key sent to Kafka successfully!";
        } else {
            kafkaTemplate.send(topicName, message);
            return "Message sent to Kafka successfully!";
        }
    }
}