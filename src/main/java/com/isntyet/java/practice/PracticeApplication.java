package com.isntyet.java.practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.TimeZone;

@SpringBootApplication
public class PracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PracticeApplication.class, args);

        LocalDateTime now = LocalDateTime.now();
        System.out.println("현재시간 " + now);
    }

    @PostConstruct
    public void started() {
        // timezone UTC 셋팅
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
}
