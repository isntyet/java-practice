package com.isntyet.java.practice.human.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
@Builder
public class GetHumansResponse {
    private final List<HumanInfo> humans;

}
