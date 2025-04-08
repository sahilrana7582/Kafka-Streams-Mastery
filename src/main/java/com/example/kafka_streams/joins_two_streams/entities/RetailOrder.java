package com.example.kafka_streams.joins_two_streams.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RetailOrder {

    private String userId;
    private double totalAmount;
    private String itemPurchased;
    private int quantity;

}



