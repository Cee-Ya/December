//package com.yarns.december.support.mq;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.support.Acknowledgment;
//import org.springframework.kafka.support.KafkaHeaders;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.stereotype.Component;
//
//import java.util.Optional;
//
///**
// * 消费者
// * @author Yarns
// * @version 1.0
// * @date 9:26
// **/
//@Component
//@Slf4j
//@RequiredArgsConstructor
//public class KafkaConsumer {
//
//    @KafkaListener(topics = KafkaProducer.TOPIC_TEST,groupId = KafkaProducer.TOPIC_GROUP1)
//    public void topic_test(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
//        Optional message = Optional.ofNullable(record.value());
//        if (message.isPresent()) {
//            Object msg = message.get();
//            log.info("topic_test 消费了： Topic:" + topic + ",Message:" + msg);
//            ack.acknowledge();
//        }
//    }
//
//    @KafkaListener(topics = "sample",groupId = KafkaProducer.TOPIC_GROUP1)
//    public void topic_2(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
//        Optional message = Optional.ofNullable(record.value());
//        if (message.isPresent()) {
//            Object msg = message.get();
//            log.info("sample 消费了： Topic:" + topic + ",Message:" + msg);
//            ack.acknowledge();
//        }
//    }
//}
