package com.example.kafka_streams.Simple_Yelling_Stream.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class Producer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(fixedRate = 5000)
    public void sendMessage() {
        String message = "hello from scheduled producer!";

        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("yelling-topic", message);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Message sent successfully: " + result.getRecordMetadata().offset());
            } else {
                System.err.println("Message failed to send: " + ex.getMessage());
            }
        });
    }

}
