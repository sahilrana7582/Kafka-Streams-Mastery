package com.example.kafka_streams.order_transactions;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private double totalSpent;

}
