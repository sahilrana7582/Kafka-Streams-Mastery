package com.example.kafka_streams.joins_two_streams.entities;

import org.apache.kafka.streams.kstream.ValueJoiner;

public class PromotionJoiner implements ValueJoiner<CoffeOrder, RetailOrder, Promotion> {
    @Override
    public Promotion apply(CoffeOrder coffeOrder, RetailOrder retailOrder) {
        Promotion promotion = new Promotion();
        promotion.setUserId(coffeOrder.getUserId());
        promotion.setPromotionType("50% Discount");
        promotion.setDiscountAmount(1000);
        return promotion;
    }
}
