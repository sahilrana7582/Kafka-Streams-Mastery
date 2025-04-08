//package com.example.kafka_streams.Order_Stream.stream;
//
//
//import com.example.kafka_streams.Order_Stream.serde.OrderEventSerde;
//import org.apache.kafka.common.serialization.Serdes;
//import org.apache.kafka.streams.StreamsConfig;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafkaStreams;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//@EnableKafkaStreams
//public class Stream {
//
//    @Bean
//    public StreamsConfig streamsConfig(){
//        Map<String, Object> config = new HashMap<>();
//        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "simple-stream-app");
//        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
//        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, OrderEventSerde.class);
//        return new StreamsConfig(config);
//    }
//}
