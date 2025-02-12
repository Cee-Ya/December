package com.yarns.december.support.mq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 消息生产者
 * 本地安装rabbitmq的时候记住版本一定要大于3.6.X
 * 因为3.6.X版本之后才支持延迟队列，之前是通过TTL（生存时间）+ DLX（死信交换机）来实现延迟队列的
 * @author Yarns
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class RabbitmqProducer {
    private final RabbitTemplate rabbitTemplate;

    /**
     *
     * @param msg 消息
     * @param messageId 唯一id
     * @param exchangeName 交换机
     * @param key 路由键
     * @param delayTime 延迟时间(毫秒)
     */
    @Async
    public void publish(String msg, String messageId, String exchangeName, String key, Integer delayTime) {
        /* 确认的回调 确认消息是否到达 Broker 服务器 其实就是是否到达交换器
         * 如果发送时候指定的交换器不存在 ack 就是 false 代表消息不可达
         */
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            assert correlationData != null;
            String correlationDataId = correlationData.getId();
            //返回成功，表示消息被正常投递到交换机
            if (ack) {
                log.info("信息投递到交换机成功，messageId:{}",correlationDataId);
            } else {
                log.error("交换机不可达，messageId:{} 原因:{}",correlationDataId,cause);
            }
        });
        /**
         * 延时消息是从磁盘读取消息然后发送（后台任务），发送消息的时候无法保证两点：
         *
         * 1、发送时消息路由的队列还存在
         * 2、发送时原连接仍然支持回调方法
         * 原因：消息写磁盘和从磁盘读取消息发送存在时间差，两个时间点的队列和连接情况可能不同。所以不支持Mandatory设置
         */
        /* 消息失败的回调
         * 例如消息已经到达交换器上，但路由键匹配任何绑定到该交换器的队列，会触发这个回调，此时 replyText: NO_ROUTE
         * 用不上
         */
        rabbitTemplate.setMandatory(false);
        rabbitTemplate.setReturnsCallback(returnedMessage -> {
                    String returnMessageId = returnedMessage.getMessage().getMessageProperties().getMessageId();
                    byte[] message = returnedMessage.getMessage().getBody();
                    Integer replyCode = returnedMessage.getReplyCode();
                    String replyText = returnedMessage.getReplyText();
                    String exchange = returnedMessage.getExchange();
                    String routingKey = returnedMessage.getRoutingKey();
                    log.warn("消息：{} 发送失败，消息ID：{} 应答码：{} 原因：{} 交换机：{} 路由键：{}",
                            new String(message),returnMessageId,replyCode,replyText,exchange,routingKey);
                }
        );

        // 在实际中ID 应该是全局唯一 能够唯一标识消息 消息不可达的时候触发ConfirmCallback回调方法时可以获取该值，进行对应的错误处理
        CorrelationData correlationData = new CorrelationData(messageId);
        rabbitTemplate.convertAndSend(exchangeName, key, msg, message -> {
            // 设置延迟时间
            message.getMessageProperties().setDelay(delayTime);
            return message;
        }, correlationData);
    }
}
