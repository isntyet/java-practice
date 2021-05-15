package com.isntyet.java.practice.human.event;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HumanEvent {
    public HumanEvent(Object source) {
        log.info("event!!");
    }
}
