package com.comp.orderprocessorapi.service.impl;

import com.comp.orderprocessorapi.model.Response;
import com.comp.orderprocessorapi.service.OrderDetailsProducerService;
import com.comp.orderprocessorapi.model.Order;
import com.comp.orderprocessorapi.repository.OrderRepository;
import com.comp.orderprocessorapi.service.OrderDetailsService;
import com.comp.orderprocessorapi.util.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OrderDetailsProducerServiceImpl implements OrderDetailsProducerService {

    private final static Logger logger = LogManager.getLogger(OrderDetailsProducerServiceImpl.class);

    @Value("${spring.kafka.template.default-topic}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, Order> sendDetailskafkaTemplate;

    @Autowired
    private OrderDetailsService orderDetailsService;

    public Response publishOrder(Order order) {

        Response response = new Response();

        try {
            sendDetailskafkaTemplate.send(topic, order);
            logger.info("Successfully sent order details with order ID: " +order.getOrderDetailsStatus().getOrderRequestId()+" to kafka");
            response.setStatus("success");
            response.setMessage("Successfully submitted order details");
            orderDetailsService.updateOrderStatus(order,OrderStatus.PLACED);
        }
        catch (Exception e){
            logger.error("Error while sending order details with order ID: " +order.getOrderDetailsStatus().getOrderRequestId()+" to kafka");
                response.setMessage("Failed to submit order details");
                response.setStatus("failure");
        }
        return response;

    }
}
