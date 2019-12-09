package com.qf.listener;

import com.qf.entity.Goods;
import com.qf.service.ISearchService;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * RabbitMQ的监听器 - 接收MQ中的消息
 */
@Component
public class RabbitMQListener {

    @Autowired
    private ISearchService searchService;

    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(value = "goods_exchange", type = "fanout"),
            value = @Queue(name = "search_queue")
    ))
    public void msgHandler(Goods goods){
//        System.out.println("搜索服务接收到队列的消息：" + goods);
        searchService.insertSolr(goods);
    }
}
