package com.isntyet.java.practice.home.domain;

import com.isntyet.java.practice.home.application.command.CreateHomeCommand;
import com.isntyet.java.practice.human.domain.Human;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor
public class Home {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "home_idx")
    private Long idx;
    private String name;
    private String address;
    private int price;

    @OneToMany(mappedBy = "home", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Human> humans = new ArrayList<>();

    @Builder
    public Home(String name, String address, int price, List<CreateHomeCommand.HumanItem> humans) {
        this.name = name;
        this.address = address;
        this.price = price;
        this.humans = humans.stream().map(
                        humanItem -> Human.builder()
                                .home(this)
                                .name(humanItem.getName())
                                .money(humanItem.getMoney())
                                .birth(humanItem.getBirth())
                                .build()
                )
                .collect(Collectors.toList());
    }

    public int decreasePrice(int price) {
        if (this.price - price < 0) {
            throw new IllegalArgumentException("가격이 부족해");
        }
        return this.price -= price;
    }
}
