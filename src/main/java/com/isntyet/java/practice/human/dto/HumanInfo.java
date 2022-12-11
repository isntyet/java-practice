package com.isntyet.java.practice.human.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.isntyet.java.practice.human.domain.Human;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
@Builder
public class HumanInfo {
    private final Long id;

    private final String name;

    private final Integer money;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate birth;

    public static HumanInfo from(Human human) {
        return HumanInfo.builder()
                .id(human.getIdx())
                .name(human.getName())
                .money(human.getMoney())
                .birth(human.getBirth())
                .build();
    }
}
