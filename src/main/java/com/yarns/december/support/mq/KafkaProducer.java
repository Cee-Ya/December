//package com.yarns.december.support.mq;
//
//import com.alibaba.fastjson.JSONObject;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.support.SendResult;
//import org.springframework.stereotype.Component;
//import org.springframework.util.concurrent.ListenableFuture;
//import org.springframework.util.concurrent.ListenableFutureCallback;
//
///**
// * 消息生产者
// * @author Yarns
// * @version 1.0
// * @date 9:22
// **/
//@Component
//@Slf4j
//@RequiredArgsConstructor
//public class KafkaProducer {
//    private final KafkaTemplate<String, Object> kafkaTemplate;
//    /**
//     * 目前使用一个测试topic
//     */
//    public static final String TOPIC_TEST = "topic.testTopic";
//    public static final String TOPIC_GROUP1 = "topic.group1";
//    public static final String TOPIC_GROUP2 = "topic.group2";
//
//
//    public void send(Object obj,String topicName) {
//        String obj2String = JSONObject.toJSONString(obj);
//        log.info("准备发送消息为：{}", obj2String);
//        //发送消息
//        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topicName,"sample", obj);
//        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
//            @Override
//            public void onFailure(Throwable throwable) {
//                //发送失败的处理
//                log.info(topicName + " - 生产者 发送消息失败：" + throwable.getMessage());
//            }
//
//            @Override
//            public void onSuccess(SendResult<String, Object> stringObjectSendResult) {
//                //成功的处理
//                log.info(topicName + " - 生产者 发送消息成功：" + stringObjectSendResult.toString());
//            }
//        });
//    }
//
//}
