package com.isntyet.java.practice.stream;

import com.isntyet.java.practice.stream.domain.Human;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MapTest {
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
        humans.add(new Human(8L, "kuku", 2222, LocalDate.of(1998, 7, 27), Arrays.asList("hawai")));
    }

    @Test
    @DisplayName("이름만 가져와서 List 만들기")
    void mapTest1() {
        List<String> humanNames = humans.stream()
                .map(h -> h.getName())
                .collect(Collectors.toList());

        System.out.println();
        for (String humanName : humanNames) {
            System.out.print(humanName + " ");
        }
    }

    @Test
    @DisplayName("중복제거")
    void mapTest2() {
        printHumanNames(humans);

        List<String> names = humans.stream()
                .map(h -> h.getName())
                .distinct()
                .collect(Collectors.toList());

        System.out.println();
        for (String name : names) {
            System.out.print(name + " ");
        }
    }

    @Test
    @DisplayName("다녀온 여행지 종합")
    void mapTest3() {
        printHumanTravelDestination(humans);

        List<String> travelDestinations = humans.stream()
                .map(h -> h.getTravelDestinations())
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());

        for (String travelDestination : travelDestinations) {
            System.out.print(travelDestination + " ");
        }
    }

    @Test
    @DisplayName("이름에 쓰인 문자 가져오기 (중복제거하여)")
    void mapTest4() {
        printHumanNames(humans);

        List<String> humanNameWords = humans.stream()
                .map(h -> h.getName().split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());

        System.out.println();
        for (String humanNameWord : humanNameWords) {
            System.out.print(humanNameWord + " ");
        }
    }

    void printHumanTravelDestination(List<Human> humans){
        for (Human human : humans) {
            System.out.println(human.getTravelDestinations().toString());
        }
        System.out.println();
    }

    void printHumanNames(List<Human> humans){
        System.out.println();
        for (Human human : humans) {
            System.out.print(human.getName() + " ");
        }
    }

}
