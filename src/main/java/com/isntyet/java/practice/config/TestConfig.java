package com.isntyet.java.practice.config;

import com.isntyet.java.practice.home.domain.Home;
import com.isntyet.java.practice.home.domain.HomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class TestConfig {
    private final HomeRepository homeRepository;

    @PostConstruct
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
}
