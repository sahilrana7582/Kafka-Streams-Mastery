//package com.example.kafka_streams.order_transactions.stream;
//
//
//import com.example.kafka_streams.order_transactions.entity.Transaction;
//import com.example.kafka_streams.order_transactions.entity.types.PaymentType;
//import com.example.kafka_streams.order_transactions.serde.TransactionSerdeEvent;
//import org.apache.kafka.common.serialization.Serdes;
//import org.apache.kafka.streams.KafkaStreams;
//import org.apache.kafka.streams.StreamsBuilder;
//import org.apache.kafka.streams.kstream.*;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//
//@Component
//public class SplitingStream {
//
//
//    @Bean(name = "SplitingStream")
//    public KStream<String, Transaction> kStream(StreamsBuilder streamsBuilder) {
//        KStream<String, Transaction> maskedCredit = streamsBuilder.stream("masked-credit",
//                Consumed.with(Serdes.String(), new TransactionSerdeEvent()));
//
//        Predicate<String, Transaction> isCard = (key, value) -> value.getPaymentType().equals(PaymentType.CARD);
//        Predicate<String, Transaction> isCash = (key, value) -> value.getPaymentType().equals(PaymentType.CASH);
//
//        maskedCredit.split()
//                .branch(isCard, Branched.withConsumer(cardStream -> cardStream.to("card-transaction", Produced.with(Serdes.String(), new TransactionSerdeEvent()))))
//                .branch(isCash, Branched.withConsumer(cashStream -> cashStream.to("cash-transaction", Produced.with(Serdes.String(), new TransactionSerdeEvent()))));
//
//        return maskedCredit;
//    }
//}
