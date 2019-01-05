package com.mycompany.ordertest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompositeBean {
    @Autowired(required = true)
    private NoOrderBean noOrderBean;
    @Autowired(required = true)
    private Order1Bean order1Bean;
    @Autowired(required = true)
    private Order2Bean order2Bean;
    @Autowired(required = true)
    private NoOrderBeanLaterDeclaration noOrderBeanLaterDeclaration;
}
