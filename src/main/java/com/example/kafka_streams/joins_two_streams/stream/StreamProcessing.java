package com.example.kafka_streams.joins_two_streams.stream;

import com.example.kafka_streams.joins_two_streams.entities.CoffeOrder;
import com.example.kafka_streams.joins_two_streams.entities.Promotion;
import com.example.kafka_streams.joins_two_streams.entities.PromotionJoiner;
import com.example.kafka_streams.joins_two_streams.entities.RetailOrder;
import com.example.kafka_streams.joins_two_streams.serdes.CoffeSerde;
import com.example.kafka_streams.joins_two_streams.serdes.PromotionSerde;
import com.example.kafka_streams.joins_two_streams.serdes.RetailSerde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Duration;


@Component
public class StreamProcessing {


    @Bean
    public KStream<String, Promotion> kStream(StreamsBuilder streamsBuilder) {
        KStream<String, CoffeOrder> coffeeStream = streamsBuilder.stream("coffe_orders", Consumed.with(Serdes.String(), new CoffeSerde()));
        KStream<String, RetailOrder> retailStream = streamsBuilder.stream("retail_orders", Consumed.with(Serdes.String(), new RetailSerde()));
        ValueJoiner<CoffeOrder, RetailOrder, Promotion> promotionJoiner = new PromotionJoiner();

        KStream<String, Promotion> promotionStream = coffeeStream.join(
                retailStream,
                promotionJoiner,
                JoinWindows.ofTimeDifferenceWithNoGrace(Duration.ofMinutes(30)),
                StreamJoined.with(Serdes.String(), new CoffeSerde(), new RetailSerde())
        );

        // Send to another topic
        promotionStream.to("promotion-topic", Produced.with(Serdes.String(), new PromotionSerde()));

        return promotionStream; // now it matches your method signature
    }


}
