package com.example.kafka_streams.order_transactions.stream;


import com.example.kafka_streams.order_transactions.entity.RegionalPurchase;
import com.example.kafka_streams.order_transactions.entity.Transaction;
import com.example.kafka_streams.order_transactions.serde.RegionalPurchaseEvent;
import com.example.kafka_streams.order_transactions.serde.TransactionSerdeEvent;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class RegionalPatternPurchase {

    @Bean(name = "RegionalPatternPurchase")
    public KStream<String, Transaction> process(StreamsBuilder streamsBuilder) {

        KStream<String, Transaction> maskedCreditStream = streamsBuilder.stream("masked-credit",
                Consumed.with(Serdes.String(), new TransactionSerdeEvent()));

        KStream<String, RegionalPurchase> regionalPurchase = maskedCreditStream.map((key, transaction) -> {
            String zipCode = transaction.getZipCode();
            String name = transaction.getName();
            return KeyValue.pair(zipCode, new RegionalPurchase( name, transaction.getItemsPurchased(),
                    transaction.getTotalSpent()));
        });

        regionalPurchase.to("regional-patterns",
                Produced.with(Serdes.String(), new RegionalPurchaseEvent()));


        return maskedCreditStream;
    }
}
