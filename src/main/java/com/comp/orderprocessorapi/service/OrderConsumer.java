package com.comp.orderprocessorapi.service;

import com.comp.orderprocessorapi.repository.OrderRepository;
import com.comp.orderprocessorapi.model.Order;
import com.comp.orderprocessorapi.util.enums.OrderStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {
    private static final Logger logger = LogManager.getLogger(OrderConsumer.class);

    @Autowired
    private OrderDetailsService orderDetailsService;

    @KafkaListener(topics = "${spring.kafka.template.default-topic}",
            groupId = "${spring.kafka.consumer.group-id}"
            ,containerFactory = "orderKafkaListenerContainerFactory")
    public void consume(Order order){
        try {
            logger.info("Saving order details with Order Request ID: " +
                    order.getOrderDetailsStatus().getOrderRequestId() + " to Database");
            orderDetailsService.updateOrderStatus(order,OrderStatus.PROCESSED);
        }
        catch (Exception e){
            logger.info("Failed to Update order details with Order Request ID: " +
                    order.getOrderDetailsStatus().getOrderRequestId() + " to Database");
        }

    }
}
