/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cqrs.controller;

import com.cqrs.commandmodel.BankAccountAggregate;
import com.cqrs.coreapi.command.AddBankAccountCommand;
import com.cqrs.coreapi.command.RemoveAccountCommand;
import com.cqrs.coreapi.command.UpdateBalanceAccountCommand;
import com.cqrs.coreapi.query.BankAccount;
import java.math.BigDecimal;
import java.util.List;
import org.axonframework.commandhandling.gateway.CommandGateway;
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
public class AccountController {

    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private QueryGateway queryGateway;

    @PostMapping("/account-add/{accountId}/{balance}/{name}")
    public HttpStatus add(@PathVariable("accountId") String id,
            @PathVariable("balance") BigDecimal balance,
            @PathVariable("name") String name) {
        commandGateway.send(new AddBankAccountCommand(id, name, balance));
        return HttpStatus.ACCEPTED;
    }

    @PostMapping("/account-remove/{accountId}")
    public HttpStatus remove(@PathVariable("accountId") String id) {
        commandGateway.send(new RemoveAccountCommand(id));
        return HttpStatus.ACCEPTED;
    }

    @PostMapping("/account-balance-update/{accountId}/{balance}")
    public HttpStatus add(@PathVariable("accountId") String id,
            @PathVariable("balance") BigDecimal balance) {
        commandGateway.send(new UpdateBalanceAccountCommand(id, balance));
        return HttpStatus.ACCEPTED;
    }

    @GetMapping("/all-accounts")
    public List<BankAccount> findAll() {

    }
}
