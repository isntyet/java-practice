package com.isntyet.java.practice.human.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetUsersResponse {
    private List<Result> results;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Result {
        private String gender;
        private String email;
    }
}


