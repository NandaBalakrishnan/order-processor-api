package com.comp.orderprocessorapi.service.impl;

import com.comp.orderprocessorapi.model.Response;
import com.comp.orderprocessorapi.service.OrderDetailsService;
import com.comp.orderprocessorapi.model.Order;
import com.comp.orderprocessorapi.model.OrderDetailsStatus;
import com.comp.orderprocessorapi.repository.OrderRepository;
import com.comp.orderprocessorapi.util.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OrderDetailsServiceImpl implements OrderDetailsService {
    private static final Logger logger = LogManager.getLogger(OrderDetailsServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;

    public Response saveOrder(Order order) {
        try {
            orderRepository.save(order);
            logger.info("Saving order details with Order Request ID "+
                    order.getOrderDetailsStatus().getOrderRequestId()+ " success");
            Response response = null;
            response.setMessage("Saved Order details Successfully");
            response.setStatus("success");
            return response;
        }
        catch (Exception e) {
            logger.error("Saving order details with Order Request ID "+
                    order.getOrderDetailsStatus().getOrderRequestId()+ " failed");
            Response response = null;
            response.setMessage("Failed to save order details");
            response.setStatus("failure");
            return response;
        }

    }

    public Order getOrderDetails(String orderRequestId) {
        try {
            return orderRepository.findById(orderRequestId);

        }
        catch (Exception e) {
            logger.error("Fetching order details with Order Request ID "+
                    orderRequestId+ "failed - error: "+
                    e.getMessage());
            return null;
        }
    }

    public OrderDetailsStatus getOrderStatus(String orderRequestId) {
        try {
            return orderRepository.findById(orderRequestId).getOrderDetailsStatus();
        }
        catch (Exception e) {
            logger.error("Fetching order status with Order Request ID "+
                    orderRequestId+ "failed - error: "+
                    e.getMessage());
            return null;
        }
    }

    @Override
    public void updateOrderStatus(Order order,OrderStatus status) {
        try {
            order.getOrderDetailsStatus().setOrderRequestStatus(status.toString());
            orderRepository.save(order);
        }
        catch (Exception e){
            logger.error("Failed to Update order status as"+
                    OrderStatus.PLACED.toString());
        }
    }
}
