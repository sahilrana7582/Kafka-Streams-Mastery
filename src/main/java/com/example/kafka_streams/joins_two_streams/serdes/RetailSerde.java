package com.example.kafka_streams.joins_two_streams.serdes;

import com.example.kafka_streams.joins_two_streams.entities.RetailOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class RetailSerde implements Serde<RetailOrder> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public Serializer<RetailOrder> serializer() {
        return (topic, data) -> {
            try{
                return objectMapper.writeValueAsBytes(data);
            }catch (Exception e) {
                throw new RuntimeException("Serialization failed", e);
            }
        };
    }

    @Override
    public Deserializer<RetailOrder> deserializer() {
        return (topic, data) -> {
            try {
                return objectMapper.readValue(data, RetailOrder.class);
            } catch (Exception e) {
                throw new RuntimeException("Deserialization failed", e);
            }
        };
    }
}
