package com.isntyet.java.practice.home.domain;

import com.isntyet.java.practice.home.application.command.HomeFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HomeRepositoryCustom {
    List<Home> findAllHomesByFilter(String name);

    Page<Home> findAllHomesByFilterWithPage(HomeFilter filter, Pageable page);
}
