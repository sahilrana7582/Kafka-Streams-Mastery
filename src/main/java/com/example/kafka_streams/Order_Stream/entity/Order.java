package com.example.kafka_streams.Order_Stream.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Order {
    private String orderId;
    private String userId;
    private double totalAmount;
    private int quantity;
    private Long timestamp;

    public Order(){};
}
