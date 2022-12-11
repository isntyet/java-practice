package com.isntyet.java.practice.human.infra;


import com.isntyet.java.practice.human.dto.GetUsersResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "userClient", url = "https://randomuser.me")
public interface UserClient {

    @GetMapping(value = "/api/")
    GetUsersResponse getUsers(@RequestParam("nat") String nation);
}
