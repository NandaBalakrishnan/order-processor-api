package com.comp.orderprocessorapi.repository;

import com.comp.orderprocessorapi.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {
    @Autowired
    private RedisTemplate<String, Order> redisTemplate;

    @Value("${db.key}")
    private String dbKey;

    public void save(Order order) {
        redisTemplate.opsForHash().put(dbKey, order.getOrderDetailsStatus().getOrderRequestId(),order);
    }

    public Order findById(String orderRequestId) {
        Order order = (Order) redisTemplate.opsForHash().get(dbKey, orderRequestId);
        return order;
    }

    public void update(Order order) {
        save(order);
    }

}
