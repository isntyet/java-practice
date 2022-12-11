package com.isntyet.java.practice.human.controller;

import com.isntyet.java.practice.common.CommonResponse;
import com.isntyet.java.practice.human.application.HumanService;
import com.isntyet.java.practice.human.domain.Human;
import com.isntyet.java.practice.human.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/human")
@RequiredArgsConstructor
@Slf4j
public class HumanController {
    private final HumanService humanService;

    @GetMapping("/decrease")
    public CommonResponse<String> decreaseMoney(@RequestParam(value = "name") String name, @RequestParam(value = "money") int money) {
        String result;
        try {
            humanService.decreaseMoney(name, money);
            result = "현재 남은돈 : " + humanService.currentMoney(name);
        }
//        catch (ObjectOptimisticLockingFailureException oe) {
//            log.info("재시도");
//            return decreaseMoney(name, money);
//        }
        catch (Exception e) {
            result = e.getMessage();
        }
        log.info(result);
        return CommonResponse.ok(result);
    }

    @GetMapping("/increase")
    public CommonResponse<String> increaseMoney(@RequestParam(value = "name") String name, @RequestParam(value = "money") int money) {
        String result;
        try {
            humanService.increaseMoney(name, money);
            result = "현재 남은돈 : " + humanService.currentMoney(name);
        } catch (Exception e) {
            result = e.getMessage();
        }
        log.info(result);
        return CommonResponse.ok(result);
    }

    @PostMapping
    public CommonResponse<Human> createHuman(
            @RequestHeader Map<String, Object> requestHeader,
            @RequestBody CreateHumanRequest request
    ) {
        this.printHeader(requestHeader);
        return CommonResponse.ok(humanService.create(request));
    }

    @GetMapping("/list")
    public CommonResponse<List<Human>> getHumans(
            @RequestHeader Map<String, Object> requestHeader,
            @RequestParam(value = "name") String name
    ) {
        this.printHeader(requestHeader);

        return CommonResponse.ok(humanService.getHumansByName(name));
    }

    @PostMapping("/external-create-human")
    public CommonResponse<CreateHumanResponse> createExternalHuman(@RequestBody CreateHumanRequest request) {
        return CommonResponse.ok(humanService.createExternalHuman(request));
    }

    @GetMapping("/external-get-humans")
    public CommonResponse<List<HumanInfo>> getExternalHumans(@RequestParam(value = "name") String name) {
        var result = humanService.getExternalHumans(name);
        return CommonResponse.ok(result);
    }

    @GetMapping("/external-get-humans2")
    public CommonResponse<List<HumanInfo>> getExternalHumans2(@RequestParam(value = "name") String name) {
        var result = humanService.getExternalHumans2(name);
        return CommonResponse.ok(result);
    }

    @GetMapping("/external-users")
    public CommonResponse<GetUsersResponse> getExternalUsers(@RequestParam(value = "nation") String nation) {
        var result = humanService.getExternalUsers(nation);
        return CommonResponse.ok(result);
    }

    private void printHeader(Map<String, Object> requestHeader) {
        System.out.println("header ================== ");
        for (String s : requestHeader.keySet()) {
            System.out.println(s + " --- " + requestHeader.get(s));
        }
        System.out.println("=========================== ");
    }
}
