//package com.yarns.december.controller;
//
//import com.yarns.december.entity.base.ResponseBo;
//import com.yarns.december.support.mq.KafkaProducer;
//import lombok.RequiredArgsConstructor;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @author Yarns
// * @version 1.0
// * @date 10:06
// **/
//@RestController
//@RequestMapping("test")
//@RequiredArgsConstructor
//public class TestController {
//
//    private final KafkaProducer kafkaProducer;
//
//    @GetMapping
//    public ResponseBo sendMessage(String topic){
//        if(StringUtils.isNotBlank(topic)){
//            kafkaProducer.send("测试消息发送",topic);
//            return ResponseBo.ok();
//        }
//        kafkaProducer.send("测试消息发送",KafkaProducer.TOPIC_TEST);
//        return ResponseBo.ok();
//    }
//
//}
