package com.isntyet.java.practice.human.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class HumanListener {

    @EventListener
    public void onHumanEvent(HumanEvent event) {
        System.out.println("listener: " + event.toString());
    }
}
