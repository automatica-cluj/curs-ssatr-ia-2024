package demo.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

//    @KafkaListener(topics = "${kafka.topic.name}", groupId = "test-group1", containerFactory = "kafkaListenerContainerFactory1")
//    public void listen1(ProcedureMessage message) {
//        System.out.println("[1] Message processed by listener 1 in 'test-group1': " + message.asJson());
//    }
//
//    @KafkaListener(topics = "${kafka.topic.name}", groupId = "test-group2", containerFactory = "kafkaListenerContainerFactory2")
//    public void listen2(ProcedureMessage message) {
//        System.out.println("[2] Message processed by listener 2 in 'test-group2: " + message.asJson());
//    }

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "perks.demo", containerFactory = "kafkaListenerContainerFactoryNoFilter")
    public void listen3(ProcedureMessage message) {
        System.out.println("[3] Message processed by listener 3 in 'test-group3': " + message.asJson());
    }
}