package com.isntyet.java.practice.human.application;

import com.isntyet.java.practice.home.domain.Home;
import com.isntyet.java.practice.home.domain.HomeRepository;
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
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class HumanServiceTest {

    @Autowired
    HomeRepository homeRepository;

    @Autowired
    HumanService humanService;

    @Autowired
    HumanRepository humanRepository;

    @BeforeEach
    void beforeEach() {
        Home home = Home.builder()
                .name("home")
                .address("address")
                .price(1000)
                .build();

        homeRepository.save(home);

        Human human1 = Human.builder()
                .home(home)
                .name("조재영")
                .money(10000)
                .birth(LocalDate.of(1991, 2, 26))
                .build();

        humanRepository.save(human1);

        Human human2 = Human.builder()
                .home(home)
                .name("조조")
                .money(2500000)
                .birth(LocalDate.of(1944, 1, 26))
                .build();
        humanRepository.save(human2);
        Human human3 = Human.builder()
                .home(home)
                .name("유비")
                .money(3000)
                .birth(LocalDate.of(1941, 5, 05))
                .build();
        humanRepository.save(human3);
        Human human4 = Human.builder()
                .home(home)
                .name("알렉산더")
                .money(10000)
                .birth(LocalDate.of(2001, 11, 20))
                .build();
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

