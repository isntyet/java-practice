package com.isntyet.java.practice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PracticeApplicationTests {

    @Test
    void contextLoads() {
        assertThat("sfdfs").isEqualTo("sfdfs");
    }

}
