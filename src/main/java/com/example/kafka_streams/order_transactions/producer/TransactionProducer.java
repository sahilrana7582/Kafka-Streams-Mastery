package com.example.kafka_streams.order_transactions.producer;

import com.example.kafka_streams.order_transactions.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionProducer {

    @Autowired
    private KafkaTemplate<String, Transaction> kafkaTemplate;

    @Scheduled(fixedRate = 5000)
    public void sendMessage() {
        List<Transaction> transactions = generateTransactions();

        for (Transaction transaction : transactions) {
            String key = generateKey(transaction);
            kafkaTemplate.send("transactions", key, transaction);
            System.out.printf("✅ Produced transaction for %s | Key: %s | Total: %.2f\n",
                    transaction.getName(), key, transaction.getTotalSpent());
        }
    }

    private List<Transaction> generateTransactions() {
        return List.of(
                new Transaction("John Doe", "1234-5678-9012-3456", "Shoes", "12345", "ZM001", List.of("Shoes", "Clothing"), 100.0),
                new Transaction("Emma Smith", "9876-5432-1098-7654", "Watch", "54321", "ZM002", List.of("Watch", "Accessories"), 180.0),
                new Transaction("Liam Johnson", "4321-8765-2109-6543", "Laptop", "67890", "ZM003", List.of("Laptop", "Electronics"), 950.0),
                new Transaction("Olivia Brown", "5678-1234-8765-4321", "Book", "11223", "ZM004", List.of("Book", "Stationery"), 35.0)
        );
    }

    private String generateKey(Transaction transaction) {
        String initials = transaction.getName().isEmpty() ? "anon" :
                transaction.getName().replaceAll("[^A-Z]", "").toLowerCase();
        String cardSuffix = transaction.getCreditCardNumber().replaceAll("-", "").substring(12);
        return initials + "-" + cardSuffix;
    }
}
