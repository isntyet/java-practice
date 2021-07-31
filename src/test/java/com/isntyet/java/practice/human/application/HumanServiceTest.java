package com.isntyet.java.practice.human.application;

import com.isntyet.java.practice.human.domain.Human;
import com.isntyet.java.practice.human.domain.HumanRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@SpringBootTest
@ActiveProfiles("test")
class HumanServiceTest {
    @Autowired
    HumanService humanService;

    @Autowired
    HumanRepository humanRepository;

    @BeforeEach
    void beforeEach() {
        Human human1 = new Human("조재영", 10000, LocalDate.of(1991, 2, 26));
        humanRepository.save(human1);
        Human human2 = new Human("조조", 2500000, LocalDate.of(1944, 1, 26));
        humanRepository.save(human2);
        Human human3 = new Human("유비", 3000, LocalDate.of(1941, 5, 05));
        humanRepository.save(human3);
        Human human4 = new Human("알렉산더", 10000, LocalDate.of(2001, 11, 20));
        humanRepository.save(human4);
    }

    @AfterEach
    void afterEach() {
        humanRepository.deleteAll();
    }

    @Test
    @DisplayName("돈 줄여보기 테스트")
    void decreaseMoneyTest() {
        assertThatCode(
                () -> humanService.decreaseMoney("조재영", 1000)
        ).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("돈 줄여보기(멀티 스레드) 테스트")
    void decreaseMoneyForMultiThreadTest() throws InterruptedException {
        AtomicInteger successCount = new AtomicInteger();
        int numberOfExecute = 100;
        ExecutorService service = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(numberOfExecute);

        for (int i = 0; i < numberOfExecute; i++) {
            service.execute(() -> {
                try {
                    humanService.decreaseMoney("조재영", 1000);
                    testDo();
                    successCount.getAndIncrement();
                    System.out.println("성공");
                } catch (ObjectOptimisticLockingFailureException oe) {
                    System.out.println("충돌감지");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                latch.countDown();
            });
        }
        latch.await();

        assertThat(successCount.get()).isEqualTo(10);
    }

    private void testDo() {
        try {
            Thread.sleep(1000 * 5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

