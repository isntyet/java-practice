package com.isntyet.java.practice.home.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

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
        int numberOfExcute = 100;
        ExecutorService service = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(numberOfExcute);

        for (int i = 0; i < numberOfExcute; i++) {
            service.execute(() -> {
                try {
                    homeService.decreasePrice("한옥", 1000);
                    successCount.getAndIncrement();
                    System.out.println("성공");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                latch.countDown();
            });
        }
        latch.await();

        assertThat(successCount.get()).isEqualTo(20);
    }
}
