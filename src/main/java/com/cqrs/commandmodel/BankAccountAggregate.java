/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cqrs.commandmodel;

import com.cqrs.coreapi.command.AddBankAccountCommand;
import com.cqrs.coreapi.command.RemoveAccountCommand;
import com.cqrs.coreapi.command.UpdateBalanceAccountCommand;
import com.cqrs.coreapi.event.AccountAddedEvent;
import com.cqrs.coreapi.event.AccountBalanceUpdatedEvent;
import com.cqrs.coreapi.event.AccountRemovedEvent;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

/**
 *
 * @author Dell
 */
@Aggregate
@Slf4j
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountAggregate {

    @AggregateIdentifier
    private String accountId;
    private String name;
    private BigDecimal balance;

    @CommandHandler
    public void handle(AddBankAccountCommand command) {
        log.info("Handling {} command: {}", command.getClass().getSimpleName(), command);

        AggregateLifecycle.apply(new AccountAddedEvent(command.getId(), command.getName(), command.getBalance()));
    }

    @CommandHandler
    public void handle(RemoveAccountCommand command) {
        log.info("Handling {} command: {}", command.getClass().getSimpleName(), command);
        AggregateLifecycle.apply(new AccountRemovedEvent(command.getId()));
    }

    @CommandHandler
    public void handle(UpdateBalanceAccountCommand command) {
        log.info("Handling {} command: {}", command.getClass().getSimpleName(), command);
        AggregateLifecycle.apply(new AccountBalanceUpdatedEvent(command.getId(), command.getBalance()));
    }

    @EventSourcingHandler
    public void on(AccountRemovedEvent event) {
        log.info("Handling {} event: {}", event.getClass().getSimpleName(), event);

    }

    @EventSourcingHandler
    public void on(AccountBalanceUpdatedEvent event) {
        log.info("Handling {} event: {}", event.getClass().getSimpleName(), event);
        this.accountId = event.getId();
        this.balance = event.getBalance();
    }

    @EventSourcingHandler
    public void on(AccountAddedEvent event) {
        log.info("Handling {} event: {}", event.getClass().getSimpleName(), event);
        this.accountId = event.getId();
        this.name = event.getName();
        this.balance = event.getBalance();
    }

}
