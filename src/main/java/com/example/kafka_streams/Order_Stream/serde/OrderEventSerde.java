//package com.example.kafka_streams.Order_Stream.serde;
//
//import com.example.kafka_streams.Order_Stream.entity.Order;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.kafka.common.serialization.Deserializer;
//import org.apache.kafka.common.serialization.Serde;
//import org.apache.kafka.common.serialization.Serializer;
//
//
//public class OrderEventSerde implements Serde<Order> {
//    private final ObjectMapper objectMapper = new ObjectMapper();
//    @Override
//    public Serializer<Order> serializer() {
//        return (topic, data) -> {
//            try {
//                return objectMapper.writeValueAsBytes(data);
//            } catch (Exception e) {
//                throw new RuntimeException("Serialization failed", e);
//            }
//        };
//    }
//
//    @Override
//    public Deserializer<Order> deserializer() {
//        return (topic, data) -> {
//            try {
//                return objectMapper.readValue(data, Order.class);
//            } catch (Exception e) {
//                throw new RuntimeException("Deserialization failed", e);
//            }
//        };
//    }
//
//    // ...
//}