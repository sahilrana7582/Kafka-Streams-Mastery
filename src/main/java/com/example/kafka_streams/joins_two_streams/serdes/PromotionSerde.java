package com.example.kafka_streams.joins_two_streams.serdes;

import com.example.kafka_streams.joins_two_streams.entities.Promotion;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class PromotionSerde implements Serde<Promotion> {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Serializer<Promotion> serializer() {
        return (topic, data) -> {
            try{
                return mapper.writeValueAsBytes(data);
            }catch (Exception e) {
                throw new RuntimeException("Serialization failed", e);
            }
        };
    }

    @Override
    public Deserializer<Promotion> deserializer() {
        return (topic, data) -> {
            try {
                return mapper.readValue(data, Promotion.class);
            } catch (Exception e) {
                throw new RuntimeException("Deserialization failed", e);
            }
        };
    }
}
