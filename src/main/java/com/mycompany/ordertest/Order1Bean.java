package com.mycompany.ordertest;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(value = 1)
@Component
public class Order1Bean {

    public Order1Bean() {
        System.out.println(getClass() + " initialized");
    }
}
