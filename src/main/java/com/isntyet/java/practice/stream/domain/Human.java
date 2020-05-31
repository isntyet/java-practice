package com.isntyet.java.practice.stream.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Data
@Builder
public class Human implements Comparable<Human> {

    private Long idx;
    private String name;
    private Integer money;
    private LocalDate birth;
    private List<String> travelDestinations;

    public Human(Long idx, String name, Integer money, LocalDate birth, List<String> travelDestinations) {
        this.idx = idx;
        this.name = name;
        this.money = money;
        this.birth = birth;
        this.travelDestinations = travelDestinations;
    }

    public boolean isNameStartwithZz() {
        return this.name.startsWith("zz");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Human human = (Human) o;
        return Objects.equals(idx, human.idx) &&
                Objects.equals(name, human.name) &&
                Objects.equals(money, human.money) &&
                Objects.equals(birth, human.birth) &&
                Objects.equals(travelDestinations, human.travelDestinations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idx, name, money, birth, travelDestinations);
    }

    @Override
    public int compareTo(Human o) {
        return this.name.compareTo(o.name);
    }
}
