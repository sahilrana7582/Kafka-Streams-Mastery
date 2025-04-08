package com.example.kafka_streams.joins_two_streams.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Promotion {

    private String userId;
    private String promotionType;
    private double discountAmount;

}
