package com.isntyet.java.practice.human.application.dto;

import com.isntyet.java.practice.human.domain.Human;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
@Builder
@Getter
public class HumanInfo {
    private final String name;
    private final Integer money;
    private final LocalDate birth;

    public static HumanInfo from(Human human) {
        return HumanInfo.builder()
                .name(human.getName())
                .money(human.getMoney())
                .birth(human.getBirth())
                .build();
    }
}
