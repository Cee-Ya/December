package com.yarns.december.support.mq;


import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消息消费者
 * @author Yarns
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class RabbitmqConsumer {


    /**
     * 消费延迟队列
     * @param obj
     * @throws Exception
     */
    @RabbitListener(queues = {"plugin.order.queue"})
    public void orderDelayQueue(String obj, Message message, Channel channel) throws Exception {
        log.info("延迟队列开始消费...");
        try {
            log.info("消息内容:{}",new String(message.getBody()));
            // 业务处理
            //告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会在发
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            log.info("消息接收成功");
        } catch (Exception e) {
            e.printStackTrace();
            //消息重新入队
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,true);
            log.info("消息接收失败，重新入队");
        }
    }
}
