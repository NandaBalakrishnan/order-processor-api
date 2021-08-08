package com.comp.orderprocessorapi.controller;

import com.comp.orderprocessorapi.model.Response;
import com.comp.orderprocessorapi.service.OrderDetailsProducerService;
import com.comp.orderprocessorapi.service.OrderDetailsService;
import com.comp.orderprocessorapi.model.Order;
import com.comp.orderprocessorapi.model.OrderDetailsStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("orders")
public class OrderController {
    private final static Logger logger = LogManager.getLogger(OrderController.class);

    @Autowired
    private OrderDetailsProducerService orderDetailsProducerService;

    @Autowired
    private OrderDetailsService orderDetailsService;

    @PostMapping(value="publish/order",consumes = {"application/json"},produces = {"application/json"})
    public ResponseEntity<Response> submitOrder(@RequestBody Order order) {
        logger.info("Submitting order details with Order Request ID: " + order.getOrderDetailsStatus().getOrderRequestId());
        Response response = orderDetailsProducerService.publishOrder(order);
        if (response == null) {
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
    }

    @GetMapping(path="order/status/{orderRequestId}",produces = {"application/json"})
    public ResponseEntity<OrderDetailsStatus> getOrderStatus(@PathVariable int orderRequestId) {
        logger.info("getting status for the order with Order Request ID: "+ orderRequestId);
        OrderDetailsStatus orderDetailsStatus = orderDetailsService.getOrderStatus(Integer.toString(orderRequestId));
        if (orderDetailsStatus == null) {
            return new ResponseEntity<>(orderDetailsStatus, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(orderDetailsStatus, HttpStatus.OK);
        }
    }

}
