/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cqrs.controller;

import com.cqrs.coreapi.command.ConfirmOrderCommand;
import com.cqrs.coreapi.command.PlaceOrderCommand;
import com.cqrs.coreapi.command.ShipOrderCommand;
import com.cqrs.coreapi.query.FindAllOrderedProducts;
import com.cqrs.coreapi.query.OrderedProduct;
import java.util.List;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Dell
 */
@RestController
public class OrderRestController {

    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private QueryGateway queryGateway;

    @PostMapping("/place-order/{orderId}/{product}")
    public HttpStatus placeOrder(@PathVariable("orderId") String orderId, @PathVariable("product") String produt) {
        commandGateway.send(new PlaceOrderCommand(orderId, produt));
        return HttpStatus.ACCEPTED;
    }

    @PostMapping("/confirm-order/{orderId}")
    public HttpStatus confirmOrder(@PathVariable("orderId") String orderId) {
        commandGateway.send(new ConfirmOrderCommand(orderId));
        return HttpStatus.ACCEPTED;
    }

    @PostMapping("/ship-order/{orderId}")
    public HttpStatus shipOrder(@PathVariable("orderId") String orderId) {
        commandGateway.send(new ShipOrderCommand(orderId));
        return HttpStatus.ACCEPTED;
    }

    @PostMapping("/ship-unconfirmed-order/{orderId}")
    public HttpStatus shipUnconfirmedOrder(@PathVariable("orderId") String orderId) {
        commandGateway.send(new ShipOrderCommand(orderId));
        return HttpStatus.ACCEPTED;
    }

    @GetMapping("/all-orders")
    public List<OrderedProduct> list() {
        return queryGateway.query(new FindAllOrderedProducts(), ResponseTypes.multipleInstancesOf(OrderedProduct.class)).join();
    }
}
