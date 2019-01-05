package com.mycompany.ordertest;

import org.springframework.stereotype.Component;

@Component
public class NoOrderBeanLaterDeclaration {
    public NoOrderBeanLaterDeclaration() {
        System.out.println(getClass() + " initialized");
    }
}
