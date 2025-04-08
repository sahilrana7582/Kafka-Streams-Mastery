package com.example.kafka_streams.joins_two_streams.serdes;

import com.example.kafka_streams.joins_two_streams.entities.CoffeOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class CoffeSerde implements Serde<CoffeOrder> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public Serializer<CoffeOrder> serializer() {
        return (topic, data) -> {
            try {
                return objectMapper.writeValueAsBytes(data);
            } catch (Exception e) {
                throw new RuntimeException("Serialization failed", e);
            }
        };
    }

    @Override
    public Deserializer<CoffeOrder> deserializer() {
        return (topic, data) -> {
            try {
                return objectMapper.readValue(data, CoffeOrder.class);
            } catch (Exception e) {
                throw new RuntimeException("Deserialization failed", e);
            }
        };
    }
}
