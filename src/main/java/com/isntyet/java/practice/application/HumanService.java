package com.isntyet.java.practice.application;

import com.isntyet.java.practice.human.domain.Human;
import com.isntyet.java.practice.human.domain.HumanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class HumanService {
    private final HumanRepository humanRepository;

    public int currentMoney(String name) {
        Human human = humanRepository.findByName(name);
        return human.getMoney();
    }

    @Transactional
//    @Lock(value = LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    public int decreaseMoney(String name, int money) {
        Human human = humanRepository.findByName(name);
        human.decreaseMoney(money);
        return human.getMoney();
    }
}
