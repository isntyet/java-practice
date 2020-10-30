package com.isntyet.java.practice.human.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HumanRepository extends JpaRepository<Human, Integer> {

    Human findByName(String name);
}
