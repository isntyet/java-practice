package com.isntyet.java.practice.home.application;

import com.isntyet.java.practice.home.application.command.CreateHomeCommand;
import com.isntyet.java.practice.home.application.command.SearchHomeQuery;
import com.isntyet.java.practice.home.application.dto.HomeInfo;
import com.isntyet.java.practice.home.domain.Home;
import com.isntyet.java.practice.home.domain.HomeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class HomeService {
    private final HomeRepository homeRepository;

    @Transactional
    public HomeInfo create(CreateHomeCommand command) {
        Home home = Home.builder()
                .name(command.getName())
                .address(command.getAddress())
                .price(command.getPrice())
                .humans(command.getHumans())
                .build();

        Home result = homeRepository.save(home);
        return HomeInfo.from(result);
    }

    @Transactional(readOnly = true)
    public List<HomeInfo> search(SearchHomeQuery query) {
        List<Home> homes = homeRepository.findAllHomesByFilter(query.getName());
        return homes.stream().map(HomeInfo::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public int currentPrice(String name) {
        Home home = homeRepository.findByName(name);
        return home.getPrice();
    }

    @Transactional
    public int decreasePrice(String name, int price) {
        Home home = homeRepository.findWithNameForUpdate(name);
        home.decreasePrice(price);
        return home.getPrice();
    }
}
