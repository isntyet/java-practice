package com.isntyet.java.practice.home.application.dto;


import com.isntyet.java.practice.home.domain.Home;
import com.isntyet.java.practice.human.application.dto.HumanInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Builder
@Getter
public class HomeInfo {
    private final Long idx;
    private final String name;
    private final String address;

    private final List<HumanInfo> humans;

    public static HomeInfo from(Home home) {
        return HomeInfo.builder()
                .idx(home.getIdx())
                .name(home.getName())
                .address(home.getAddress())
                .humans(
                        home.getHumans().stream()
                                .map(HumanInfo::from)
                                .collect(Collectors.toList())
                )
                .build();
    }
}
