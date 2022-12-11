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
public class CreateHumanResponse {
    private final String name;

    private final Integer money;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate birth;

    public static CreateHumanResponse from(Human human) {
        return CreateHumanResponse.builder()
                .name(human.getName())
                .money(human.getMoney())
                .birth(human.getBirth())
                .build();
    }
}
