package com.isntyet.java.practice.home.controller;

import com.isntyet.java.practice.common.CommonResponse;
import com.isntyet.java.practice.home.application.HomeService;
import com.isntyet.java.practice.home.application.command.CreateHomeCommand;
import com.isntyet.java.practice.home.application.dto.HomeInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController {
    private final HomeService homeService;

    @PostMapping
    public CommonResponse<HomeInfo> create(@RequestBody CreateHomeCommand command) {
        HomeInfo home = homeService.create(command);

        return CommonResponse.ok(home);
    }

    @GetMapping("/decrease")
    public String decreasePrice(@RequestParam(value = "name") String name, @RequestParam(value = "price") int price) {
        String result;
        try {
            homeService.decreasePrice(name, price);
            result = "현재 가격 : " + homeService.currentPrice(name);
        }
//        catch (ObjectOptimisticLockingFailureException oe) {
//            log.info("재시도");
//            return decreaseMoney(name, money);
//        }
        catch (Exception e) {
            result = e.getMessage();
        }
        log.info(result);
        return result;
    }
}
