package com.isntyet.java.practice.human.infra;


import com.isntyet.java.practice.human.dto.CreateHumanRequest;
import com.isntyet.java.practice.human.dto.CreateHumanResponse;
import com.isntyet.java.practice.human.dto.GetHumansResponse;
import com.isntyet.java.practice.human.dto.HumanInfo;
import com.isntyet.java.practice.human.infra.config.HumanFeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "humanClient", url = "${external.human-service.host}", configuration = HumanFeignClientConfig.class)
public interface HumanClient {

    @PostMapping(value = "/human")
    CreateHumanResponse createHuman(CreateHumanRequest request);

    @GetMapping(value = "/human/list")
    List<HumanInfo> getHumans(@RequestParam("name") String name);
}
