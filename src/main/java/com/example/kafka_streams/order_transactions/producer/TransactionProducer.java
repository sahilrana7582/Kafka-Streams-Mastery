package com.example.kafka_streams.order_transactions.producer;


import com.example.kafka_streams.order_transactions.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TransactionProducer {


    @Autowired
    private KafkaTemplate<String, Transaction> kafkaTemplate;

    @Scheduled(fixedRate = 5000)
    public void sendMessage() {
        Transaction transaction = new Transaction("John Doe", "1234-5678-9012-3456", "Shoes", "12345", "123456", 50.0);
        kafkaTemplate.send("transactions", transaction);
    }
}
