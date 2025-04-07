package com.example.kafka_streams.Simple_Yelling_Stream.consumer;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YellingStream {

    @Bean
    public KStream<String, String> kStream(StreamsBuilder streamsBuilder) {
        KStream<String, String> stream = streamsBuilder.stream("yelling-topic",
                Consumed.with(Serdes.String(), Serdes.String()));

        stream
                .mapValues(value -> value.toUpperCase() + "! LALALALALA") // YELLING the message
                .to("yelling-output-topic", Produced.with(Serdes.String(), Serdes.String())); // Sending to new topic

        return stream;
    }
}
