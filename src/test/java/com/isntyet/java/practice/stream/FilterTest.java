package com.isntyet.java.practice.stream;

import com.isntyet.java.practice.stream.domain.Human;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class FilterTest {
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
    @DisplayName("이름이 zzang인 사람")
    void filterTest1() {
        Human human = humans.stream()
                .filter(h -> h.getName().equals("zzang"))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException());

        System.out.println(human.getIdx());

        assertThat(
                human.getIdx()
        ).isEqualTo(5L);
    }

    @Test
    @DisplayName("이름이 zz로 시작하는 사람(메소드 참조 사용)")
    void filterTest2() {
        Human human = humans.stream()
                .filter(Human::isNameStartwithZz)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException());

        System.out.println(human.getIdx());

        assertThat(
                human.getIdx()
        ).isEqualTo(5L);
    }

    @Test
    @DisplayName("돈이 2000원 이상인 사람 전부")
    void filterTest3() {
        List<Human> tmpHumans = humans.stream()
                .filter(h -> h.getMoney() > 2000)
                .collect(Collectors.toList());

        for (Human human : tmpHumans) {
            System.out.println(human.getIdx());
        }

        assertThat(
                humans.size()
        ).isEqualTo(4);
    }

    @Test
    @DisplayName("이름이 zzang이고 돈이 2000원 이상인 사람")
    void filterTest4() {
        List<Human> tmpHumans = humans.stream()
                .filter(h -> "zzang".equals(h.getName()) && h.getMoney() > 2000)
                .collect(Collectors.toList());

        for (Human human : tmpHumans) {
            System.out.println(human.getIdx());
        }

        assertThat(
                tmpHumans.size()
        ).isEqualTo(1);
    }
}
