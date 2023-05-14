package com.isntyet.java.practice.config;

import com.isntyet.java.practice.home.domain.Home;
import com.isntyet.java.practice.home.domain.HomeRepository;
import com.isntyet.java.practice.human.domain.Human;
import com.isntyet.java.practice.human.domain.HumanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class TestConfig {
    private final HomeRepository homeRepository;
    private final HumanRepository humanRepository;

    //    @PostConstruct
    void initData() {
        List<Home> homes = new ArrayList<>();

        for (int i = 0; i < 100000; i++) {
            Home home = Home.builder()
                    .name("name " + i)
                    .price(i)
                    .address("address " + i)
                    .build();

            homes.add(home);

            if (homes.size() % 10000 == 0) {
                homeRepository.saveAll(homes);
                homes.clear();
            }
        }

        if (homes.size() > 0) {
            homeRepository.saveAll(homes);
        }
    }

    @PostConstruct
    void initHumanData() {
        List<Human> humans = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            LinkedHashSet<String> tags = new LinkedHashSet<>();
            tags.add("haha" + i);
            tags.add("jojo" + i);

            Human human = Human.builder()
                    .name("jojo")
                    .money(i * 1000)
                    .birth(LocalDate.now().plusDays(i))
                    .tags(tags)
                    .build();

            humans.add(human);

            if (humans.size() % 10000 == 0) {
                humanRepository.saveAll(humans);
                humans.clear();
            }
        }

        if (humans.size() > 0) {
            humanRepository.saveAll(humans);
        }
    }
}
