package com.isntyet.java.practice.human.domain;

import com.isntyet.java.practice.home.domain.Home;
import com.isntyet.java.practice.human.domain.converter.HumanTagConverter;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class Human {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "human_idx")
    private Long idx;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "home_idx", referencedColumnName = "home_idx", nullable = true)
    private Home home;
    private String name;
    private Integer money;
    private LocalDate birth;

    @Convert(converter = HumanTagConverter.class)
    @Column(name = "tags", columnDefinition = "TEXT")
    private LinkedHashSet<String> tags;

    @Version
    private Integer version;

    @Builder
    public Human(Home home, String name, Integer money, LocalDate birth, Set<String> tags) {
        this.home = home;
        this.name = name;
        this.money = money;
        this.birth = birth;
        this.tags = new LinkedHashSet<>(tags);
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
