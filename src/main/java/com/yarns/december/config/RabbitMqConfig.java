package com.yarns.december.config;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


/**
 * 消息队列配置
 * @author Yarns
 */
@Configuration
public class RabbitMqConfig {

    //-------------------------------交换机---------------------------------

    /**
     * 延迟订单交换机名称
     */
    public static final String DELAY_EXCHANGE_NAME = "plugin.delay.exchange";



    //-------------------------------队列---------------------------------

    /**
     * 延迟订单消息队列名称
     */
    public static final String DELAY_QUEUE_ORDER_NAME = "plugin.order.queue";


    //-------------------------------路由---------------------------------

    /**
     * 延迟订单路由名称
     */
    public static final String ROUTING_KEY_ORDER = "plugin.delay.routing_order";


    /**
     * 声明一个订单延迟队列
     * @return
     */
    @Bean("ORDER_DELAY_QUEUE")
    public Queue orderDelayQueue(){
        return QueueBuilder.durable(DELAY_QUEUE_ORDER_NAME).build();
    }

    /**
     * 声明一个订单延迟交换机
     * @return
     */
    @Bean("DELAY_EXCHANGE")
    public CustomExchange delayExchange(){
        Map<String, Object> args = new HashMap<>(1);
        args.put("x-delayed-type", "direct");
        return new CustomExchange(DELAY_EXCHANGE_NAME, "x-delayed-message", true,false, args);
    }

    /**
     * 订单延迟队列绑定
     * @param orderDelayQueue
     * @param delayExchange
     * @return
     */
    @Bean
    public Binding orderDelayQueueBinding(@Qualifier("ORDER_DELAY_QUEUE") Queue orderDelayQueue, @Qualifier("DELAY_EXCHANGE") CustomExchange delayExchange){
        return BindingBuilder.bind(orderDelayQueue).to(delayExchange).with(ROUTING_KEY_ORDER).noargs();
    }

}
