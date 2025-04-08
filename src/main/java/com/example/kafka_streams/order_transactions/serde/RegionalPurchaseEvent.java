//package com.example.kafka_streams.order_transactions.serde;
//
//import com.example.kafka_streams.order_transactions.entity.RegionalPurchase;
//import com.example.kafka_streams.order_transactions.entity.Transaction;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.kafka.common.serialization.Deserializer;
//import org.apache.kafka.common.serialization.Serde;
//import org.apache.kafka.common.serialization.Serializer;
//
//public class RegionalPurchaseEvent implements Serde<RegionalPurchase> {
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @Override
//    public Serializer<RegionalPurchase> serializer() {
//        return (topic, data) -> {
//            try{
//                return objectMapper.writeValueAsBytes(data);
//            }catch (Exception e) {
//                throw new RuntimeException("Serialization failed", e);
//            }
//        };
//    }
//
//    @Override
//    public Deserializer<RegionalPurchase> deserializer() {
//        return (topic, data) -> {
//            try {
//                return objectMapper.readValue(data, RegionalPurchase.class);
//            } catch (Exception e) {
//                throw new RuntimeException("Deserialization failed", e);
//            }
//        };
//    }
//}
