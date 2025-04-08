//package com.example.kafka_streams.Order_Stream.service;
//
//
//import com.example.kafka_streams.Order_Stream.entity.Order;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//public class ProducerService {
//
//    @Autowired
//    public KafkaTemplate<String, Order> kafkaTemplate;
//
//    private final String topic = "order-topic";
//
//    public void sendMessage(Order order) {
//        kafkaTemplate.send("order-topic", order.getOrderId(), order);
//    }
//}
