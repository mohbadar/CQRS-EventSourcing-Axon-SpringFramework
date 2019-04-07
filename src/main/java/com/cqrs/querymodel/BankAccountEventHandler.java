/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cqrs.querymodel;

import com.cqrs.coreapi.event.AccountAddedEvent;
import com.cqrs.coreapi.query.BankAccount;
import java.util.HashMap;
import java.util.Map;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

/**
 *
 * @author Dell
 */
@Service
public class BankAccountEventHandler {

    private final Map<String, BankAccount> accounts = new HashMap<>();

    @EventHandler
    public void on(AccountAddedEvent event) {
        accounts.put(event.getId(), new BankAccount(event.getId(), event.getName(), event.getBalance()));
    }

    @EventHandler
    public void on() {

    }
    
    

}
