package com.isntyet.java.practice.stream;

import com.isntyet.java.practice.stream.domain.Human;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class SortTest {
    private List<Human> humans;

    @BeforeEach
    void setUp() {
        humans = new ArrayList<>();
        humans.add(new Human(1L, "jojae", 2900, LocalDate.of(1991, 2, 26), Arrays.asList("seoul", "hawai")));
        humans.add(new Human(2L, "haha", 1000, LocalDate.of(2003, 3, 2), Arrays.asList("busan")));
        humans.add(new Human(3L, "arabia", 30000, LocalDate.of(2001, 4, 6), Arrays.asList("seoul", "paris")));
        humans.add(new Human(4L, "cici", 150, LocalDate.of(1982, 5, 16), Arrays.asList("daegu", "hongkong")));
        humans.add(new Human(5L, "zzang", 40000, LocalDate.of(1910, 6, 26), Arrays.asList("gwangju")));
        humans.add(new Human(6L, "ssu", 200000, LocalDate.of(2012, 7, 11), Arrays.asList("busan")));
        humans.add(new Human(7L, "kuku", 150, LocalDate.of(1991, 2, 27), Arrays.asList("seoul", "hawai")));
    }

    @Test
    @DisplayName("이름 사전순 정렬")
    void sortTest1() {
        printHumans(humans);

        List<Human> sortedHumans = humans.stream()
                .sorted()
                .collect(Collectors.toList());

        printHumans(sortedHumans);
    }

    @Test
    @DisplayName("이름 사전 역순 정렬")
    void sortTest2() {
        printHumans(humans);

        List<Human> sortedHumans = humans.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        printHumans(sortedHumans);
    }

    @Test
    @DisplayName("돈 순으로 정렬")
    void sortTest3() {
        printHumans(humans);

        List<Human> sortedHumans = humans.stream()
                .sorted(Comparator.comparingInt(Human::getMoney))
                .collect(Collectors.toList());

        printHumans(sortedHumans);
    }

    @Test
    @DisplayName("돈 순으로 정렬 (역순)")
    void sortTest4() {
        printHumans(humans);

        List<Human> sortedHumans = humans.stream()
                .sorted(Comparator.comparingInt(Human::getMoney).reversed())
                .collect(Collectors.toList());

        printHumans(sortedHumans);
    }

    @Test
    @DisplayName("람다 표현식을 사용하여 정렬")
    void sortedTest5() {
        printHumans(humans);

        List<Human> sortedHumans = humans.stream()
                .sorted((h1, h2) -> h1.getMoney() - h2.getMoney())
                .collect(Collectors.toList());

        printHumans(sortedHumans);
    }

    void printHumans(List<Human> humans){
        System.out.println();
        for (Human human : humans) {
            System.out.print(human.getName() + " ");
        }
    }

}
