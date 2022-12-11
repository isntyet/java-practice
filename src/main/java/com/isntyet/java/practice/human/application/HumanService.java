package com.isntyet.java.practice.human.application;

import com.isntyet.java.practice.human.domain.Human;
import com.isntyet.java.practice.human.domain.HumanRepository;
import com.isntyet.java.practice.human.dto.GetUsersResponse;
import com.isntyet.java.practice.human.event.HumanEvent;
import com.isntyet.java.practice.human.infra.UserClient;
import com.isntyet.java.practice.human.infra.config.UserFeignClientConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class HumanService {
    private final HumanRepository humanRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final UserClient userClient;

    @Transactional
    public int currentMoney(String name) {
        Human human = humanRepository.findByName(name);
        return human.getMoney();
    }

    @Transactional
    public int decreaseMoney(String name, int money) {
        Human human = humanRepository.findWithNameForUpdate(name);
        human.decreaseMoney(money);
        return human.getMoney();
    }

    @Transactional
    public void increaseMoney(String name, int money) {
        log.info("in service");
        Human human = humanRepository.findByName(name);
        human.increaseMoney(money);
        eventPublisher.publishEvent(new HumanEvent(this));
    }

    public GetUsersResponse getExternalUsers(String nation) {
        return userClient.getUsers(nation);
    }
}
