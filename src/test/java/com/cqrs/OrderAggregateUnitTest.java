/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cqrs;

import com.cqrs.commandmodel.OrderAggregate;
import com.cqrs.coreapi.command.ConfirmOrderCommand;
import com.cqrs.coreapi.command.PlaceOrderCommand;
import com.cqrs.coreapi.command.ShipOrderCommand;
import com.cqrs.coreapi.event.OrderConfirmedEvent;
import com.cqrs.coreapi.event.OrderPlacedEvent;
import com.cqrs.coreapi.event.OrderShippedEvent;
import java.util.UUID;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Dell
 */
public class OrderAggregateUnitTest {

    private FixtureConfiguration<OrderAggregate> fixture;

    @Before
    public void init() {
        fixture = new AggregateTestFixture<>(OrderAggregate.class);
    }

    @Test
    public void giveNoPriorActivity_whenOrderPlaceCommand_thenShouldPublishOrderPlacedEvent() {
        String orderId = UUID.randomUUID().toString();
        String product = "Nice Chair";

        fixture.givenNoPriorActivity().when(new PlaceOrderCommand(orderId, product))
                .expectEvents(new OrderPlacedEvent(orderId, product));
    }

    @Test
    public void givenOrderPlaceEvent_when_ConfirmOrderCommad_andShouldPublishOrderConfirmedEvent() {
        String orderId = UUID.randomUUID().toString();
        String product = "Nice Chair";

        fixture.given(new OrderPlacedEvent(orderId, product))
                .when(new ConfirmOrderCommand(orderId))
                .expectEvents(new OrderConfirmedEvent(orderId));
    }

    @Test
    public void givenOrderPlacedEvent_whenShipOrderCommand_thenShouldThrowIllegalStateException() {
        String orderId = UUID.randomUUID().toString();
        String product = "Deluxe Chair";
        fixture.given(new OrderPlacedEvent(orderId, product))
                .when(new ShipOrderCommand(orderId))
                .expectException(IllegalStateException.class);
    }
    
    @Test
    public void givenOrderPlaceEventAndOrderConfirmedEvent_whenShipOrderCommad_thenPublishOrderShippedEvent()
    {
        String orderId = UUID.randomUUID().toString();
        String product ="Nice Chair";
        
        fixture.given(new OrderPlacedEvent(orderId, product), new OrderConfirmedEvent(orderId))
                .when(new ShipOrderCommand(orderId))
                .expectEvents(new OrderShippedEvent(orderId));
    }
}
