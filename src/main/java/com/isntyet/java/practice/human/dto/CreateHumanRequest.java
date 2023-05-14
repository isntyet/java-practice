package com.isntyet.java.practice.human.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateHumanRequest {
    private String name;

    private Integer money;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;

    private Set<String> tags = new LinkedHashSet<>();
}
