package com.isntyet.java.practice.home.application.command;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class SearchHomeQuery {
    private final String name;
}
