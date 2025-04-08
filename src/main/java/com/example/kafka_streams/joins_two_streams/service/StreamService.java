package com.example.kafka_streams.joins_two_streams.service;


import com.example.kafka_streams.joins_two_streams.entities.CoffeOrder;
import com.example.kafka_streams.joins_two_streams.entities.Promotion;
import com.example.kafka_streams.joins_two_streams.entities.RetailOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class StreamService {

    @Autowired
    private KafkaTemplate<String, CoffeOrder> coffeOrderKafkaTemplate;

    @Autowired
    private KafkaTemplate<String, RetailOrder> retailOrderKafkaTemplate;

    @Autowired
    private KafkaTemplate<String, Promotion> promotionKafkaTemplate;

    @Scheduled(fixedRate = 5000)
    public void sendOrder(){

        CoffeOrder coffeOrder = new CoffeOrder();
        coffeOrder.setUserId("ubac-12na-123");
        coffeOrder.setTotalAmount(10);
        coffeOrder.setCoffeeType("Late");
        coffeOrder.setQuantity(1);

        RetailOrder retailOrder = new RetailOrder();
        retailOrder.setUserId("ubac-12na-123");
        retailOrder.setTotalAmount(50);
        retailOrder.setItemPurchased("Shirt");
        retailOrder.setQuantity(1);


        coffeOrderKafkaTemplate.send("coffe_orders", coffeOrder.getUserId(), coffeOrder);
        retailOrderKafkaTemplate.send("retail_orders", retailOrder.getUserId(), retailOrder);
    }
}
