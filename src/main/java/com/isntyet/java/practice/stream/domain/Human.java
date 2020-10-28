package com.isntyet.java.practice.stream.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    public Human(String name, Integer money, LocalDate birth) {
        this.idx = idx;
        this.name = name;
        this.money = money;
        this.birth = birth;
    }
}
