//package com.example.kafka_streams.order_transactions.stream;
//
//import com.example.kafka_streams.order_transactions.entity.Transaction;
//import com.example.kafka_streams.order_transactions.serde.TransactionSerdeEvent;
//import org.apache.kafka.common.serialization.Serdes;
//import org.apache.kafka.streams.StreamsBuilder;
//import org.apache.kafka.streams.kstream.Consumed;
//import org.apache.kafka.streams.kstream.KStream;
//import org.apache.kafka.streams.kstream.Produced;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//
//@Component
//public class MaskedCreditStream {
//
//    @Bean(name = "MaskedCreditStream")
//    public KStream<String, Transaction> kStream(StreamsBuilder streamsBuilder) {
//        KStream<String, Transaction> stream = streamsBuilder.stream("transactions",
//                Consumed.with(Serdes.String(), new TransactionSerdeEvent()));
//
//        KStream<String, Transaction> maskedCreditCard = stream.mapValues((key, transaction)  -> {
//            String ccNum = transaction.getCreditCardNumber();
//            StringBuilder masked = new StringBuilder(ccNum);
//            for (int i = 0; i < ccNum.length() - 4; i++) {
//                if (ccNum.charAt(i) != '-') {
//                    masked.setCharAt(i, '#');
//                }
//            }
//            transaction.setCreditCardNumber(masked.toString());
//            return transaction;
//        });
//
//        maskedCreditCard.to("masked-credit",
//                Produced.with(Serdes.String(), new TransactionSerdeEvent()));
//
//
//
//        return maskedCreditCard;
//    }
//}
