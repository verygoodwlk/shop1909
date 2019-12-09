package com.qf.shop_goods;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public FanoutExchange getExchange(){
        return new FanoutExchange("goods_exchange");
    }

}
