package com.isntyet.java.practice.human.infra;


import com.isntyet.java.practice.human.dto.CreateHumanRequest;
import com.isntyet.java.practice.human.dto.CreateHumanResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "humanClient", url = "${external.human-service.host}")
public interface HumanClient {

    @PostMapping(value = "/human")
    CreateHumanResponse createHuman(CreateHumanRequest request);
}
