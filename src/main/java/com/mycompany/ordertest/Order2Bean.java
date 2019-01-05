package com.mycompany.ordertest;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(value = 2)
@Component
public class Order2Bean {

    public Order2Bean() {
        System.out.println(getClass() + " initialized");
    }
}
