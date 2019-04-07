/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cqrs.querymodel;

import com.cqrs.coreapi.event.OrderConfirmedEvent;
import com.cqrs.coreapi.event.OrderPlacedEvent;
import com.cqrs.coreapi.event.OrderShippedEvent;
import com.cqrs.coreapi.query.FindAllOrderedProducts;
import com.cqrs.coreapi.query.OrderStatus;
import com.cqrs.coreapi.query.OrderedProduct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

/**
 *
 * @author Dell
 */
@Service
public class OrderedProductsEventHandler {

    private final Map<String, OrderedProduct> orderProducts = new HashMap<>();

    @EventHandler
    public void on(OrderPlacedEvent event) {
        String orderId = event.getOrderId();
        orderProducts.put(orderId, new OrderedProduct(event.getOrderId(), event.getProduct(), OrderStatus.PLACED));
    }

    @EventHandler
    public void on(OrderConfirmedEvent event) {
        orderProducts.computeIfPresent(event.getOrderId(), (orderId, orderedProduct) -> {
            orderedProduct.setOrderStatus(OrderStatus.CONFIRMED);
            return orderedProduct;
        });
    }

    @EventHandler
    public void on(OrderShippedEvent event) {
        orderProducts.compute(event.getOrderId(), (orderId, confirmedOrder) -> {
            confirmedOrder.setOrderStatus(OrderStatus.SHIPPED);
            return confirmedOrder;
        });
    }

    @QueryHandler
    public List<OrderedProduct> handle(FindAllOrderedProducts query) {
        return new ArrayList<>(orderProducts.values());
    }
}
