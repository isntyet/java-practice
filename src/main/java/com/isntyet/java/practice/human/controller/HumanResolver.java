package com.isntyet.java.practice.human.controller;

import com.isntyet.java.practice.human.application.HumanService;
import com.isntyet.java.practice.human.domain.Human;
import com.isntyet.java.practice.human.dto.CreateHumanRequest;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;

import java.util.List;

@DgsComponent
@RequiredArgsConstructor
public class HumanResolver {
    private final HumanService humanService;

    @DgsQuery
    public List<Human> getHumansByName(@InputArgument String name) {
        var humans = this.humanService.getHumansByName(name);
        return humans;
    }

    @DgsMutation
    public Human createHuman(@InputArgument CreateHumanRequest input) {
        return this.humanService.create(input);
    }
}
