/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cqrs.coreapi.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Dell
 */
@Data
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class OrderConfirmedEvent {
    
    private final String orderId;
}
