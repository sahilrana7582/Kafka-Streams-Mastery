//package com.example.kafka_streams.Order_Stream.controller;
//
//
//import com.example.kafka_streams.Order_Stream.entity.Order;
//import com.example.kafka_streams.Order_Stream.service.ProducerService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/kafka")
//public class KafkaController {
//
//    @Autowired
//    private  ProducerService producerService;
//
//    @PostMapping("/send")
//    public String sendMessage(@RequestBody Order[] order) {
//        for(Order o : order) {
//            producerService.sendMessage(o);
//        }
//        return "Message sent to Kafka!";
//    }
//
//
//}
