package com.isntyet.java.practice.home.application.command;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Builder
@Getter
public class CreateHomeCommand {
    private final String name;
    private final String address;

    private final int price;

    private final List<HumanItem> humans;

    @RequiredArgsConstructor
    @Builder
    @Getter
    public static class HumanItem {
        private final String name;
        private final Integer money;
        private final LocalDate birth;
    }
}
