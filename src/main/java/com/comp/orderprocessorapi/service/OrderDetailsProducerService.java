package com.comp.orderprocessorapi.service;

import com.comp.orderprocessorapi.model.Response;
import com.comp.orderprocessorapi.model.Order;

public interface OrderDetailsProducerService {
    Response publishOrder(Order order);
}
