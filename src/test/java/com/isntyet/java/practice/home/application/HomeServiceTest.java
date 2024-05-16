package com.isntyet.java.practice.home.application;

import com.isntyet.java.practice.home.domain.Home;
import com.isntyet.java.practice.home.domain.HomeRepository;
import com.isntyet.java.practice.human.domain.HumanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HomeServiceTest {
    @Autowired
    HomeService homeService;

    @Autowired
    HomeRepository homeRepository;

    @BeforeEach
    void beforeEach() {
        Home home1 = Home.builder()
                .name("한옥")
                .address("address")
                .price(1000)
                .build();

        homeRepository.save(home1);

        Home home2 = Home.builder()
                .name("양옥")
                .address("address")
                .price(20000)
                .build();

        homeRepository.save(home2);
    }

    @Test
    void test() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("시간 " + now);
    }

    @Test
    @DisplayName("가격 줄여보기 테스트")
    void decreasePriceTest() {
        assertThatCode(
                () -> homeService.decreasePrice("한옥", 1000)
        ).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("가격 줄여보기(멀티 스레드) 테스트")
    void decreasePriceForMultiThreadTest() throws InterruptedException {
        AtomicInteger successCount = new AtomicInteger();
        int numberOfExecute = 100;
        ExecutorService service = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(numberOfExecute);

        for (int i = 0; i < numberOfExecute; i++) {
            service.execute(() -> {
                try {
                    homeService.decreasePrice("양옥", 1000);
                    successCount.getAndIncrement();
                    System.out.println("성공");
                } catch (Exception e) {
                    System.out.println(e);
                }
                latch.countDown();
            });
        }
        latch.await();

        assertThat(successCount.get()).isEqualTo(20);
    }
}
