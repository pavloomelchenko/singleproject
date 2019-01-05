package com.mycompany.springmessaging;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MyListener {

    @JmsListener(destination = "someQueue")
    public void processMessage(String content) {

        System.out.println("Succesfully received a JMS Message. Queue setup correctly.");
        //throw new RuntimeException();
    }
}
