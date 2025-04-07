package com.example.kafka_streams.Order_Stream.service;


import com.example.kafka_streams.Order_Stream.entity.Order;
import com.example.kafka_streams.Order_Stream.serde.OrderEventSerde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class StreamProcessor {

    @Bean
    public KStream<String, Order> process(StreamsBuilder builder) {
        KStream<String, Order> inputStream = builder.stream("order-topic", Consumed.with(Serdes.String(), new OrderEventSerde()));
        KStream<String, Order> highOrderStream = inputStream.filter((key, value) -> value.getQuantity() > 100);
        highOrderStream.to("high-order-topic", Produced.with(Serdes.String(), new OrderEventSerde()));

        KStream<String, Order> lowOrderStream = inputStream.filter((key, value) -> value.getQuantity() < 100);
        lowOrderStream.to("low-order-topic", Produced.with(Serdes.String(), new OrderEventSerde()));
        return inputStream;
    }

}
