package com.comp.orderprocessorapi.service;

import com.comp.orderprocessorapi.model.Response;
import com.comp.orderprocessorapi.model.Order;
import com.comp.orderprocessorapi.model.OrderDetailsStatus;
import com.comp.orderprocessorapi.util.enums.OrderStatus;

public interface OrderDetailsService {

    Response saveOrder(Order order);
    Order getOrderDetails(String orderRequestId);
    OrderDetailsStatus getOrderStatus(String orderRequestId);
    void updateOrderStatus(Order order, OrderStatus status);

}
