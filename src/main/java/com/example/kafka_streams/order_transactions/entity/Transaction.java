package com.example.kafka_streams.order_transactions.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    private String name;
    private String creditCardNumber;
    private String itemPurchased;
    private String zipCode;
    private String zMartNumber;
    private List<String> itemsPurchased;
    private double totalSpent;

}
