package com.isntyet.java.practice.human.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Human {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idx;
    private String name;
    private Integer money;
    private LocalDate birth;

    @Version
    private Integer version;

    public Human(String name, Integer money, LocalDate birth) {
        this.name = name;
        this.money = money;
        this.birth = birth;
    }

    public int decreaseMoney(int money) {
        if (this.money - money < 0) {
            throw new IllegalArgumentException("돈이 부족해");
        }
        return this.money -= money;
    }

    public int increaseMoney(int money) {
        return this.money += money;
    }
}
