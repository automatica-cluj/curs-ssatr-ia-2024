package demo.kafka;


import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfigCloud {

    @Value("${kafka.api.key}")
    private String apiKey;

    @Value("${kafka.api.secret}")
    private String apiSecret;

    @Value("${kafka.endpoint}")
    private String kafkaEndpoint;

    @Bean
    public ProducerFactory<String, ProcedureMessage> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaEndpoint);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put("security.protocol", "SASL_SSL");
        configProps.put(SaslConfigs.SASL_MECHANISM, "PLAIN");
        configProps.put(SaslConfigs.SASL_JAAS_CONFIG,
            "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"" + apiKey + "\" password=\"" + apiSecret + "\";");
        return new DefaultKafkaProducerFactory<>(configProps);
    }

//    @Bean
//public ProducerFactory<String, ProcedureMessage> producerFactory() {
//    Map<String, Object> configProps = new HashMap<>();
//    configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaEndpoint);
//    configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//    configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//    return new DefaultKafkaProducerFactory<>(configProps);
//}

    @Bean
    public KafkaTemplate<String, ProcedureMessage> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ConsumerFactory<String, ProcedureMessage> consumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaEndpoint);
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        configProps.put("security.protocol", "SASL_SSL");
        configProps.put(SaslConfigs.SASL_MECHANISM, "PLAIN");
        configProps.put(SaslConfigs.SASL_JAAS_CONFIG,
            "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"" + apiKey + "\" password=\"" + apiSecret + "\";");
        return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(), new ErrorHandlingDeserializer<>(new JsonDeserializer<>(ProcedureMessage.class)));
    }

//    @Bean
//public ConsumerFactory<String, ProcedureMessage> consumerFactory() {
//    Map<String, Object> configProps = new HashMap<>();
//    configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
//    configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaEndpoint);
//    configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//    configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
//    return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(), new ErrorHandlingDeserializer<>(new JsonDeserializer<>(ProcedureMessage.class)));
//}

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ProcedureMessage> kafkaListenerContainerFactory1() {
        ConcurrentKafkaListenerContainerFactory<String, ProcedureMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setRecordFilterStrategy(new RecordFilterStrategy<String, ProcedureMessage>() {
            @Override
            public boolean filter(ConsumerRecord<String, ProcedureMessage> consumerRecord) {
                // Add your filtering logic here
                // Return true to filter out the record, false to keep it
                return !consumerRecord.value().getStatus().equals("Pending");
            }
        });
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ProcedureMessage> kafkaListenerContainerFactory2() {
        ConcurrentKafkaListenerContainerFactory<String, ProcedureMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setRecordFilterStrategy(new RecordFilterStrategy<String, ProcedureMessage>() {
            @Override
            public boolean filter(ConsumerRecord<String, ProcedureMessage> consumerRecord) {
                // Add your filtering logic here
                // Return true to filter out the record, false to keep it
                return !consumerRecord.value().getStatus().equals("Confirmed");
            }
        });
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ProcedureMessage> kafkaListenerContainerFactoryNoFilter() {
        ConcurrentKafkaListenerContainerFactory<String, ProcedureMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        // No RecordFilterStrategy set, so it will consume all messages
        return factory;
    }
}