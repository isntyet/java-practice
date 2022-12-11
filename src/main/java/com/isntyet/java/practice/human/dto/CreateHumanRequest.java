package com.isntyet.java.practice.human.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@RequiredArgsConstructor
public class CreateHumanRequest {
    private final String name;

    private final Integer money;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate birth;
}
