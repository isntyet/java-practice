package com.isntyet.java.practice.controller;

import com.isntyet.java.practice.application.HumanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/human")
@RequiredArgsConstructor
@Slf4j
public class HumanController {
    private final HumanService humanService;

    @GetMapping("/decrease")
    public String decreaseMoney(@RequestParam(value = "name") String name, @RequestParam(value = "money") int money) {
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
        return result;
    }
}
