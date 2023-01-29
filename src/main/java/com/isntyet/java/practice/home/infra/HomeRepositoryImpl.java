package com.isntyet.java.practice.home.infra;

import com.isntyet.java.practice.home.domain.Home;
import com.isntyet.java.practice.home.domain.HomeRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.isntyet.java.practice.home.domain.QHome.home;
import static com.isntyet.java.practice.human.domain.QHuman.human;

@RequiredArgsConstructor
@Repository
public class HomeRepositoryImpl implements HomeRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Home> findAllHomesByFilter(String name) {
        List<Home> homes = queryFactory.selectFrom(home)
                .leftJoin(home.humans, human).fetchJoin()
                .where(home.name.like(name))
                .fetch();

        return homes;
    }
}
