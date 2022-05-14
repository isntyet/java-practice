package com.isntyet.java.practice.human.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDateTime;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HumanTest {

    @Test
    void test() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("시간 " + now);
    }
}
