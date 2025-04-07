package com.example.kafka_streams.order_transactions.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegionalPurchase {

    private String name;
    private List<String> itemsPurchased;
    private double totalSpent;

}
