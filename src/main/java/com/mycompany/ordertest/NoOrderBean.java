package com.mycompany.ordertest;

import org.springframework.stereotype.Component;

@Component
public class NoOrderBean {
    public NoOrderBean() {
        System.out.println(getClass() + " initialized");
    }
}
