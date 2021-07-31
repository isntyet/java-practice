package com.isntyet.java.practice.locker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class LockerServiceTest {
    @Autowired
    private LockerService lockerService;

    @Test
    void multiThreadTest() throws InterruptedException {
        AtomicInteger successCount = new AtomicInteger();
        int numberOfExecute = 5;
        ExecutorService service = Executors.newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch(numberOfExecute);
        LocalDate now = LocalDate.now();

        for (int i = 0; i < numberOfExecute; i++) {
            service.execute(() -> {
                try {
                    Locker locker = lockerService.lock(now);
                    testDo();
                    lockerService.complete(locker);
                    successCount.getAndIncrement();
                    System.out.println("성공");
                } catch (Exception e) {
                    System.out.println("충돌감지 " + e.getMessage());
                }
                latch.countDown();
            });
        }
        latch.await();

        assertThat(successCount.get()).isEqualTo(1);
    }

    private void testDo() {
        try {
            Thread.sleep(1000 * 5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
