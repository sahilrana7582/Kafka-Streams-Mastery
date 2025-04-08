package com.example.kafka_streams.joins_two_streams.config;

import com.example.kafka_streams.joins_two_streams.entities.CoffeOrder;
import com.example.kafka_streams.joins_two_streams.entities.RetailOrder;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class coffeOrderConfig {



    @Bean(name = "CoffeOrderProducerFactory")
    public ProducerFactory<String, CoffeOrder> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }


    @Bean(name = "CoffeOrderKafkaTemplate")
    public KafkaTemplate<String, CoffeOrder> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }


}
