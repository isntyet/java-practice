package com.isntyet.java.practice.home.domain;

import java.util.List;

public interface HomeRepositoryCustom {
    List<Home> findAllHomesByFilter(String name);
}
